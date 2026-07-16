# 07 - Code Review Checklist

## Overview

This document defines the code review checklist followed during the development of UAF (Unified Automation Framework).

The purpose of code reviews is to maintain code quality, enforce engineering standards, and ensure long-term maintainability.

Every Pull Request should be reviewed against the following checklist before approval.

---

# 1. Architecture Review

## Verify

- [ ] Class follows the Single Responsibility Principle (SRP).
- [ ] New code fits into the existing architecture.
- [ ] No unnecessary abstractions have been introduced.
- [ ] Package placement is appropriate.
- [ ] Dependencies flow in the correct direction.
- [ ] No circular dependencies.

---

# 2. Code Quality

## Verify

- [ ] No duplicated code.
- [ ] Methods are small and focused.
- [ ] Meaningful class names.
- [ ] Meaningful method names.
- [ ] Meaningful variable names.
- [ ] No dead code.
- [ ] No commented-out code.

---

# 3. Exception Handling

## Verify

- [ ] No RuntimeException is thrown directly.
- [ ] Framework-specific exceptions are used.
- [ ] Exception messages are meaningful.
- [ ] Specific exceptions are caught instead of generic Exception.
- [ ] Original exception (cause) is preserved where applicable.

---

# 4. Logging

## Verify

- [ ] Parameterized logging is used.
- [ ] Log messages describe meaningful events.
- [ ] No unnecessary debug logging.
- [ ] Errors include sufficient context.

---

# 5. Thread Safety

## Verify

- [ ] ThreadLocal is used correctly.
- [ ] No shared mutable state.
- [ ] Static fields are immutable where possible.

---

# 6. Page Object Model

## Verify

- [ ] Assertions are not placed inside Page Objects.
- [ ] Page Objects contain only UI interactions.
- [ ] Business logic remains in test classes.
- [ ] Method chaining is used consistently where appropriate.

---

# 7. Utilities

## Verify

- [ ] Utility methods are generic.
- [ ] No business-specific logic.
- [ ] Utility classes remain reusable.

---

# 8. Configuration

## Verify

- [ ] Environment-specific values remain in configuration files.
- [ ] Framework implementation details are not configurable.
- [ ] No hardcoded URLs or credentials.

---

# 9. Path Management

## Verify

- [ ] FrameworkPaths is used.
- [ ] No string concatenation for paths.
- [ ] Java Path API is preferred.

---

# 10. Reports

## Verify

- [ ] Reporting changes follow the reporting architecture.
- [ ] Screenshots are attached where appropriate.
- [ ] No duplicated reporting logic.

---

# 11. Documentation

## Verify

- [ ] Javadocs added where necessary.
- [ ] Markdown documentation updated.
- [ ] Design Decisions updated (if applicable).
- [ ] CHANGELOG updated (if applicable).

---

# 12. Testing

## Verify

- [ ] Smoke test completed.
- [ ] Existing functionality verified.
- [ ] No regression introduced.
- [ ] Reports generated successfully.

---

# 13. Git

## Verify

- [ ] Feature branch used.
- [ ] Commit messages follow conventions.
- [ ] Pull Request description is complete.
- [ ] Branch is up to date before merging.

---

# Framework Evolution Checklist

Before introducing a new class or feature, ask:

- Does it solve a real problem?
- Can an existing class be extended instead?
- Does it follow the current architecture?
- Will another engineer understand it easily?
- Does it reduce duplication?
- Is it documented?

# Pull Request Approval Criteria

A Pull Request is ready for approval when:

- All checklist items have been reviewed.
- Smoke tests pass successfully.
- Documentation is updated where required.
- Code review comments have been addressed.
- The reviewer is satisfied with the overall design and implementation.

---

# Guiding Principle

The goal of code review is not only to identify defects but also to improve code readability, maintainability, and architectural consistency.

Reviews should remain constructive, respectful, and focused on improving the framework.