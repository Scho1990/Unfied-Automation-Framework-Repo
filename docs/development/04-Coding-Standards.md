# 04 - Coding Standards

## Overview

This document defines the coding standards and engineering guidelines followed by UAF (Unified Automation Framework).

The primary goals of these standards are:

- Improve code readability.
- Maintain consistency across the framework.
- Reduce code duplication.
- Encourage clean architecture.
- Simplify maintenance.
- Support long-term scalability.

All framework contributors are expected to follow these standards.

---

# 1. General Principles

The framework follows these engineering principles:

- Single Responsibility Principle (SRP)
- Don't Repeat Yourself (DRY)
- Keep It Simple (KISS)
- Separation of Concerns
- Composition over unnecessary inheritance
- Readability over cleverness

---

# 2. Package Organization

## Rule

Every package must have a single responsibility.

### Good

```
driver
reports
utilities
exceptions
```

### Bad

```
common
helper
misc
utils
```

These packages become dumping grounds over time.

---

# 3. Class Design

## Rule

Every class should have one clear responsibility.

### Good

```
DriverFactory
```

Creates browser instances only.

```
DriverManager
```

Stores WebDriver only.

### Bad

```
DriverFactory
```

Creates drivers

Stores drivers

Captures screenshots

Reads configuration

---

# 4. Method Design

## Rule

Methods should perform one task only.

### Good

```java
initializeDriver()
```

### Bad

```java
initializeDriverAndOpenApplication()
```

---

## Rule

Prefer small methods.

Aim for approximately **20–30 lines**.

Extract private helper methods whenever appropriate.

---

# 5. Naming Conventions

## Classes

Use PascalCase.

### Good

```
DriverFactory
ExecutionContext
FrameworkPaths
```

### Bad

```
driverFactory
driver_factory
DF
```

---

## Methods

Use camelCase.

Methods should describe an action.

### Good

```
initializeDriver()

captureScreenshot()

getExecutionDirectory()
```

### Bad

```
doDriver()

clickBtn()

init()
```

---

## Variables

Use meaningful names.

### Good

```java
executionDirectory
```

### Bad

```java
dir
```

---

## Constants

Use UPPER_SNAKE_CASE.

### Good

```java
REPORT_FILE_NAME

LOGIN_SHEET
```

---

# 6. Exception Handling

## Rule

Never throw RuntimeException directly.

Always throw framework-specific exceptions.

### Good

```java
throw new ConfigurationException(...)
```

### Bad

```java
throw new RuntimeException(...)
```

---

## Rule

Catch the most specific exception possible.

### Good

```java
catch (IOException e)
```

### Bad

```java
catch (Exception e)
```

---

## Rule

Exception messages should:

- Start with a capital letter.
- End with a period.
- Clearly explain the problem.

---

# 7. Logging

## Rule

Always use parameterized logging.

### Good

```java
logger.info("Launching {} browser.", browserType);
```

### Bad

```java
logger.info("Launching " + browserType);
```

---

## Rule

Log meaningful events.

Examples

- Browser launched
- Test started
- Screenshot captured
- Configuration loaded

Avoid logging unnecessary implementation details.

---

# 8. Path Handling

## Rule

Never build paths manually.

### Good

```java
FrameworkPaths
        .getExecutionDirectory()
        .resolve(fileName);
```

### Bad

```java
"user.dir" + "/reports/" + fileName
```

---

# 9. Configuration

## Rule

Configuration files should contain only environment-specific values.

Examples

- Base URL
- Browser
- Timeout
- Headless mode

Framework implementation details should not be stored in configuration files.

---

# 10. Utilities

## Rule

Utilities should remain generic.

### Good

```
DateUtility
WaitUtility
ExcelUtility
```

### Bad

```
OrangeHRMUtility
```

Business-specific utilities should not exist.

---

# 11. Page Object Model

## Rule

Page Objects represent UI only.

Responsibilities

- Locators
- Click
- Type
- Select
- Read UI data

Do NOT include:

- Assertions
- TestNG annotations
- Business validations

---

# 12. Test Classes

Test classes should contain only:

- Business flow
- Assertions
- DataProvider usage

Framework logic belongs elsewhere.

---

# 13. Thread Safety

Use ThreadLocal for shared execution resources.

Avoid static mutable variables.

---

# 14. Code Duplication

If identical code appears more than twice, consider extracting it into a reusable method.

Follow the DRY principle.

---

# 15. Java Best Practices

Prefer:

- Path over File
- Files over File utilities (where practical)
- try-with-resources
- AutoCloseable
- String.formatted()

Avoid:

- Manual resource cleanup
- String concatenation for paths
- Deprecated APIs

---

# 16. Git Standards

Every feature should be developed in its own branch.

Example

```
feature/framework-core

feature/framework-exceptions

feature/framework-lifecycle
```

Commit messages should clearly describe the change.

Example

```
refactor(driver): simplify browser initialization

feat(report): add centralized report constants

fix(excel): improve workbook exception handling
```

---

# 17. Code Review Checklist

Before creating a Pull Request, verify:

✅ No duplicated code

✅ No RuntimeException

✅ Meaningful logging

✅ Meaningful exception messages

✅ Single Responsibility Principle

✅ Cross-platform paths

✅ Smoke test passed

✅ No unused imports

✅ Code formatted

---

# 18. Future Standards

As UAF evolves, additional standards will be introduced for:

- API automation
- Database validation
- Docker execution
- Selenium Grid
- AI-assisted testing

---

# 19. Documentation Standards

Every new feature should include updates to the appropriate documentation.

Examples:

- Architecture changes → 01-Architecture.md
- New design decisions → 03-Design-Decisions.md
- New coding rules → 04-Coding-Standards.md
- Version changes → CHANGELOG.md

---

# 20. Pull Request Standards

Each Pull Request should:

- Solve one problem only.
- Include a clear description.
- Pass smoke tests.
- Be reviewed before merging.
- Keep changes focused and easy to review.

---

# Conclusion

Following these coding standards ensures that UAF remains readable, maintainable, scalable, and consistent as it evolves.

Consistency is valued over individual coding preferences.