package com.javiergs.examples;

import com.javiergs.cortex4j.CortexSample;
import com.javiergs.cortex4j.CortexClient;

/**
 * Minimal observer example for the Cortex "pow" stream.
 * Values are printed in Cortex channel order; no sensor mapping is assumed.
 */
public final class BandPowerExample {
    private BandPowerExample() { }

    public static void main(String[] args) throws InterruptedException {
        try (CortexClient session = CortexClient.connect()) {
            System.out.println("Headset: " + session.getHeadsetId());
            System.out.println("Reported sensors: " + session.getSensorLabels());
            session.addListener("pow", BandPowerExample::printSample);
            session.subscribe("pow");
            System.out.println("Listening for band-power data for 30 seconds...");
            Thread.sleep(30_000);
        } catch (IllegalStateException error) {
            CortexErrorReporter.report(error);
        }
    }

    private static void printSample(CortexSample sample) {
        System.out.println(sample.getStream() + " @ " + sample.getTimestamp()
                + ": " + sample.getValues());
    }
}
