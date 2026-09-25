# Rank-Up Leaderboard

A single-file, zero-dependency leaderboard for tracking students through the
[8 ranks](../README.md#the-ranks). Runs on plain Node.js — nothing to
`npm install`, no build step, no framework.

## Run it

```bash
node leaderboard/server.js
```

Then open **http://localhost:3000**. Pass a different port as an argument or
via `PORT`:

```bash
node leaderboard/server.js 8080
```

## Editing

Everything is editable straight from the page:

- **Add a student** with the form at the top (name, track, starting rank).
- **Click a name** to rename, **change the track or rank dropdown** to move
  them, or hit **✕** to remove them.

Every change is saved immediately to `data.json` in this folder, so the
board survives a restart. It's created on first save and is gitignored, so
real student data never ends up in the repo. That file is plain JSON if you ever want to
hand-edit or back it up:

```json
{ "students": [{ "id": "...", "name": "Ada", "track": "web", "rank": "gold" }] }
```

## How it's built

- `server.js` — a plain `node:http` server. Serves the static page, the rank
  badge images, and a tiny JSON API (`GET/POST /api/students`,
  `PATCH/DELETE /api/students/:id`). No Express, no dependencies at all.
- `public/index.html` — the whole UI: markup, styles, and vanilla JS in one
  file, talking to the API with `fetch`.
- `public/badges/` — the rank badge artwork, one `.webp` per rank.

This folder is self-contained — copy it anywhere a bare Node install can run it.
