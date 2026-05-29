# Branch Protection Setup — Enabling Merge Queue

## Prerequisites

- You must be a repo admin
- At least one CI run must have completed so the `ci` check appears in the dropdown

## Steps

### 1. Go to Branch Protection Settings

1. Navigate to: `https://github.com/vasantbala/merge-queue-test`
2. Click **Settings** → **Branches**
3. Click **Add branch protection rule**

### 2. Configure the Rule

Set the branch name pattern to `main`, then configure:

| Setting | Value | Notes |
|---|---|---|
| Require a pull request before merging | ✅ | Required for merge queue |
| Require approvals | 0 | For solo testing |
| Require status checks to pass | ✅ | Add `ci` from the dropdown |
| Require branches to be up to date before merging | ✅ | Prerequisite for merge queue |
| **Require merge queue** | ✅ | The key toggle |
| Maximum PRs to build | 3 | Good starting point |
| Minimum PRs to merge | 1 | So single PRs don't wait indefinitely |
| Wait timer | 1 minute | Queue waits this long for more PRs before batching |
| Merge method | Squash | Match ndc-hub policy |

### 3. Save the Rule

Click **Create**.

### 4. Verify

Open a test PR — the merge button should say **"Merge when ready"** instead of **"Merge pull request"**.

---

## Tuning for ndc-hub

| Setting | Recommended Value |
|---|---|
| Maximum PRs to build | 3 |
| Minimum PRs to merge | 1 |
| Wait timer | 5 minutes |
| Merge method | Squash (matches current policy) |

> **Note for ndc-hub**: The branch name is `master`, not `main`.
