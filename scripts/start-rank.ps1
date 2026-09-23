<#
============================================================
 start-rank.ps1 - set up your folder for a rank assessment.

   .\scripts\start-rank.ps1 <track> <rank> <github-username>

 Example:
   .\scripts\start-rank.ps1 robot copper octocat

 It checks that you finished the rank below this one, makes a branch,
 copies the template into students/<username>/<track>/<rank>/, and tells
 you what to do next. Guide: GUIDE_URL

 If PowerShell refuses to run this file, run it like this once:
   powershell -ExecutionPolicy Bypass -File .\scripts\start-rank.ps1 robot copper octocat
============================================================
#>
[CmdletBinding()]
param(
    [Parameter(Position = 0)][string]$Track,
    [Parameter(Position = 1)][string]$Rank,
    [Parameter(Position = 2)][string]$Username,
    [switch]$Force
)

$ErrorActionPreference = 'Stop'

function Show-Usage {
    Write-Host "Usage: .\scripts\start-rank.ps1 <track> <rank> <github-username> [-Force]"
    Write-Host "  track: robot | web"
    Write-Host "  rank:  copper | iron | gold | platinum | diamond | emerald | ruby"
    Write-Host "  -Force: skip the previous-rank check (leads only)"
}

if (-not $Track -or -not $Rank -or -not $Username) {
    Show-Usage
    Write-Host "Example: .\scripts\start-rank.ps1 robot copper octocat"
    exit 1
}

$Track = $Track.ToLowerInvariant()
$Rank = $Rank.ToLowerInvariant()
$Username = $Username.ToLowerInvariant()

$RepoRoot = Split-Path -Parent $PSScriptRoot
Set-Location -LiteralPath $RepoRoot

# ------------------------------------------------------------
# Validate the arguments
# ------------------------------------------------------------
if ($Track -notin @('robot', 'web')) {
    Write-Host "I don't know the track '$Track'. It has to be 'robot' or 'web'."
    exit 1
}

if ($Rank -eq 'unranked') {
    Write-Host "Unranked is different: you do it by editing this repo directly, not with this script."
    Write-Host "Open unranked/README.md and follow the steps there."
    exit 1
}

if ($Rank -notin @('copper', 'iron', 'gold', 'platinum', 'diamond', 'emerald', 'ruby')) {
    Write-Host "I don't know the rank '$Rank'."
    Write-Host "Ranks: copper, iron, gold, platinum, diamond, emerald, ruby"
    exit 1
}

if ($Username -notmatch '^[a-z0-9]([a-z0-9-]*[a-z0-9])?$') {
    Write-Host "'$Username' doesn't look like a GitHub username."
    Write-Host "Use your GitHub username in lowercase, e.g. octocat."
    exit 1
}

# ------------------------------------------------------------
# Look up this rank
# ------------------------------------------------------------
$TemplateFolders = @{
    'robot/copper'   = 'copper-joystick-decider'
    'robot/iron'     = 'iron-motor-ramp'
    'robot/gold'     = 'gold-mock-robot-hardware'
    'robot/platinum' = 'platinum-bench-motor-control'
    'robot/diamond'  = 'diamond-simple-motor-subsystem'
    'robot/emerald'  = 'emerald-arm-to-presets'
    'robot/ruby'     = 'ruby'
    'web/copper'     = 'copper-student-filter'
    'web/iron'       = 'iron-typed-student-tracker'
    'web/gold'       = 'gold-attendance-card'
    'web/platinum'   = 'platinum-attendance-dashboard'
    'web/diamond'    = 'diamond-attendance-app'
    'web/emerald'    = 'emerald'
    'web/ruby'       = 'ruby'
}

