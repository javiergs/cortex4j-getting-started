package com.javiergs.examples;

import com.javiergs.cortex4j.CortexSession;

/** First Cortex4J example: receive headset device/status samples. */
public final class DeviceExample {
    public static void main(String[] args) {
        try (CortexSession session = CortexSession.connect()) {
            System.out.println("Connected headset: " + session.getHeadsetId());
            session.addListener("dev", sample ->
                System.out.println("Device @ " + sample.getTimestamp() + ": " + sample.getValues()));
            session.subscribe("dev");
            System.out.println("Listening for device samples for 30 seconds...");
            Thread.sleep(30_000);
        } catch (InterruptedException error) {
            Thread.currentThread().interrupt();
            System.err.println("Listening interrupted.");
        } catch (IllegalStateException error) {
            CortexErrorReporter.report(error);
        }
    }
}
