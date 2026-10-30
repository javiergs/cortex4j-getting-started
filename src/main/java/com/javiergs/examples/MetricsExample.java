package com.javiergs.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.javiergs.cortex4j.CortexListener;
import com.javiergs.cortex4j.CortexSample;
import com.javiergs.cortex4j.CortexClient;

import java.util.List;

/**
 * Shows human-readable performance metrics for documented Cortex layouts.
 * CortexClient 0.1.0 does not yet expose the subscription's "cols" metadata.
 */
public final class MetricsExample {
    private static final List<String> INSIGHT_EPOC_LABELS = List.of(
            "Engagement active", "Engagement",
            "Excitement active", "Excitement", "Long-term excitement",
            "Stress active", "Stress",
            "Relaxation active", "Relaxation",
            "Interest active", "Interest",
            "Attention active", "Attention");
    private static final List<String> MN8_LABELS = List.of(
            "Attention active", "Attention",
            "Cognitive stress active", "Cognitive stress");

    private static final class MetricsListener implements CortexListener {
        @Override public void onSample(CortexSample sample) {
            List<JsonNode> values = sample.getValues();
            List<String> labels = switch (values.size()) {
                case 13 -> INSIGHT_EPOC_LABELS;
                case 4 -> MN8_LABELS;
                default -> List.of();
            };
            System.out.println("Metrics @ " + sample.getTimestamp());
            if (labels.isEmpty()) {
                System.out.println("Unknown metric layout (" + values.size()
                        + " values); raw channels: " + values);
                return;
            }
            for (int i = 0; i < values.size(); i++) {
                System.out.println("  " + labels.get(i) + ": " + values.get(i));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try (CortexClient session = CortexClient.connect()) {
            session.addListener("met", new MetricsListener());
            session.subscribe("met");
            System.out.println("Listening for performance metrics for 30 seconds...");
            Thread.sleep(30_000);
        } catch (IllegalStateException error) {
            CortexErrorReporter.report(error);
        }
    }
}