$Kinds = @{
    'robot/copper'   = 'java';   'robot/iron'    = 'java';   'robot/gold'    = 'java'
    'robot/platinum' = 'wpilib'; 'robot/diamond' = 'wpilib'; 'robot/emerald' = 'wpilib'
    'robot/ruby'     = 'external'
    'web/copper'     = 'node';   'web/iron'      = 'node'
    'web/gold'       = 'vite';   'web/platinum'  = 'vite'
    'web/diamond'    = 'expo'
    'web/emerald'    = 'external'; 'web/ruby'    = 'external'
}

$PreviousRanks = @{
    'copper' = 'unranked'; 'iron' = 'copper'; 'gold' = 'iron'; 'platinum' = 'gold'
    'diamond' = 'platinum'; 'emerald' = 'diamond'; 'ruby' = 'emerald'
}

$RunCommands = @{
    'java' = './gradlew run'; 'wpilib' = './gradlew simulateJava'
    'node' = 'npm install; npm start'; 'vite' = 'npm install; npm run dev'
    'expo' = 'npm install; npx expo start'; 'external' = '(no code here - read the README)'
}

$CheckCommands = @{
    'java' = './gradlew rankCheck'; 'wpilib' = './gradlew rankCheck'
    'node' = 'npm run check'; 'vite' = 'npm run check'; 'expo' = 'npm run check'
    'external' = '(reviewed by a lead)'
}

$Key = "$Track/$Rank"
$Folder = $TemplateFolders[$Key]
$Kind = $Kinds[$Key]
$Prev = $PreviousRanks[$Rank]
$TemplateDir = Join-Path 'templates' (Join-Path $Track $Folder)
$DestDir = Join-Path 'students' (Join-Path $Username (Join-Path $Track $Rank))
$Branch = "$Username/$Track-$Rank"

if (-not (Test-Path -LiteralPath $TemplateDir)) {
    Write-Host "I can't find the template at $TemplateDir."
    Write-Host "Are you running this from inside the rank-up repo?"
    exit 1
}

# ------------------------------------------------------------
# Are we in a git repo?
# ------------------------------------------------------------
git rev-parse --is-inside-work-tree 2>$null | Out-Null
if ($LASTEXITCODE -ne 0) {
    Write-Host "This folder isn't a git repository, so I can't make a branch for you."
    Write-Host "Clone the repo with git and run this script from inside the clone."
    exit 1
}

$MainRef = $null
foreach ($ref in @('origin/main', 'main')) {
    git rev-parse --verify --quiet $ref 2>$null | Out-Null
    if ($LASTEXITCODE -eq 0) { $MainRef = $ref; break }
}
if (-not $MainRef) {
    Write-Host "I couldn't find a 'main' branch to compare against."
    Write-Host "Run 'git fetch origin' and try again."
    exit 1
}

# ------------------------------------------------------------
# No skipping: check the rank below this one
# ------------------------------------------------------------
if ($Force) {
    Write-Host "! -Force: skipping the previous-rank check. Leads only."
}
elseif ($Rank -eq 'copper') {
    git cat-file -e "${MainRef}:unranked/members/$Username.md" 2>$null
    if ($LASTEXITCODE -ne 0) {
        Write-Host ""
        Write-Host "Hold on - I can't find unranked/members/$Username.md on $MainRef."
        Write-Host ""
        Write-Host "Ranks go in order, and Copper builds on Unranked. Before you start Copper, your"
        Write-Host "Unranked pull request needs to be merged into main. That's the one where you add"
        Write-Host "your member file and your row in ROSTER.md."
        Write-Host ""
        Write-Host "  - Start here:            unranked/README.md"
        Write-Host "  - Already opened a PR?   It has to be *merged*, not just open."
        Write-Host "  - Merged a while ago?    Run 'git fetch origin' so your clone can see it."
        Write-Host "  - Different username?    Use the same one as your member file."
        Write-Host ""
        exit 1
    }
}
else {
    $found = git ls-tree -d --name-only $MainRef "students/$Username/$Track/$Prev"
    if (-not $found) {
        Write-Host ""
        Write-Host "Hold on - I can't find students/$Username/$Track/$Prev/ on $MainRef."
        Write-Host ""
        Write-Host "Ranks go in order, and $Track $Rank builds directly on $Track $Prev. Each rank"
        Write-Host "assumes you already know everything below it, so there's no skipping ahead."
        Write-Host ""
        Write-Host "  - Finish $Track $Prev first and get that pull request merged into main."
        Write-Host "  - Already merged?  Run 'git fetch origin' so your clone can see it."
        Write-Host "  - Wrong track?     You're asking for the '$Track' track."
        Write-Host ""
        exit 1
    }
}

