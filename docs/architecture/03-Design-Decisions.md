# 03 - Design Decisions

## Overview

This document records the major architectural and engineering decisions made during the design and development of UAF (Unified Automation Framework).

Each decision captures:

- The problem being solved
- The selected approach
- Alternative approaches considered
- The reasoning behind the decision
- Trade-offs

The purpose of this document is to help future contributors understand the rationale behind the framework instead of relying solely on the implementation.

---

# DD-001 : Page Object Model (POM)

## Problem

Directly interacting with Selenium WebDriver inside test classes makes tests difficult to maintain and causes duplicated locator logic.

## Decision

Implement the Page Object Model (POM).

## Why?

- Separates UI interactions from business tests.
- Improves maintainability.
- Centralizes locators.
- Encourages code reuse.
- Reduces duplication.

## Alternatives Considered

Writing Selenium code directly inside test classes.

## Why Rejected

- Poor maintainability.
- Duplicate locators.
- High coupling between tests and UI.

---

# DD-002 : BasePage

## Problem

Every Page Object required common Selenium operations such as click, type, select, waits, scrolling, and JavaScript execution.

## Decision

Introduce BasePage.

## Why?

- Eliminates duplicate code.
- Provides a common API for all pages.
- Makes future Selenium changes centralized.

## Alternatives Considered

Implement click(), type(), etc., inside every Page Object.

## Why Rejected

Large code duplication and difficult maintenance.

---

# DD-003 : DriverFactory + DriverManager

## Problem

Browser creation and browser storage are separate responsibilities.

## Decision

Separate browser creation from browser management.

## Why?

DriverFactory

- Creates browsers.

DriverManager

- Stores ThreadLocal WebDriver.

This follows the Single Responsibility Principle.

## Alternatives Considered

Keep WebDriver directly inside BaseTest.

## Why Rejected

Tightly coupled design.

Poor support for parallel execution.

---

# DD-004 : ThreadLocal WebDriver

## Problem

Parallel execution requires isolated browser instances.

## Decision

Use ThreadLocal<WebDriver>.

## Why?

- Thread-safe.
- Supports parallel execution.
- Eliminates browser collisions.

## Alternatives Considered

Static WebDriver.

## Why Rejected

Static WebDriver is not thread-safe.

---

# DD-005 : BrowserOptionsFactory

## Problem

Browser options were becoming duplicated across multiple browsers.

## Decision

Introduce BrowserOptionsFactory.

## Why?

- Centralized browser configuration.
- Easier maintenance.
- Supports future browser additions.

## Alternatives Considered

Configure options directly inside DriverFactory.

## Why Rejected

DriverFactory would become unnecessarily large.

---

# DD-006 : ConfigurationManager + ConfigReader

## Problem

Loading configuration files and reading configuration values are different responsibilities.

## Decision

Split configuration into two classes.

## Why?

ConfigurationManager

- Determines which configuration file to load.

ConfigReader

- Reads configuration values.

## Alternatives Considered

One ConfigReader class.

## Why Rejected

Mixed responsibilities.

Violates SRP.

---

# DD-007 : FrameworkPaths

## Problem

Framework paths were duplicated throughout the project.

## Decision

Centralize path management.

## Why?

- Single source of truth.
- Cross-platform compatibility.
- Easy maintenance.

## Alternatives Considered

Hardcoded paths.

## Why Rejected

Scattered path logic.

Higher maintenance cost.

---

# DD-008 : DirectoryManager

## Problem

Multiple classes were creating directories independently.

## Decision

Centralize directory creation.

## Why?

- One responsibility.
- Cleaner utilities.
- No duplicated directory creation logic.

## Alternatives Considered

Each utility creates its own directories.

## Why Rejected

Duplicate code.

Inconsistent behavior.

---

# DD-009 : Java NIO Path API

## Problem

Legacy File API requires string manipulation and provides limited path handling.

## Decision

Use java.nio.file.Path wherever practical.

## Why?

- Cross-platform.
- Modern Java API.
- Cleaner path composition.
- Uses resolve() instead of string concatenation.

