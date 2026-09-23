# Rank Up — FRC 2658 Software Training

This repo holds every **starter template** for the software subteam's rank-up system.

Each rank has an assessment. You copy that rank's template into your own folder, write the
code the STEP comments ask for, open a pull request, and a lead reviews it. When your PR is
merged, that folder is the permanent record that you earned the rank.

The full guide — concepts, resources, and the reasoning behind each rank — lives at **GUIDE_URL**.
This repo is the hands-on half. The guide is the reading half. You need both.

---

## The ranks

There are 8 ranks. Unranked is shared by everybody. After that you pick a track.

| Rank | Robot track (Java / WPILib) | Web track (TypeScript / React / React Native) |
| --- | --- | --- |
| **Unranked** | Git and Workflows (everyone) | Git and Workflows (everyone) |
| **Copper** | Java variables, conditionals, operators | JavaScript fundamentals and tooling |
| **Iron** | Java loops, methods, arrays | TypeScript and async code |
| **Gold** | Java classes, inheritance, interfaces, lambdas | React fundamentals |
| **Platinum** | WPILib structure and motors | Deeper React |
| **Diamond** | Sensors, subsystems, commands | React Native, Expo, navigation |
| **Emerald** | PID and setpoints | First contribution to nautilus-frontend |
| **Ruby** | Contributing to Rebuilt-2026 | Owning a feature in nautilus-frontend |

### Two rules about ranks

1. **No skipping.** You earn ranks in order. The `start-rank` script checks that your previous
   rank is merged into `main` before it will set up the next one.
2. **Ranks are cumulative.** Copper skills are still fair game at Diamond. Your reviewer can ask
   you about anything from a lower rank, and several assessments ask you to bring forward code
   you wrote at an earlier rank.

Emerald and Ruby on the web track, and Ruby on the robot track, have **no code template** — by
then you are working in the team's real repos. Those folders hold an orientation README instead.

---

## Starting a rank

### Once, ever

1. Finish **Unranked** first. It is the only rank you do by editing this repo directly:
   see [unranked/README.md](unranked/README.md).
2. Install the tools your track needs (listed in each template's README).

### Every rank after that

macOS / Linux:

```bash
./scripts/start-rank.sh <track> <rank> <your-github-username>
```

Windows PowerShell:

```powershell
.\scripts\start-rank.ps1 <track> <rank> <your-github-username>
```

For example:

```bash
./scripts/start-rank.sh robot copper octocat
```

The script will:

- check that you finished the rank below this one (and stop with a friendly message if not),
- create the branch `<username>/<track>-<rank>`,
- copy the template into `students/<username>/<track>/<rank>/`,
- write a small `.rank.json` so CI knows how to check your work,
- print the exact commands to run next.

Then: work **only inside your own folder**, commit as you go, push, and open a PR into `main`.

---

## Folder map

```
rank-up/
├── README.md              you are here
├── REVIEWING.md           for leads: how to review each rank
├── scripts/               start-rank.sh and start-rank.ps1
├── unranked/              the Unranked assessment — edited in place by everyone
├── templates/
│   ├── robot/             one folder per robot rank
│   └── web/               one folder per web rank
├── students/              your work goes here, created by start-rank
│   └── <username>/<track>/<rank>/
└── bonus/                 optional challenges, just for fun
```

---

## Run and check commands

Every template has exactly two commands you care about. **Run** shows you your program.
**Check** grades it. The check is supposed to fail on a fresh template — that is your to-do list.

| Track | Rank | Run | Check |
| --- | --- | --- | --- |
| robot | Copper | `./gradlew run` | `./gradlew rankCheck` |
| robot | Iron | `./gradlew run` | `./gradlew rankCheck` |
| robot | Gold | `./gradlew run` | `./gradlew rankCheck` |
| robot | Platinum | `./gradlew simulateJava` | `./gradlew build` + reviewer |
| robot | Diamond | `./gradlew simulateJava` | `./gradlew rankCheck` |
| robot | Emerald | `./gradlew simulateJava` | `./gradlew rankCheck` |
| web | Copper | `npm start` | `npm run check` |
| web | Iron | `npm start` | `npm run check` |
| web | Gold | `npm run dev` | `npm run check` |
| web | Platinum | `npm run dev` | `npm run check` |
| web | Diamond | `npx expo start` | `npm run check` |

On Windows use `gradlew.bat` instead of `./gradlew`.

Every check failure starts with a step number, like:

```
STEP 6: expected a ternary operator (? :) in decideDirection
```

That tells you exactly which STEP comment to go read.

---

## How to read a template

Every source file starts with a header block telling you the rank, which steps live in that
file, and the run/check commands. Then each step looks like this:

```java
// STEP 2: Write clamp
// WHAT:       ...what to build, including the exact name and signature...
// WHY:        ...why real robot code needs this...
// CONCEPTS:   ...the ideas involved...
// READ:       Guide > Robot Iron > Resources #3 and #7
// CHECKED BY: ClampCheck, or "your reviewer"
// DONE WHEN:  ...how you know it works...
```

The comments tell you **what** to build and **why**. They never contain the answer. The exact
names and signatures are given because the checks depend on them — spell them exactly.

---

## Getting help

- Re-read the STEP comment and the guide section it points at.
- Run the check and read the first failure. Only the first one.
- Ask in the software channel. Show what you tried.
- Ask a lead. "I don't understand X" is always a fine thing to say.

Do not paste an AI-generated solution. You will pass the check and fail the review, because
your reviewer will ask you to explain your own code.
