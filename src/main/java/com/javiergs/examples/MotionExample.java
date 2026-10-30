package com.javiergs.examples;

import com.javiergs.cortex4j.CortexSample;
import com.javiergs.cortex4j.CortexClient;

/**
 * Minimal observer example for the Cortex "mot" stream.
 * Values are printed in Cortex channel order; no sensor mapping is assumed.
 */
public final class MotionExample {
    private MotionExample() { }

    public static void main(String[] args) throws InterruptedException {
        try (CortexClient cortex = CortexClient.connect()) {
            System.out.println("Headset: " + cortex.getHeadsetId());
            System.out.println("Reported sensors: " + cortex.getSensorLabels());
            cortex.addListener("mot", MotionExample::printSample);
            cortex.subscribe("mot");
            System.out.println("Listening for motion data for 30 seconds...");
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
