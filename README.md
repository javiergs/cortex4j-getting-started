# Cortex4J — Getting Started

Independent Java 21/Maven examples for **Cortex4J**, available on Maven Central. This repository contains examples, not the library implementation.

<img width="1536" height="1024" alt="a" src="https://github.com/user-attachments/assets/f3881025-747b-4e13-8cfa-cd653f633b7b" />

## API documentation

- [Cortex4J Javadoc](docs/javadoc/index.html) — generated public API reference (available after the documentation publication PR is merged).

Javadoc is generated from the library repository and proposed here through a reviewed pull request. The published documentation contains API HTML, **not the Java implementation source**. To publish the HTML site, configure GitHub Pages for **Deploy from a branch → main → /docs** in this repository's Settings → Pages.

## Setup

1. Install JDK 21 and Maven 3.9+; clone this repository and open a terminal in its root directory.
2. Create an [EMOTIV account](https://www.emotiv.com/) and install/sign in to EMOTIV Launcher.
3. [Register a Cortex application](https://www.emotiv.com/knowledge-base/how-to-register-your-app-id-with-cortex-sdk-for-development) to obtain your Client ID and Client Secret.
4. Connect a headset or configure [Virtual Brainwear](https://emotiv.gitbook.io/emotiv-launcher/devices-setting-up-virtual-brainwear-r/creating-a-virtual-brainwear-device).
5. Approve application access in Launcher. Available streams depend on your headset, permissions, and EMOTIV licensing; a free account does not guarantee access to EEG or metrics.

## Install Cortex4J

Cortex4J 0.1.0 is available on Maven Central. Add the following dependency to the `<dependencies>` section of your Maven project's `pom.xml`:

```xml
<dependency>
    <groupId>com.javiergs</groupId>
    <artifactId>cortex4j</artifactId>
    <version>0.1.0</version>
</dependency>
```

This Getting Started repository already includes the dependency in its `pom.xml`. Maven downloads Cortex4J automatically when you build or run an example; you do not need to clone or install the library separately.

## Credentials

**Do not commit your client secret.** Each student should register their own Cortex application, subject to EMOTIV's account and licensing requirements.


Your **Client ID and Client Secret identify your registered Cortex application**. They are **not** your EMOTIV account email address and password. Obtain them by following the application registration instructions in Setup step 3.

For these Getting Started examples, store your Cortex application credentials in a local properties file.

1. Copy `src/main/resources/cortex4j.properties.example` to `src/main/resources/cortex4j.properties` (the example template is included in this repository).
2. Edit `cortex4j.properties` with the Client ID and Client Secret from your registered EMOTIV application:

```properties
cortex.client.id=YOUR_CLIENT_ID
cortex.client.secret=YOUR_CLIENT_SECRET
```

3. Save the file and run an example. `CortexSession.connect()` automatically reads these credentials; no changes to the Java examples are necessary.

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


The metrics example uses the documented channel ordering for known layouts; Cortex4J 0.1.0 does not expose the `subscribe` response's `cols` metadata yet. For unknown layouts, the example prints raw values instead of guessing labels. Verify the reported labels against your headset and Cortex version. See [EMOTIV's sample format documentation](https://emotiv.gitbook.io/cortex-api/data-subscription/data-sample-object).

Each program connects, registers an Observer listener, subscribes, prints samples for 30 seconds, and closes the session.

## Troubleshooting

- No headset: connect hardware or enable Virtual Brainwear.
- Access denied: check Client ID/Secret, Launcher sign-in, and application approval.
- Stream rejected: check permissions, headset compatibility, and subscription entitlement.
- Connection failed: confirm Launcher is running.

See the [public Cortex4J Javadoc](docs/javadoc/index.html) for API details once documentation is published.
