# merge-queue-test

Experimentation repo for GitHub Merge Queue before enabling it on ndc-hub.

## Purpose

Validate merge queue behavior in a low-risk environment:
- Happy path: single PR queued and merged
- Batching: multiple PRs queued simultaneously
- Batch failure: one bad PR ejected, others continue
- Conflict handling: two PRs touching the same file
- Stacked PRs: base must merge before stacked can be enqueued

## Repo Structure

```
.github/workflows/ci.yml   # CI workflow (uses GitHub-hosted runners)
src/calculator.kt          # Sample source file — modify in PRs to create conflicts
SETUP.md                   # Branch protection configuration steps
SCENARIOS.md               # Test scenarios to run
```

## Quick Start

1. Follow [SETUP.md](SETUP.md) to enable merge queue on `main`
2. Run scenarios in [SCENARIOS.md](SCENARIOS.md) in order
3. Record observations — then apply learnings to ndc-hub
