package com.javiergs.examples;

import com.javiergs.cortex4j.CortexListener;
import com.javiergs.cortex4j.CortexSample;
import com.javiergs.cortex4j.CortexSession;

/** Observer example: the application owns its concrete stream listener. */
public final class MetricsExample {
    private static final class MetricsListener implements CortexListener {
        @Override public void onSample(CortexSample sample) {
            System.out.println("Metrics @ " + sample.getTimestamp() + ": " + sample.getValues());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try (CortexSession session = CortexSession.connect()) {
            session.addListener("met", new MetricsListener());
            session.subscribe("met");
            System.out.println("Listening for performance metrics for 30 seconds...");
            Thread.sleep(30_000);
        }
    }
}
