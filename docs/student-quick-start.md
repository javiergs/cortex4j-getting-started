# Student quick start

This guide uses the published **Cortex4J 0.2.0** Java library. You do not need the library's implementation source.

## 1. Prepare Java and Maven

Install JDK 21 and Maven 3.9 or newer. From a terminal, check:

```bash
java -version
mvn -version
```

Both should report Java 21. Clone or download this repository, then open a terminal in its root directory.

## 2. Prepare EMOTIV

1. Create an EMOTIV account, install **EMOTIV Launcher**, and sign in.
2. Connect a supported headset, or set up **Virtual Brainwear** if available to your account.
3. Register a Cortex application to obtain its **Client ID** and **Client Secret**.
4. Approve application access in Launcher when prompted.

Your Cortex application credentials are **not** your EMOTIV account password. Streams and Virtual Brainwear availability depend on your headset, account, and licensing.

See the [EMOTIV application registration instructions](https://www.emotiv.com/knowledge-base/how-to-register-your-app-id-with-cortex-sdk-for-development).

## 3. Configure credentials

For a local experiment, copy `src/main/resources/cortex4j.properties.example` to `src/main/resources/cortex4j.properties` and replace the placeholders with your application credentials.

The real properties file is ignored by Git and **excluded from packaged JARs**. Maven may still copy it into local `target/classes` for running examples; never share your build output. Do not commit or share it.

Alternatively, set both `CORTEX_CLIENT_ID` and `CORTEX_CLIENT_SECRET` environment variables. These take precedence over the properties file. Prefer environment variables for independent applications and CI. Never print secrets or paste them into issues.

## 4. Run the first example

With Launcher running and a headset or Virtual Brainwear available:

```bash
mvn clean verify
mvn exec:java -Dexec.mainClass=com.javiergs.examples.DeviceExample
```

`DeviceExample` connects, reports the headset ID, listens for device/status samples for about **30 seconds**, and closes the connection. A successful Maven build **does not** prove headset connectivity.

## 5. Try another stream

If your account permits performance metrics:

```bash
mvn exec:java -Dexec.mainClass=com.javiergs.examples.MetricsExample
```

Other examples cover EEG, motion, and band power. See the [examples guide](examples.md) for commands and limitations.

## 6. Create your own project

In a separate Java 21 Maven project, add:

```xml
<dependency>
    <groupId>com.javiergs</groupId>
    <artifactId>cortex4j</artifactId>
    <version>0.2.0</version>
</dependency>
```

Start with `CortexClient.connect()`, register a `CortexListener` for a stream, subscribe, and close the client when finished. Use the examples here as references, not as a required dependency.

If something fails, see [README troubleshooting](../README.md#troubleshooting). Never include credentials or identifiable participant data in a bug report.
