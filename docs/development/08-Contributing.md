# 06 - Contributing

## Welcome

Thank you for your interest in contributing to UAF (Unified Automation Framework).

This document describes the engineering standards, development workflow, and contribution process followed by the project.

The objective is to ensure that every contribution maintains the framework's architecture, quality, and long-term maintainability.

---

# Before You Start

Before contributing, please read the following documents:

1. 01-Architecture.md
2. 02-Project-Structure.md
3. 03-Design-Decisions.md
4. 04-Coding-Standards.md
5. 05-Git-Workflow.md
6. 07-Code-Review-Checklist.md

Understanding the framework architecture is more important than writing code immediately.

---

# Development Environment

Ensure the following tools are installed:

- Java 21+
- Maven 3.9+
- Git
- Chrome Browser
- ChromeDriver (managed automatically by Selenium Manager)
- IntelliJ IDEA (Recommended)

---

# Getting Started

Clone the repository.

```bash
git clone <repository-url>
```

Create a new feature branch.

```bash
git checkout -b feature/<feature-name>
```

Example

```bash
git checkout -b feature/framework-lifecycle
```

---

# Development Guidelines

Every contribution should follow the established coding standards.

Key principles include:

- Follow SOLID principles.
- Avoid duplicated code.
- Keep methods small and focused.
- Use meaningful names.
- Follow the existing package structure.
- Do not introduce unnecessary abstractions.

---

# Pull Request Process

Every Pull Request should:

- Solve one problem only.
- Include a clear description.
- Pass smoke tests.
- Update documentation when required.
- Be reviewed before merging.

Large Pull Requests should be avoided.

---

# Coding Standards

Before submitting code, verify:

- No RuntimeException
- Framework-specific exceptions
- Parameterized logging
- Path API used
- No hardcoded values
- No duplicated code

Refer to:

04-Coding-Standards.md

---

# Documentation

Documentation is considered part of the codebase.

Whenever architecture or framework behavior changes, update the relevant documentation.

Examples:

Architecture changes

→ 01-Architecture.md

New design decision

→ 03-Design-Decisions.md

Coding rule

→ 04-Coding-Standards.md

Version changes

→ CHANGELOG.md

---

# Testing

Before submitting code:

Run a smoke test.

```bash
mvn clean test
```

Verify:

- Tests pass
- Reports generated
- Screenshots captured (where applicable)
- No regressions introduced

---

# Commit Messages

Follow the Conventional Commits style.

Examples

```text
feat(driver): add BrowserOptionsFactory

refactor(report): simplify ExtentManager

fix(excel): improve workbook handling

docs(architecture): update design decisions
```

---

# Code Review

Every contribution is reviewed using the Code Review Checklist.

Review focuses on:

- Architecture
- Maintainability
- Readability
- Testing
- Documentation

Code review is intended to improve the framework rather than criticize the contributor.

---

# Reporting Issues

When reporting a bug, include:

- Framework version
- Java version
- Browser
- Browser version
- Operating system
- Stack trace
- Steps to reproduce
- Expected behavior
- Actual behavior

---

# Feature Requests

Feature requests should include:

- Problem statement
- Proposed solution
- Expected benefits
- Possible alternatives
- Impact on the existing architecture

---

# Engineering Principles

Every contribution should aim to improve one or more of the following:

- Readability
- Maintainability
- Reusability
- Scalability
- Stability
- Performance

Avoid introducing complexity unless it solves a real problem.

---

# Contributor Checklist

Before opening a Pull Request, verify:

- [ ] Code follows coding standards.
- [ ] Smoke test passed.
- [ ] Documentation updated.
- [ ] No duplicated code.
- [ ] No unused imports.
- [ ] Logging reviewed.
- [ ] Exception handling reviewed.
- [ ] Meaningful commit message used.

---

# Engineering Philosophy

UAF favors:

- Simplicity over cleverness.
- Readability over brevity.
- Reusability over duplication.
- Incremental improvements over large rewrites.
- Stable architecture over rapid feature additions.

Every new feature should integrate naturally into the existing architecture rather than introducing isolated solutions.

# Thank You

Thank you for helping improve UAF.

Every contribution—whether code, documentation, testing, or design feedback—helps make the framework more maintainable, scalable, and valuable for future users.