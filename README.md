# Getting Started with Cortex4J

![Java 21+](https://img.shields.io/badge/Java-21%2B-blue)
![Version 0.2.0](https://img.shields.io/badge/version-0.2.0-green)
[![Java CI](https://github.com/javiergs/cortex4j-getting-started/actions/workflows/java-ci.yml/badge.svg)](https://github.com/javiergs/cortex4j-getting-started/actions/workflows/java-ci.yml)

**Cortex4J** is a Java library for accessing EMOTIV headset data. Use this repository to explore device status, performance metrics, EEG, motion, and band-power streams with Java 21 and Maven.

<img width="1536" height="1024" alt="Cortex4J overview illustration" src="https://github.com/user-attachments/assets/f3881025-747b-4e13-8cfa-cd653f633b7b" />

## Requirements

- **Java 21+** and Maven 3.9+
- EMOTIV Launcher, a supported headset or available Virtual Brainwear
- Registered Cortex application credentials; stream access depends on your account and license

## Quick start

For the complete setup and credential instructions, follow the [Student Quick Start](docs/student-quick-start.md). Start EMOTIV Launcher, connect a headset or Virtual Brainwear, and approve application access. From the repository root:

```bash
mvn clean verify
mvn exec:java -Dexec.mainClass=com.javiergs.examples.DeviceExample
```

The example listens for device/status samples for about 30 seconds. A successful Maven build alone does not verify headset connectivity.

## Install with Maven

Cortex4J 0.2.0 is available on Maven Central. Add the following dependency to the `<dependencies>` section of your Maven project's `pom.xml`:

```xml
<dependency>
    <groupId>com.javiergs</groupId>
    <artifactId>cortex4j</artifactId>
    <version>0.2.0</version>
</dependency>
```

This Getting Started repository already includes the dependency in its `pom.xml`. Maven downloads Cortex4J automatically when you build or run an example; you do not need to clone or install the library separately.

## Examples

- **Device status** — confirm connection and listen for headset events (`DeviceExample`).
- **Performance metrics** — inspect available cognitive metrics (`MetricsExample`).
- **EEG, motion, and band power** — subscribe to `eeg`, `mot`, and `pow` streams when your device and account support them.

See [Runnable Examples](docs/examples.md) for commands, output, and limitations. Sample channel layouts vary by headset and stream; do not assume fixed electrode positions.

## API documentation

[Cortex4J API reference](https://javiergs.github.io/cortex4j-getting-started/javadoc/) — public types including `CortexClient`, `CortexListener`, and `CortexSample`.

## Credentials and privacy

Use your **Cortex application Client ID and Client Secret**, not your EMOTIV account password. For local development, copy `src/main/resources/cortex4j.properties.example` to the ignored `cortex4j.properties` file and enter your credentials, or set both `CORTEX_CLIENT_ID` and `CORTEX_CLIENT_SECRET` environment variables (preferred for independent projects).

Never commit or share credentials, participant recordings, or generated build directories. Maven excludes `cortex4j.properties` from packaged JARs, but may copy it to local `target/classes`. See the [Student Quick Start](docs/student-quick-start.md) for the full setup.

## Build checks

The public **Java CI** workflow runs `mvn clean verify` with Java 21. The separate **Verify Maven Central dependency** workflow compiles with an isolated empty Maven cache to check that the published Cortex4J dependency resolves independently. Neither workflow requires EMOTIV credentials or tests headset connectivity.

## Troubleshooting

- No headset: connect hardware or enable Virtual Brainwear.
- Access denied: check Client ID/Secret, Launcher sign-in, and application approval.
- Stream rejected: check permissions, headset compatibility, and subscription entitlement.
- Connection failed: confirm Launcher is running.
- Java compilation error: check that `mvn -version` reports Java 21, not Java 17.
- Dependency resolution error: confirm internet access and that Maven can download `com.javiergs:cortex4j:0.2.0` from Maven Central.
