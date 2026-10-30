package com.javiergs.examples;

import com.javiergs.cortex4j.CortexSample;
import com.javiergs.cortex4j.CortexClient;

/**
 * Minimal observer example for the Cortex "eeg" stream.
 * Values are printed in Cortex channel order; no sensor mapping is assumed.
 */
public final class EegExample {
    private EegExample() { }

    public static void main(String[] args) throws InterruptedException {
        try (CortexClient cortex = CortexClient.connect()) {
            System.out.println("Headset: " + cortex.getHeadsetId());
            System.out.println("Reported sensors: " + cortex.getSensorLabels());
            cortex.addListener("eeg", EegExample::printSample);
            cortex.subscribe("eeg");
            System.out.println("Listening for EEG data for 30 seconds...");
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
