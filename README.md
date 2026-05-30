# BDD Selenium Framework for OrangeHRM

[![CI](https://github.com/OWNER/REPO/actions/workflows/ci.yml/badge.svg)](https://github.com/OWNER/REPO/actions/workflows/ci.yml)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

This repository contains a small BDD test automation framework (Maven + TestNG + Cucumber + Selenium) targeting OrangeHRM. It follows a layered pattern (Pages → Actions → Steps → Features) and includes a GitHub Actions workflow to build and run tests.

Table of contents
- Quick summary
- Badges and how to update them
- Prerequisites
- Local setup and run
- CI (GitHub Actions)
- Project structure
- Configuration reference
- How to add new features / steps
- Troubleshooting
- Contributing
- License

Quick summary
---------------
- Language / tools: Java (JDK 24 as configured in `pom.xml`), Maven, Selenium 4, Cucumber JVM, TestNG.
- Feature files are under `src/test/resources/features` and step definitions are under `src/test/java/steps`.
- Pages and Actions live in `src/test/java/pages` and `src/test/java/actions` respectively.

Badges
------
The CI badge points to the workflow we added at `.github/workflows/ci.yml`.
Replace the `OWNER/REPO` placeholders below with your GitHub organization/user and repository name to enable the live badge:

```md
[![CI](https://github.com/OWNER/REPO/actions/workflows/ci.yml/badge.svg)](https://github.com/OWNER/REPO/actions/workflows/ci.yml)
```

You can add additional badges (coverage, Maven Central, etc.) once those services are enabled for the repository.

Prerequisites
-------------
- Java JDK 24 (the project `pom.xml` sets `maven.compiler.source`/`target` to 24). If you prefer another JDK, update `pom.xml` and CI workflow accordingly.
- Maven 3.6+
- Internet access for Maven dependencies and Selenium Manager to download browser drivers
- (Local) A Chrome or Firefox installation if running browser tests locally (CI installs Chrome on Ubuntu runners)

Local setup
-----------
1. Clone the repository:

```bash
git clone https://github.com/OWNER/REPO.git
cd bdd-selenium-framework
```

2. Configure environment or system properties (optional). The framework reads configuration with the following precedence: System properties (-D), environment variables, then `src/test/resources/config/config.properties`.

Common runtime configuration keys (see `ConfigReader` for details):

- `base.url` (e.g. `https://opensource-demo.orangehrmlive.com`)
- `browser` (`chrome` or `firefox`) — default `chrome`
- `headless` (`true` / `false`) — default `false`
- `wait.timeout` (seconds) — default `15`
- `explicit.wait.timeout` (seconds) — default `20`
- `screenshot.on.failure` (`true` / `false`) — default `true`
- `screenshot.path` — default `target/screenshots`
- `retry.count` — default `0`

Run a compile-only build:

```bash
mvn -DskipTests=true verify
```

Run the full test suite (headless Chrome by default in CI; for local runs set the properties as needed):

```bash
mvn test -DBROWSER=chrome -DHEADLESS=true -DBASE_URL=https://opensource-demo.orangehrmlive.com
```

Run only tests with a specific cucumber tag (example `@pim` or `@navigation`):

```bash
mvn test -Dcucumber.filter.tags="@pim"
```

Run a single feature file (example):

```bash
mvn test -Dcucumber.features="src/test/resources/features/admin.feature"
```

Continuous Integration (GitHub Actions)
------------------------------------
We provide a workflow at `.github/workflows/ci.yml` which:

- checks out the code
- sets up Temurin JDK 24
- caches `~/.m2/repository`
- installs Google Chrome on the Ubuntu runner
- runs `mvn -DskipTests=true verify`
- runs `mvn test` with environment variables set for headless run
- uploads `target/surefire-reports` and `target/cucumber.json` as artifacts

Important: set repository secret `BASE_URL` if you want CI to target a custom environment. The workflow sets `BASE_URL` default to `https://opensource-demo.orangehrmlive.com` if the secret is not present.

Project structure overview
--------------------------
Top-level relevant files and folders:

- `pom.xml` — Maven project definition and plugin versions
- `testng.xml` — TestNG suite used by the Surefire plugin
- `src/test/resources/features/` — Gherkin feature files
- `src/test/java/steps/` — Cucumber step definition classes
- `src/test/java/pages/` — Page Object classes
- `src/test/java/actions/` — Action layer (higher-level flows)
- `src/test/java/hooks/` — Cucumber hooks (Before/After)
- `src/test/java/driver/DriverFactory.java` — WebDriver lifecycle
- `src/test/resources/config/config.properties` — default runtime properties

How to add a feature
--------------------
1. Create a Gherkin file under `src/test/resources/features`, e.g. `admin.feature`.
2. Add step definitions under `src/test/java/steps`. Use the existing package naming and follow existing step method patterns.
3. Implement page-level locators and interactions under `src/test/java/pages`.
4. Add higher-level flows in `src/test/java/actions` when multiple page interactions are combined.

Conventions and tips
--------------------
- Keep feature Background minimal: log in only — navigation should be performed from a reusable step that accepts the module name as a string.
- Use the Action layer from step definitions to keep step code readable and high-level.
- Reuse `DriverFactory` to obtain the WebDriver and always rely on hooks to initialize/quit the driver.
- Keep locators in `Page` classes and avoid mixing waits or assertions in them; return data to be asserted in `Steps`.

Troubleshooting
---------------
- WebDriver not initialized (IllegalStateException from `DriverFactory.getDriver()`): ensure hooks call `DriverFactory.initDriver()` before step code that accesses the driver.
- Chromedriver issues on local machine: Selenium Manager (bundled with Selenium 4) will typically download the matching driver; ensure your Chrome installation matches a supported version or install Chrome on CI runners.
- JDK mismatch: If your local JDK differs from the version in `pom.xml` (24), adjust either `pom.xml` or your local JDK.

CI debugging tips
-----------------
- Download artifacts from the Actions run (test reports, cucumber.json, screenshots) to inspect failures.
- Re-run a workflow with SSH or with a matrix including a debug variant (you can modify the workflow to run non-headless for debugging on a self-hosted runner).

Contributing
------------
1. Fork and branch from `main`.
2. Keep changes small and focused per PR.
3. Add/update feature files and corresponding step implementations.
4. Ensure `mvn -DskipTests=true verify` succeeds and that any new tests run in CI.

License
-------
This repository includes a placeholder MIT license badge. Add a `LICENSE` file or replace the badge with the correct license for your project.

Contact / Questions
-------------------
If you want help extending the framework (more actions, robust waits, CI matrix runs or parallel TestNG configurations), open an issue or ask via your team's preferred channel.

---
_Generated README — edit OWNER/REPO in the badge links to point to your repository._

