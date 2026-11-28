package com.javiergs.cortex4j;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** Tests the device-status stream with a non-activated session on Cortex4J 0.2.0. */
public final class OpenSessionDeviceExample {

  public static void main(String[] args) throws Exception {
    CortexCredentials credentials = CortexConfiguration.loadCredentials();
    try (CortexRpcClient client = await(CortexRpcClient.connectWithLocalFallback())) {
      if (client.getConnectionSecurity() != ConnectionSecurity.SECURE) {
        throw new IllegalStateException("Verified TLS is required before sending Cortex credentials.");
      }

      JsonNode access = result("requestAccess", client.requestAccess(credentials));
      if (!access.path("accessGranted").asBoolean()) {
        throw new IllegalStateException("Approve the app in EMOTIV Launcher and rerun.");
      }
      String token = result("authorize", client.authorize(credentials))
          .path("cortexToken").asText();
      if (token.isBlank()) {
        throw new IllegalStateException("Cortex authorization returned no token.");
      }

      String headset = "";
      for (JsonNode device : result("queryHeadsets", client.queryHeadsets())) {
        if ("connected".equals(device.path("status").asText())) {
          headset = device.path("id").asText();
          break;
        }
      }
      if (headset.isBlank()) {
        throw new IllegalStateException("Connect Virtual Brainwear in EMOTIV Launcher and rerun.");
      }

      var params = JsonNodeFactory.instance.objectNode()
          .put("cortexToken", token).put("headset", headset).put("status", "open");
      String sessionId = result("createSession", client.call("createSession", params))
          .path("id").asText();
      if (sessionId.isBlank()) {
        throw new IllegalStateException("Cortex did not return an open session ID.");
      }

      try {
        System.out.println("Connected headset: " + headset);
        client.addSampleListener(sample -> {
          if ("dev".equals(sample.getStream()) && sessionId.equals(sample.getSessionId())) {
            System.out.println("Device @ " + sample.getTimestamp() + ": " + sample.getValues());
          }
        });
        JsonNode subscription = result("subscribe", client.subscribe(token, sessionId, List.of("dev")));
        if (!subscription.path("success").isArray() || subscription.path("success").isEmpty()) {
          throw new IllegalStateException("Cortex declined the dev subscription: "
              + subscription.path("failure"));
        }
        System.out.println("Listening for device samples for 30 seconds...");
        Thread.sleep(30_000);
      } finally {
        result("updateSession", client.updateSession(token, sessionId, "close"));
      }
    }
  }

  private static JsonNode result(String method, CompletableFuture<JsonNode> call) throws Exception {
    JsonNode response = await(call);
    if (response.hasNonNull("error")) {
      throw new IllegalStateException(method + " failed: " + response.path("error"));
    }
    if (!response.hasNonNull("result")) {
      throw new IllegalStateException(method + " returned no result.");
    }
    return response.path("result");
  }

  private static <T> T await(CompletableFuture<T> future) throws Exception {
    return future.get(15, TimeUnit.SECONDS);
  }
}
