# Cortex4J — Getting Started

Independent Java 21/Maven examples for [Cortex4J](https://github.com/jgs-lab/api-cortex4j). This repository contains examples, not the library implementation.

<img width="1536" height="1024" alt="A0F040FB-3FCC-4300-B330-99A379DBCE86" src="https://github.com/user-attachments/assets/71244245-469d-479d-ba10-c69f02660ba7" />


## Setup

1. Install JDK 21 and Maven.
2. Create an [EMOTIV account](https://www.emotiv.com/) and install/sign in to EMOTIV Launcher.
3. [Register a Cortex application](https://www.emotiv.com/knowledge-base/how-to-register-your-app-id-with-cortex-sdk-for-development) to obtain your Client ID and Client Secret.
4. Connect a headset or configure [Virtual Brainwear](https://emotiv.gitbook.io/emotiv-launcher/devices-setting-up-virtual-brainwear-r/creating-a-virtual-brainwear-device).
5. Approve application access in Launcher. Available streams depend on your headset, permissions, and EMOTIV licensing; a free account does not guarantee access to EEG or metrics.

## Install Cortex4J

Cortex4J 0.1.0 is not assumed to be published to Maven Central. Install it locally first:

```bash
git clone https://github.com/jgs-lab/api-cortex4j.git
cd api-cortex4j
mvn clean install
```

Then return to this repository's root directory.

## Credentials

Set environment variables:

```bash
export CORTEX_CLIENT_ID="your-client-id"
export CORTEX_CLIENT_SECRET="your-client-secret"
```

Alternatively copy `src/main/resources/cortex4j.properties.example` to `src/main/resources/cortex4j.properties` and enter your credentials. The actual properties file is ignored by Git. Environment variables take precedence. **Never commit credentials or distribute them in a JAR.**

## Examples

Device-status stream (recommended first test):

```bash
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.DeviceExample
```

Performance metrics (requires stream entitlement):

```bash
mvn compile exec:java -Dexec.mainClass=com.javiergs.examples.MetricsExample
```

Each program connects, registers an Observer listener, subscribes, prints samples for 30 seconds, and closes the session.

## Troubleshooting

- No headset: connect hardware or enable Virtual Brainwear.
- Access denied: check Client ID/Secret, Launcher sign-in, and application approval.
- Stream rejected: check permissions, headset compatibility, and subscription entitlement.
- Connection failed: confirm Launcher is running.

See [CortexSession documentation](https://github.com/jgs-lab/api-cortex4j/blob/main/docs/session-api.md) for API details.
