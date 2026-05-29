# Merge Queue Test Scenarios

Run these scenarios in order. Each builds intuition for how merge queue behaves.

---

## Scenario 1 — Happy Path (Single PR)

**Goal**: Verify a single PR can be enqueued and merged.

1. Create a branch: `git checkout -b scenario-1-happy-path`
2. Edit `src/calculator.kt` — add a comment or change a value
3. Push and open a PR against `main`
4. Click **"Merge when ready"**
5. Observe: queue creates a temporary `gh-readonly-queue/main/...` branch, CI runs, PR merges

**Expected**: PR merges after CI passes (~30s)

---

## Scenario 2 — Batching (Multiple PRs)

**Goal**: Verify multiple PRs are batched into a single CI run.

1. Create 3 branches, each modifying a **different** function in `src/calculator.kt`
2. Open all 3 PRs against `main`
3. Enqueue all 3 by clicking **"Merge when ready"** on each (within the wait timer window)
4. Observe: GitHub batches them, runs CI once (or twice for 2+1), all merge

**Expected**: Fewer CI runs than PRs; all merge atomically

---

## Scenario 3 — Batch Failure (One Bad PR)

**Goal**: Verify a failing PR is ejected without blocking others.

1. Create 2 branches:
   - `scenario-3-good`: valid change to `src/calculator.kt`
   - `scenario-3-bad`: create a file named `FAIL_TESTS` in the root (CI checks for this)
2. Open both PRs and enqueue both
3. Observe: CI fails on the batch; GitHub bisects — bad PR is ejected, good PR re-queues and merges

**Expected**: `scenario-3-bad` is kicked out with a failure notification; `scenario-3-good` merges

> The `FAIL_TESTS` trigger is baked into `.github/workflows/ci.yml`.

---

## Scenario 4 — Conflict Handling

**Goal**: Verify two PRs modifying the same line are handled correctly.

1. Create 2 branches from the same base, both modifying the **same line** in `src/calculator.kt`
2. Open both PRs and enqueue both
3. Observe: one merges; the other is ejected due to conflict

**Expected**: Second PR is ejected with a merge conflict error; developer must rebase and re-enqueue

---

## Scenario 5 — Stacked PRs

**Goal**: Verify stacked PRs work correctly with merge queue.

1. Create branch `scenario-5-base` from `main`, modify `src/calculator.kt`
2. Open PR #A targeting `main`
3. Create branch `scenario-5-stacked` from `scenario-5-base`, add more changes
4. Open PR #B targeting `scenario-5-base`
5. Enqueue PR #A — it merges into `main`
6. Retarget PR #B to `main`, rebase if needed, then enqueue

**Expected**: Only PR #A can be enqueued initially; PR #B must wait and be retargeted after #A merges

> Mirrors ndc-hub stacked PRs (e.g. T2AIR-67109 stacked on T2AIR-67097).

---

## Scenario 6 — Auto-merge via CLI (Renovate simulation)

**Goal**: Verify that `gh pr merge --auto` works with merge queue.

1. Create a branch with a trivial change
2. Open a PR
3. Run: `gh pr merge <number> --auto --squash`
4. Observe: PR is automatically enqueued and merges when CI passes

**Expected**: Same as happy path but triggered via CLI, not UI

---

## Observations Checklist

For each scenario, record:
- [ ] Time from "enqueue" to merge
- [ ] Number of CI runs triggered (check Actions tab)
- [ ] Temporary branch names created by queue (`gh-readonly-queue/main/...`)
- [ ] PR notification messages on ejection
- [ ] Any UI confusion or unexpected behavior
