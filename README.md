# Getting Started with CORTEX4J

[Contributing](CONTRIBUTING.md) · [Code of Conduct](CODE_OF_CONDUCT.md) · [Accessibility](ACCESSIBILITY.md)

![Java 21+](https://img.shields.io/badge/Java-21%2B-blue)
![Version 0.2.0](https://img.shields.io/badge/version-0.2.0-green)

Learn to read EMOTIV headset data using **Cortex4J 0.2.0**, Java 21, and Maven. Start with the device-status example, then explore performance metrics, EEG, motion, and band power.

<img width="1536" height="1024" alt="Cortex4J overview illustration" src="https://github.com/user-attachments/assets/f3881025-747b-4e13-8cfa-cd653f633b7b" />

## Quick start

1. Install **JDK 21** and **Maven 3.9+**. Check with `java -version` and `mvn -version`; both should use Java 21.
2. Follow [Setup](#setup) to configure EMOTIV Launcher, a headset or Virtual Brainwear, and your Cortex application credentials.
3. In this repository's root folder, run:

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass=com.javiergs.examples.DeviceExample
```

The device example listens for headset status for 30 seconds and closes the connection automatically. If it cannot connect, see [Troubleshooting](#troubleshooting).

## API documentation

[Cortex4J API reference](https://javiergs.github.io/cortex4j-getting-started/javadoc/) — documentation for `CortexClient`, `CortexListener`, `CortexSample`, and other public types.

## Setup

1. Install JDK 21 and Maven 3.9+; clone or download this repository and open a terminal in its root directory.
2. Create an [EMOTIV account](https://www.emotiv.com/) and install/sign in to EMOTIV Launcher.
3. [Register a Cortex application](https://www.emotiv.com/knowledge-base/how-to-register-your-app-id-with-cortex-sdk-for-development) to obtain your Client ID and Client Secret.
4. Connect a headset or configure [Virtual Brainwear](https://emotiv.gitbook.io/emotiv-launcher/devices-setting-up-virtual-brainwear-r/creating-a-virtual-brainwear-device).
5. Approve application access in Launcher. Available streams depend on your headset, permissions, and EMOTIV licensing; a free account does not guarantee access to EEG or metrics.

## Install Cortex4J

Cortex4J 0.2.0 is available on Maven Central. Add the following dependency to the `<dependencies>` section of your Maven project's `pom.xml`:

```xml
<dependency>
    <groupId>com.javiergs</groupId>
    <artifactId>cortex4j</artifactId>
    <version>0.2.0</version>
</dependency>
```

This Getting Started repository already includes the dependency in its `pom.xml`. Maven downloads Cortex4J automatically when you build or run an example; you do not need to clone or install the library separately.

## Credentials

**Do not commit your client secret.** Register your own Cortex application, subject to EMOTIV's account and licensing requirements.


Your **Client ID and Client Secret identify your registered Cortex application**. They are **not** your EMOTIV account email address and password. Obtain them by following the application registration instructions in Setup step 3.

For these Getting Started examples, store your Cortex application credentials in a local properties file.

1. Copy `src/main/resources/cortex4j.properties.example` to `src/main/resources/cortex4j.properties` (the example template is included in this repository).
2. Edit `cortex4j.properties` with the Client ID and Client Secret from your registered EMOTIV application:

```properties
cortex.client.id=YOUR_CLIENT_ID
cortex.client.secret=YOUR_CLIENT_SECRET
```

3. Save the file and run an example. `CortexClient.connect()` automatically reads these credentials; no changes to the Java examples are necessary.

The real `cortex4j.properties` is excluded by `.gitignore`. **Never commit credentials or distribute a JAR containing them.**

**Optional:** Instead of a properties file, you can set `CORTEX_CLIENT_ID` and `CORTEX_CLIENT_SECRET` environment variables. If both are set, they take precedence over the properties file.

## Examples

From the repository root, first confirm Maven can compile the examples:

```bash
mvn clean compile
```

This verifies the Java dependency setup but does not test headset access. Start EMOTIV Launcher, sign in, connect your headset or Virtual Brainwear, and approve the application before running an example.


Device-status stream (recommended first test):

```bash
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.DeviceExample
```

Performance metrics (requires stream entitlement; prints named values for documented 13-channel EPOC/Insight or 4-channel MN8 layouts):

```bash
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.MetricsExample
```

Additional stream examples (availability depends on headset and EMOTIV license):

```bash
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.EegExample
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.MotionExample
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.BandPowerExample
```

These examples print raw ordered channel values. Sensor names and band-power channels are headset/stream-dependent; do not assume array indexes represent specific electrodes or frequency bands. The EEG stream may require additional licensing. All three require EMOTIV Launcher and a connected headset or Virtual Brainwear.


The metrics example uses the documented channel ordering for known layouts; Cortex4J does not expose the `subscribe` response's `cols` metadata yet. For unknown layouts, the example prints raw values instead of guessing labels. Verify the reported labels against your headset and Cortex version. See [EMOTIV's sample format documentation](https://emotiv.gitbook.io/cortex-api/data-subscription/data-sample-object).

Each program connects using `CortexClient`, registers a `CortexListener`, subscribes to a stream, prints samples for 30 seconds, and closes automatically. The example code is in `src/main/java/com/javiergs/examples/`.

## Troubleshooting

- No headset: connect hardware or enable Virtual Brainwear.
- Access denied: check Client ID/Secret, Launcher sign-in, and application approval.
- Stream rejected: check permissions, headset compatibility, and subscription entitlement.
- Connection failed: confirm Launcher is running.
- Java compilation error: check that `mvn -version` reports Java 21, not Java 17.
- Dependency resolution error: confirm internet access and that Maven can download `com.javiergs:cortex4j:0.2.0` from Maven Central.
