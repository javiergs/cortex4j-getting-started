# Contributing to Cortex4J Getting Started

Thank you for improving the public CORTEX4J learning materials. This repository contains examples and documentation for the published Cortex4J library; the library is distributed separately through Maven Central.

## Welcome contributions

- Clarify setup, authentication, headset/Virtual Brainwear prerequisites, and troubleshooting.
- Improve reproducible Java 21 and Maven examples using the published library API.
- Fix documentation errors and add helpful tests or explanations.
- Improve accessibility of examples and diagrams.

## How to contribute

1. Open a Bug Report or Documentation Improvement issue when useful.
2. Fork the repository and create a focused branch.
3. Make a small, documented change using the published library version unless the maintainer requests an update.
4. Run `mvn clean verify` and, when relevant, run the affected example with appropriate hardware or Virtual Brainwear. Explain any testing you could not perform.
5. Open a pull request against `main` and respond to maintainer feedback. A maintainer reviews changes before merging.

Never commit EMOTIV credentials, tokens, personal data, raw participant recordings, or private library source code. Use synthetic or consent-appropriate data in examples and screenshots.

See [Accessibility](ACCESSIBILITY.md) and the [Code of Conduct](CODE_OF_CONDUCT.md).
