package com.javiergs.examples;

import com.javiergs.cortex4j.CortexClient;

/**
 * Receive headset device/status samples.
 * This example demonstrates how to connect to a headset and listen to device samples.
 *
 * @author javiergs
 * @version 1.0 (2026-10-29)
 */
public final class DeviceExample {

  public static void main(String[] args) throws InterruptedException {
    try (CortexClient cortex = CortexClient.connect()) {
      System.out.println("Connected headset: " + cortex.getHeadsetId());
      cortex.addListener("dev", sample ->
          System.out.println("Device @ " + sample.getTimestamp() + ": " + sample.getValues()));
      cortex.subscribe("dev");
      System.out.println("Listening for device samples for 30 seconds...");
      Thread.sleep(30_000);
    }
  }
}
