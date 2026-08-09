# QA Selenium Automation Assignment

## Overview

This project is a production-ready Selenium Automation Framework developed using **Java, Selenium WebDriver, TestNG, Maven, Apache POI, Log4j2, and Extent Reports**.

The framework follows the **Page Object Model (POM)** design pattern and is designed with maintainability, reusability, scalability, and thread-safe execution in mind.

The framework demonstrates automation for two web applications:

- **OrangeHRM**
- **ERail**

# Assumptions

- Java 21 is installed.
- Maven is configured in the system PATH.
- Supported browsers are installed on the execution machine.
- Internet connection is required to access the application under test.

---

# Technology Stack

- Java 21
- Selenium WebDriver 4
- TestNG
- Maven
- Apache POI
- Log4j2
- Extent Reports

---

# Framework Design

The framework is designed using industry-standard automation practices.

## Key Features

- Page Object Model (POM)
- Thread-safe WebDriver using ThreadLocal
- Browser Factory Design
- Browser Options Factory
- Generic BasePage
- Explicit Wait Utility
- JavaScript Utility
- Screenshot Utility
- Date Utility
- Excel Utility for Data Driven Testing
- Log4j2 Logging
- Extent Reporting
- Configurable Environment using Properties Files
- Generic Auto Suggestion Handling
- Dynamic Journey Date Selection

---

# Project Structure

```
QA-Selenium-Assignment
│
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── base
│   │   │   ├── config
│   │   │ 
│   │   │   ├── driver
│   │   │   ├── enums
│   │   │   ├── pages
│   │   │   ├── reports
│   │   │   └── utilities
│   │   │
│   │   └── resources
│   │       ├── config
│   │       ├── data
│   │       └── log4j2.xml
│   │
│   └── test
│       └── java
│           ├── base
│           ├── dataprovider
│           ├── listeners
│           └── tests
│
├── pom.xml
├── testng.xml
└── README.md
```

---

# Design Patterns Used

- Page Object Model (POM)
- Factory Pattern
- Singleton Pattern (ExtentReports)
- ThreadLocal WebDriver Management

---

# Test Scenarios

## OrangeHRM

- Verify Valid Login
- Verify Invalid Login
- Verify Mandatory Field Validation
- Data Driven Testing using Excel

---

## ERail

- Verify Source Station Auto Suggestions
- Compare Actual vs Expected Station List
- Select Future Journey Date
- Search Trains
- Verify Search Results

---

# Reporting

The framework generates:

- Extent Reports
- Log4j2 Execution Logs
- Screenshot Capture on Failure

---

# Browser Support

The framework supports:

- Google Chrome
- Mozilla Firefox

Browser can be selected either from the configuration file or at runtime.

Example:

```bash
mvn clean test -Dbrowser=chrome

mvn clean test -Dbrowser=firefox

```

---

# Framework Highlights

- Thread-safe WebDriver implementation
- Generic reusable framework components
- Business-level logging using Extent Reports
- Technical logging using Log4j2
- Data-driven testing using Apache POI
- Dynamic browser selection
- Generic wait implementation
- Generic screenshot utility
- Generic JavaScript utility
- Generic Auto Suggestion handling

---

# Execution

Run all tests

```bash
mvn clean test
```

Run on Chrome

```bash
mvn clean test -Dbrowser=chrome
```

Run on Firefox

```bash
mvn clean test -Dbrowser=firefox
```

---

# Notes

- Browser can be overridden using JVM system properties.
- Test data is maintained externally in Excel files.
- Configuration is maintained separately from the source code.
- The framework is designed to be easily extensible for additional browsers and test scenarios.

---

# Author

**Santosh Choudhary**

Senior QA Automation Engineer
