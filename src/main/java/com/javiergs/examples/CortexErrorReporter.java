package com.javiergs.examples;

/** Presents common Cortex failures without an intimidating Java stack trace. */
final class CortexErrorReporter {
    private CortexErrorReporter() { }

    static void report(IllegalStateException error) {
        String message = error.getMessage() == null ? "Unknown Cortex error" : error.getMessage();
        System.err.println("Unable to start Cortex session.");
        if (message.contains("-32004") || message.contains("No Cortex headset available")) {
            System.err.println("No available headset. Connect a supported EMOTIV headset or start Virtual Brainwear in EMOTIV Launcher.");
        } else if (message.contains("-32021")) {
            System.err.println("Invalid Cortex application credentials. Check cortex4j.properties and any environment-variable overrides.");
        } else {
            System.err.println("Details: " + message);
            System.err.println("Verify EMOTIV Launcher is running and your application has access.");
        }
    }
}
