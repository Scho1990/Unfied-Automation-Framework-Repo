# Changelog

All notable changes to UAF will be documented in this file.

The project follows Semantic Versioning.

---

# [2.0.0] - Initial Enterprise Framework

## Added

### Core Infrastructure

- ExecutionContext
- FrameworkPaths
- DirectoryManager

### Driver Layer

- DriverFactory
- DriverManager
- BrowserOptionsFactory

### Reporting

- ExtentManager
- ExtentLogger
- ExtentTestManager
- ReportConstants

### Exception Framework

- FrameworkException
- ConfigurationException
- DriverInitializationException
- DataProviderException
- ExcelOperationException
- ScreenshotException
- ReportException

### Utilities

- ExcelUtility
- WaitUtility
- ScreenshotUtility
- DateUtility
- JavaScriptUtility

### Documentation

- Architecture Guide
- Project Structure
- Design Decisions
- Coding Standards
- Git Workflow
- Roadmap
- Code Review Checklist
- Contributing Guide
- Ideas

---

## Changed

- Centralized framework paths.
- Refactored reporting infrastructure.
- Refactored screenshot handling.
- Introduced BrowserOptionsFactory.
- Improved exception handling.
- Improved DataProvider architecture.
- Improved package organization.

---

## Removed

- report.path from configuration.
- screenshot.path from configuration.
- Generic RuntimeException usage.

---

## Fixed

- Duplicate framework path creation.
- Multiple reporting responsibilities.
- Resource cleanup in ExcelUtility.
- Exception consistency across framework.