## Alternatives Considered

java.io.File

## Why Rejected

Older API.

Less expressive.

---

# DD-010 : Extent Reporting Architecture

## Problem

Reporting responsibilities were mixed.

## Decision

Separate reporting into:

- ExtentManager
- ExtentLogger
- ExtentTestManager

## Why?

Each class has a single responsibility.

## Alternatives Considered

One large report utility.

## Why Rejected

Hard to maintain.

Violates SRP.

---

# DD-011 : Custom Exception Hierarchy

## Problem

RuntimeException does not communicate where failures originate.

## Decision

Introduce framework-specific exceptions.

FrameworkException

- ConfigurationException
- DriverInitializationException
- DataProviderException
- ExcelOperationException
- ScreenshotException
- ReportException

## Why?

- Better debugging.
- Layer-specific failures.
- Cleaner architecture.

## Alternatives Considered

RuntimeException.

## Why Rejected

Generic.

Poor readability.

---

# DD-012 : DataProvider Delegation

## Problem

DataProviders were directly handling Excel operations.

## Decision

Delegate Excel operations to ExcelUtility.

## Why?

DataProviders prepare test data.

ExcelUtility reads Excel.

Responsibilities remain separated.

## Alternatives Considered

Read Excel directly inside DataProviders.

## Why Rejected

Duplicated logic.

Poor maintainability.

---

# DD-013 : Assertions in Test Classes

## Problem

Where should assertions be placed?

## Decision

Assertions belong only inside test classes.

## Why?

Page Objects should represent UI behavior.

Business validation belongs in tests.

## Alternatives Considered

Assertions inside Page Objects.

## Why Rejected

Mixes UI interaction with business validation.

---

# DD-014 : Fluent Interface

## Problem

Repeated page object references reduced readability.

## Decision

Return the current page object from page methods.

Example

loginPage

.enterUsername()

.enterPassword()

.clickLogin();

## Why?

Improves readability.

Supports method chaining.

## Alternatives Considered

Void methods.

## Why Rejected

More verbose code.

---

# DD-015 : try-with-resources

## Problem

Excel resources must always be closed.

## Decision

Implement AutoCloseable and use try-with-resources.

## Why?

Automatic resource cleanup.

Cleaner code.

Reduced memory leaks.

## Alternatives Considered

Manual close().

## Why Rejected

Easy to forget.

Resource leaks.

---

# DD-016 : Layered Package Structure

## Problem

Large frameworks become difficult to navigate.

## Decision

Organize packages by responsibility.

## Why?

Improves maintainability.

Simplifies onboarding.

Supports future expansion.

---

# Future Design Decisions

The following architectural decisions are planned for future versions.

- Framework Lifecycle
- Dynamic Logging
- Selenium Grid
- Docker Execution
- REST API Layer
- Database Layer
- AI-assisted Testing
- Plugin Architecture

---

# DD-017: Centralized Report and Screenshot Path Management

## Problem

Report and screenshot paths were stored in configuration files, mixing framework implementation details with environment configuration.

## Decision

Centralize report and screenshot paths using FrameworkPaths.

## Why?

Environment files contain only environment-specific settings.
Framework paths become implementation details managed in one place.
Easier maintenance and cross-platform compatibility.

## Alternative Considered

Store report.path and screenshot.path in config-*.properties.

## Why Rejected

These are framework concerns, not environment concerns.

---

## DD-018: Incremental Refactoring Strategy

## Problem

Large-scale refactoring increases risk and makes reviews difficult.

## Decision

Refactor the framework using small feature branches and pull requests (PR-1, PR-2, PR-3...).

## Why?

Easier code reviews.
Lower risk.
Easier rollback.
Better Git history.
Continuous smoke testing after each change.

## Alternative Considered

Refactor everything in a single commit.

## Why Rejected

High risk and difficult to review or troubleshoot.

# Conclusion

Every architectural decision in UAF has been made with maintainability, scalability, readability, and long-term evolution in mind.

The framework emphasizes simplicity, clear responsibilities, and clean engineering practices over unnecessary abstraction.