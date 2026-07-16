# 02 - Project Structure

## Overview

UAF (Unified Automation Framework) follows a layered and modular package structure designed to improve maintainability, scalability, readability, and code reusability.

Each package has a single responsibility and follows the **Single Responsibility Principle (SRP)**. Business test scenarios are kept separate from framework infrastructure, allowing the framework to evolve independently of the application under test.

---

# Project Structure

```
UAF
│
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   │
│   └── test
│       ├── java
│       └── resources
│
├── reports
├── docs
├── pom.xml
└── README.md
```

---

# src/main/java

The `src/main/java` directory contains the reusable automation framework implementation.

No application-specific test scenarios should be placed here.

---

# Package Overview

| Package | Responsibility |
|----------|----------------|
| base | Test lifecycle management |
| config | Configuration management |
| constants | Framework constants |
| core | Framework infrastructure |
| dataprovider | Test data providers |
| driver | Browser lifecycle management |
| enums | Enumerations |
| exceptions | Framework exceptions |
| listeners | TestNG listeners |
| pages | Page Object Model |
| reports | Reporting infrastructure |
| utilities | Reusable helper classes |

---

# base

## Purpose

Contains the common TestNG lifecycle implementation used by all test classes.

## Key Class

```
BaseTest
```

## Responsibilities

- Browser setup
- Browser teardown
- Common initialization
- Test lifecycle management

## Design Decision

Every test class extends `BaseTest` to eliminate duplicated setup and cleanup code.

## Do

- Common setup
- Common teardown

## Don't

- Business test logic
- Assertions
- Page-specific functionality

---

# config

## Purpose

Provides centralized configuration management.

## Key Classes

```
ConfigurationManager
ConfigReader
```

## Responsibilities

- Load environment-specific properties
- Read configuration values
- Support runtime overrides using JVM system properties

## Design Decision

Configuration loading is separated from property reading.

ConfigurationManager decides **which** configuration file to load, while ConfigReader provides access to configuration values.

## Do

- Environment configuration
- Runtime property resolution

## Don't

- Framework business logic

---

# constants

## Purpose

Stores reusable framework constants.

## Current Classes

```
ReportConstants
```

## Future

- FrameworkConstants
- APIConstants

## Design Decision

Only framework-wide constants belong here.

Environment-specific values belong in configuration files.

---

# core

## Purpose

Contains the framework's core infrastructure.

## Key Classes

```
ExecutionContext
FrameworkPaths
DirectoryManager
```

## Responsibilities

- Execution metadata
- Framework directory management
- Centralized path handling

## Design Decision

Infrastructure responsibilities are centralized to avoid duplicated path and directory creation logic.

---

# dataprovider

## Purpose

Provides reusable TestNG DataProviders.

## Key Classes

```
LoginDataProvider
```

## Responsibilities

- Prepare test data
- Delegate Excel operations to ExcelUtility
- Filter test scenarios

## Design Decision

DataProviders should only prepare test data.

Reading Excel files is delegated to ExcelUtility.

## Do

- Prepare Object[][] for TestNG
- Filter test data

## Don't

- Read Excel manually
- Implement business logic

---

# driver

## Purpose

Manages the browser lifecycle.

## Key Classes

```
DriverFactory
DriverManager
BrowserOptionsFactory
```

## Responsibilities

- Browser initialization
- Browser configuration
- Driver storage
- Driver cleanup

## Design Pattern

Factory Pattern

ThreadLocal Pattern

## Design Decision

Driver creation and driver storage are intentionally separated to support parallel execution.

---

# enums

## Purpose

Contains framework enumerations.

## Current Enums

```
BrowserType
```

## Future

- EnvironmentType
- ExecutionMode

---

# exceptions

## Purpose

Contains the framework exception hierarchy.

## Classes

```
FrameworkException

ConfigurationException

DriverInitializationException

DataProviderException

ExcelOperationException

ReportException

ScreenshotException
```

## Design Decision

Each framework layer throws its own exception instead of generic RuntimeException.

This simplifies debugging and improves readability.

---

# listeners

## Purpose

Contains TestNG listeners.

## Responsibilities

- Reporting
- Screenshot capture
- Test lifecycle events

## Current Classes

```
TestListener
```

## Future

- RetryListener
- SuiteListener

---

# pages

## Purpose

Implements the Page Object Model.

## Responsibilities

- Store locators
- Perform page interactions
- Navigate between pages

## Design Decision

Assertions are intentionally excluded.

Business validation belongs in the test layer.

## Do

- Click
- Type
- Select
- Read UI data

## Don't

- Assertions
- TestNG annotations
- Business logic

---

# reports

## Purpose

Provides reporting infrastructure.

## Classes

```
ExtentManager

ExtentLogger

ExtentTestManager

ReportConstants
```

## Responsibilities

- Report creation
- Step logging
- Screenshot attachment
- Report flushing

## Design Decision

Report generation is independent of framework configuration.

---

# utilities

## Purpose

Contains reusable helper classes.

## Current Utilities

```
ExcelUtility

DateUtility

WaitUtility

ScreenshotUtility

JavaScriptUtility
```

## Responsibilities

Provide reusable functionality that can be shared across the framework.

## Design Decision

Utilities should remain generic and independent of business test scenarios.

---

# src/main/resources

Contains framework resources.

Examples

- Configuration files
- Test data
- Log4j configuration
- Browser-specific resources

---

# src/test/java

Contains business test scenarios.

Only test classes should be placed here.

Framework implementation should never be written under `src/test/java`.

---

# Package Dependency Rules

The following dependency rules help maintain a clean architecture.

| Package | Can Depend On |
|----------|---------------|
| testscripts | pages, dataprovider, reports |
| pages | base, driver, utilities |
| driver | config, core, enums |
| reports | core |
| utilities | core, exceptions |
| dataprovider | utilities, config |
| core | Java SDK only (where practical) |

The framework avoids circular dependencies and ensures that lower-level infrastructure remains independent of higher-level business logic.

---

# Package Design Principles

The project structure follows these engineering principles:

- One responsibility per package.
- Business tests are separated from framework infrastructure.
- Framework services are reusable across projects.
- Utilities remain generic and stateless where possible.
- Dependencies flow from higher-level modules to lower-level infrastructure.

---

# Conclusion

The UAF package structure has been designed to support scalability, maintainability, and long-term evolution.

By clearly separating framework infrastructure from business test implementation, new capabilities such as API automation, database validation, Selenium Grid, Docker execution, and AI-assisted testing can be integrated without requiring major architectural changes.