# ------------------------------------------------------------
# Make the branch
# ------------------------------------------------------------
$dirty = git status --porcelain
if ($dirty) {
    Write-Host "Heads up: you have uncommitted changes. Commit or stash them first so they"
    Write-Host "don't get mixed into your new rank branch."
    exit 1
}

git rev-parse --verify --quiet $Branch 2>$null | Out-Null
if ($LASTEXITCODE -eq 0) {
    Write-Host "You already have a branch called $Branch. Switching to it."
    git switch $Branch
}
else {
    git switch -c $Branch $MainRef
    Write-Host "Created branch $Branch"
}

# ------------------------------------------------------------
# Copy the template
# ------------------------------------------------------------
if (Test-Path -LiteralPath $DestDir) {
    Write-Host ""
    Write-Host "$DestDir already exists, so I left it alone - your work is safe."
    Write-Host "Delete that folder yourself if you really want a fresh copy."
}
else {
    New-Item -ItemType Directory -Force -Path $DestDir | Out-Null
    Copy-Item -Path (Join-Path $TemplateDir '*') -Destination $DestDir -Recurse -Force
    Write-Host "Copied $TemplateDir -> $DestDir"
}

$rankJson = @"
{
  "track": "$Track",
  "rank": "$Rank",
  "student": "$Username",
  "kind": "$Kind"
}
"@
Set-Content -LiteralPath (Join-Path $DestDir '.rank.json') -Value $rankJson

# ------------------------------------------------------------
# Tell them what to do next
# ------------------------------------------------------------
$run = $RunCommands[$Kind]
$check = $CheckCommands[$Kind]

Write-Host ""
Write-Host "------------------------------------------------------------"
Write-Host "  You're set up for $Track $Rank."
Write-Host "------------------------------------------------------------"
Write-Host ""
Write-Host "Your folder:   $DestDir"
Write-Host "Your branch:   $Branch"
Write-Host ""
Write-Host "Next:"
Write-Host ""
Write-Host "  1. Read the README in your folder, start to finish:"
Write-Host "       $DestDir\README.md"
Write-Host ""
Write-Host "  2. Run it once before you change anything:"
Write-Host "       cd `"$DestDir`""
if ($Kind -eq 'java' -or $Kind -eq 'wpilib') {
    Write-Host "       $($run -replace '\./gradlew', '.\gradlew.bat')"
} else {
    Write-Host "       $run"
}
Write-Host ""
Write-Host "  3. Run the check. It is SUPPOSED to fail right now - the failures are your to-do list:"
if ($Kind -eq 'java' -or $Kind -eq 'wpilib') {
    Write-Host "       $($check -replace '\./gradlew', '.\gradlew.bat')"
} else {
    Write-Host "       $check"
}
Write-Host ""
Write-Host "  4. Work through the STEP comments in order. Commit as you go:"
Write-Host "       git add `"$DestDir`""
Write-Host "       git commit -m `"$Track ${Rank}: step 1`""
Write-Host ""
Write-Host "  5. When the check passes, push and open a pull request into main:"
Write-Host "       git push -u origin $Branch"
Write-Host ""
Write-Host "Work only inside $DestDir. Don't edit anyone else's folder or the templates."
Write-Host ""
Write-Host "Guide: GUIDE_URL"
