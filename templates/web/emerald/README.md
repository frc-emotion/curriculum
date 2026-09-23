# Web Emerald — First contribution to nautilus-frontend

**Track:** Web (TypeScript / React / React Native)
**Builds on:** Web Diamond (React Native, Expo, navigation)

There is no template for this rank. You are working in the team's actual app:
[`frc-emotion/nautilus-frontend`](https://github.com/frc-emotion/nautilus-frontend).

Guide: **GUIDE_URL** (section "Web Emerald")

---

## What's different about this repo

You'll notice some things that don't match what you just built at Diamond. That is normal —
real codebases have history:

- It runs on an **older Expo SDK** and uses a **development build**, not Expo Go. Follow the
  repo's own setup instructions, and expect the first run to take a while.
- It uses **React Navigation 6**. Your Diamond app used 7. The ideas are the same — a
  navigator, screens, typed params — and a couple of imports differ.
- The UI is built from **gluestack-ui** components plus **NativeWind** classes. Your job is
  to use what is already there, not to add a new way of doing things.

Ask a lead to walk you through the first setup. It is the fiddliest part of this rank and
there is no prize for struggling alone.

---

## Steps

1. **Read the repo before you change it.** Where do screens live? Where do shared components
   live? Where do API calls live? Write that map in your PR description.
2. **Get it running on a device** and confirm you can see your change before you make one.
3. **Claim a `good first issue`** by commenting on it. If none are open, ask a lead.
4. **Branch** as `<your-username>/<short-description>`.
5. **Build it with the components that already exist.** If you find yourself writing a new
   button from scratch, stop and go look for the existing one.
6. **Test it on a real device**, not just a simulator.
7. **Open a PR** with before/after screenshots and `Closes #<issue>`.

---

## Passes when

- Your issue is **closed by a merged PR**.
- You used existing gluestack-ui components and NativeWind classes rather than inventing a
  parallel design system.
- You tested on a device, and the screenshots are in your PR.

---

## Things that get a PR sent back

- A new component that duplicates one already in the repo.
- Inline styles where the rest of the file uses NativeWind classes.
- A fetch call inside a component, when the repo keeps those in its API layer.
- `any` sneaked in to make the compiler quiet.
- Screenshots missing, so the reviewer has to build your branch to see what changed.

---

## Resources

1. [nautilus-frontend](https://github.com/frc-emotion/nautilus-frontend)
2. [gluestack-ui: Components](https://gluestack.io/ui/docs/home/overview/introduction)
3. [NativeWind: Usage](https://www.nativewind.dev/docs/getting-started/installation)
4. [GitHub: Contributing to a project](https://docs.github.com/en/get-started/exploring-projects-on-github/contributing-to-a-project)

Full guide: **GUIDE_URL**
