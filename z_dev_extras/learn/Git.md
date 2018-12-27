### -----------------------GIT REBASE ----------------------------------------------------------


**git checkout feature**

**git rebase master** (will add all master commits before the commits of the repos branch
- branches will be identical now so we can fast forward)
(solve conflicts here if any after pull)
git add .
git commit -m "Merge after rebase"
git merge repos (fast-forward)

### -----------------------REMOVING COMMITS ----------------------------------------------------------

**git reset  --hard HEAD~1** (Remove the latest (3rd) local commit - it was messed-up after squeeze. It deletes changes in WD as well)

**git pull** (pulls the 3rd commit (not messed-up) from the remote)
now the local is back to the remote state
