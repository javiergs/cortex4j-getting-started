# Runnable Cortex4J examples

All examples use **Java 21**, the public Maven Central dependency **com.javiergs:cortex4j:0.2.0**, and EMOTIV Launcher. Run commands from the repository root after following the [student quick start](student-quick-start.md).

First verify the build:

```bash
mvn clean verify
```

| Example | Stream | What you observe | Important prerequisite |
| --- | --- | --- | --- |
| `DeviceExample` | `dev` | Headset ID, timestamps, device/status samples | Connected headset or available Virtual Brainwear |
| `MetricsExample` | `met` | Named metrics for recognized layouts; otherwise raw values | Performance metrics stream entitlement |
| `EegExample` | `eeg` | Timestamped, ordered raw EEG values | EEG-capable device and any required licensing |
| `MotionExample` | `mot` | Timestamped motion values | Supported motion stream |
| `BandPowerExample` | `pow` | Timestamped band-power values | Supported band-power stream |

## Run an example

```bash
mvn exec:java -Dexec.mainClass=com.javiergs.examples.DeviceExample
mvn exec:java -Dexec.mainClass=com.javiergs.examples.MetricsExample
mvn exec:java -Dexec.mainClass=com.javiergs.examples.EegExample
mvn exec:java -Dexec.mainClass=com.javiergs.examples.MotionExample
mvn exec:java -Dexec.mainClass=com.javiergs.examples.BandPowerExample
```

Run **one command at a time**. Each example subscribes for about **30 seconds**, prints received samples, and closes the client. Actual samples depend on Launcher, the headset, stream availability, and permissions. Output may be absent if no data arrives.

## Understanding the output

- `DeviceExample` is the recommended first connectivity check.
- `MetricsExample` recognizes the documented 13-value Insight/EPOC and 4-value MN8 layouts. Cortex4J 0.2.0 does **not** expose subscription `cols` metadata, so unknown layouts are printed without guessed labels.
- `EegExample`, `MotionExample`, and `BandPowerExample` print **ordered raw values**. Do not assume a fixed array index always corresponds to a specific sensor or band.
- A passing `mvn clean verify` checks the Java project and published dependency; it does **not** test a real headset or account entitlements.

For stream-specific column definitions, consult the [EMOTIV sample format documentation](https://emotiv.gitbook.io/cortex-api/data-subscription/data-sample-object).

## Student exercises

Try displaying only timestamps and sample counts, comparing two permitted streams, or designing a listener that computes a rolling summary. Do not commit secrets, headset identifiers tied to participants, or identifiable recordings.
