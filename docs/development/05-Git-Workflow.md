# 05 - Git Workflow

## Overview

This document defines the Git branching strategy, commit conventions, and Pull Request (PR) workflow followed during the development of UAF (Unified Automation Framework).

The objective is to keep the repository organized, maintain a clean commit history, and ensure that every change is reviewed before merging.

---

# Branching Strategy

UAF follows a simplified Git Flow model.

```

                main
                  │
                  │
             develop
                  │
      ┌───────────┼────────────┐
      │           │            │
      ▼           ▼            ▼
 feature/*   release/*    hotfix/*

```

## Branch Responsibilities

### main

Contains production-ready code.

Rules

- Never commit directly.
- Only merge reviewed code.
- Every commit should be stable.

---

### develop

Integration branch.

Responsibilities

- Collect completed features.
- Prepare the next release.

---

### feature/*

Used for new framework enhancements.

Examples

```

feature/framework-core

feature/reporting

feature/exceptions

feature/framework-lifecycle

feature/api-framework

```

Each feature branch should solve only one problem.

---

### release/*

Used when preparing a stable framework release.

Example

```

release/v2.1

```

---

### hotfix/*

Used only for urgent fixes in production.

Example

```

hotfix/report-generation

```

---

# Development Workflow

Every enhancement follows the same lifecycle.

```

Requirement

↓

Feature Branch

↓

Development

↓

Smoke Test

↓

Pull Request

↓

Code Review

↓

Approval

↓

Merge into develop

↓

Release

```

---

# Commit Message Convention

UAF follows the Conventional Commits style.

## Format

```

<type>(scope): description

```

Examples

```

feat(driver): add BrowserOptionsFactory

refactor(report): simplify ExtentManager

fix(excel): improve workbook handling

docs(architecture): update design decisions

test(login): add invalid login scenarios

```

---

# Pull Request Guidelines

Each Pull Request should:

- Solve one problem only.
- Include a meaningful title.
- Pass smoke tests.
- Be reviewed before merging.
- Keep changes focused and easy to understand.

Avoid combining unrelated changes in the same PR.

---

# Code Review Process

Every Pull Request is reviewed using the following checklist.

## Architecture

- Single Responsibility Principle
- No unnecessary abstractions
- Proper package placement

## Code Quality

- No duplicate code
- Meaningful method names
- Small focused methods

## Exception Handling

- No RuntimeException
- Framework-specific exceptions
- Meaningful messages

## Logging

- Parameterized logging
- Useful log messages

## Paths

- Use FrameworkPaths
- No hardcoded paths

## Testing

- Smoke test passed
- Existing functionality verified

---

# Merge Strategy

Use **Squash and Merge** for feature branches whenever appropriate.

Benefits

- Cleaner Git history
- One commit per feature
- Easier rollback

---

# Versioning

U   AF follows Semantic Versioning.

Format

```

MAJOR.MINOR.PATCH

```

Example

```

2.0.0

```

Meaning

MAJOR

Breaking architectural changes.

MINOR

New features.

PATCH

Bug fixes.

---

# Git Best Practices

- Commit frequently.
- Keep commits focused.
- Never commit generated reports.
- Never commit IDE-specific files.
- Review changes before committing.
- Pull latest changes before starting new work.

---

# Files Excluded from Git

Examples

```

reports/

target/

logs/

.idea/

*.iml

```

These files should remain in `.gitignore`.

---

# Release Strategy

Every release should include:

- Updated documentation.
- Updated changelog.
- Smoke test report.
- Version increment.

---

# Branch Naming Standards

Feature

feature/<feature-name>

Examples

feature/framework-core

feature/reporting

feature/api-framework

Bug Fix

bugfix/<bug-name>

Hotfix

hotfix/<issue-name>

Documentation

docs/<topic>

Examples

docs/architecture

docs/coding-standards

---

## Pull Request Template

### Summary

Describe the purpose of the change.

### Changes

- Item 1
- Item 2
- Item 3

### Testing

- Smoke test completed
- Existing functionality verified

### Checklist

- [ ] Code reviewed
- [ ] Documentation updated
- [ ] No duplicated code
- [ ] No RuntimeException
- [ ] All tests passed

# Conclusion

A disciplined Git workflow ensures that UAF remains maintainable, reviewable, and easy to evolve.

Small feature branches, focused commits, and structured code reviews reduce risk while improving code quality and collaboration.