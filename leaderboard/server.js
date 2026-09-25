#!/usr/bin/env node
// ============================================================
// Rank-Up Leaderboard — a single-process, zero-dependency server.
//
// Runs on plain Node.js only (http/fs/path/crypto from the standard
// library — nothing to `npm install`).
//
//   node server.js [port]
//
// Data lives in data.json next to this file. Every edit made in the
// browser is persisted there immediately, so the leaderboard survives
// restarts.
// ============================================================
'use strict';

const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const crypto = require('node:crypto');

const ROOT = __dirname;
const PUBLIC_DIR = path.join(ROOT, 'public');
const DATA_FILE = path.join(ROOT, 'data.json');
const PORT = Number(process.argv[2] || process.env.PORT || 3000);

const RANKS = ['unranked', 'copper', 'iron', 'gold', 'platinum', 'diamond', 'emerald', 'ruby'];
const TRACKS = ['robot', 'web'];

const MIME = {
  '.html': 'text/html; charset=utf-8',
  '.css': 'text/css; charset=utf-8',
  '.js': 'text/javascript; charset=utf-8',
  '.json': 'application/json; charset=utf-8',
  '.webp': 'image/webp',
};

// ---------- data.json helpers ----------

function readStudents() {
  try {
    const raw = fs.readFileSync(DATA_FILE, 'utf8');
    const parsed = JSON.parse(raw);
    return Array.isArray(parsed.students) ? parsed.students : [];
  } catch {
    return [];
  }
}

function writeStudents(students) {
  fs.writeFileSync(DATA_FILE, JSON.stringify({ students }, null, 2) + '\n');
}

function validate(body, { partial = false } = {}) {
  if (!body || typeof body !== 'object' || Array.isArray(body)) {
    throw new Error('request body must be a JSON object');
  }
  const out = {};

  if (!partial || body.name !== undefined) {
    if (typeof body.name !== 'string' || !body.name.trim()) {
      throw new Error('name is required');
    }
    out.name = body.name.trim().slice(0, 60);
  }

  if (!partial || body.track !== undefined) {
    if (!TRACKS.includes(body.track)) {
      throw new Error(`track must be one of: ${TRACKS.join(', ')}`);
    }
    out.track = body.track;
  }

  if (!partial || body.rank !== undefined) {
    if (!RANKS.includes(body.rank)) {
      throw new Error(`rank must be one of: ${RANKS.join(', ')}`);
    }
    out.rank = body.rank;
  }

  return out;
}

// ---------- tiny HTTP helpers (no framework) ----------

function sendJson(res, status, body) {
  const text = JSON.stringify(body);
  res.writeHead(status, {
    'Content-Type': 'application/json; charset=utf-8',
    'Content-Length': Buffer.byteLength(text),
  });
  res.end(text);
}

function readBody(req) {
  return new Promise((resolve, reject) => {
    let data = '';
    req.on('data', (chunk) => {
      data += chunk;
      if (data.length > 1e6) req.destroy(new Error('body too large'));
    });
    req.on('end', () => resolve(data));
    req.on('error', reject);
  });
}

function serveStatic(res, pathname) {
  const safePath = path.normalize(pathname === '/' ? '/index.html' : pathname);
  const filePath = path.join(PUBLIC_DIR, safePath);

  if (!filePath.startsWith(PUBLIC_DIR)) {
    res.writeHead(403, { 'Content-Type': 'text/plain' });
    res.end('Forbidden');
    return;
  }

  fs.readFile(filePath, (err, data) => {
    if (err) {
      res.writeHead(404, { 'Content-Type': 'text/plain' });
      res.end('Not found');
      return;
    }
    res.writeHead(200, { 'Content-Type': MIME[path.extname(filePath)] || 'application/octet-stream' });
    res.end(data);
  });
}

// ---------- routing ----------

const server = http.createServer(async (req, res) => {
  let pathname;
  try {
    pathname = decodeURIComponent(new URL(req.url, 'http://localhost').pathname);
  } catch {
    res.writeHead(400, { 'Content-Type': 'text/plain' });
    res.end('Bad request');
    return;
  }

  try {
    if (pathname === '/api/ranks' && req.method === 'GET') {
      return sendJson(res, 200, { ranks: RANKS, tracks: TRACKS });
    }

    if (pathname === '/api/students' && req.method === 'GET') {
      return sendJson(res, 200, readStudents());
    }

    if (pathname === '/api/students' && req.method === 'POST') {
      const fields = validate(JSON.parse((await readBody(req)) || '{}'));
      const students = readStudents();
      const student = { id: crypto.randomUUID(), ...fields };
      students.push(student);
      writeStudents(students);
      return sendJson(res, 201, student);
    }

    const idMatch = pathname.match(/^\/api\/students\/([\w-]+)$/);

    if (idMatch && req.method === 'PATCH') {
      const fields = validate(JSON.parse((await readBody(req)) || '{}'), { partial: true });
      const students = readStudents();
      const index = students.findIndex((s) => s.id === idMatch[1]);
      if (index === -1) return sendJson(res, 404, { error: 'student not found' });
      students[index] = { ...students[index], ...fields };
      writeStudents(students);
      return sendJson(res, 200, students[index]);
    }

    if (idMatch && req.method === 'DELETE') {
      const students = readStudents();
      const next = students.filter((s) => s.id !== idMatch[1]);
      if (next.length === students.length) return sendJson(res, 404, { error: 'student not found' });
      writeStudents(next);
      return sendJson(res, 200, { ok: true });
    }

    if (req.method === 'GET') {
      return serveStatic(res, pathname);
    }

    sendJson(res, 405, { error: 'method not allowed' });
  } catch (err) {
    sendJson(res, 400, { error: err.message });
  }
});

server.listen(PORT, () => {
  console.log(`Rank-Up Leaderboard running at http://localhost:${PORT}`);
  console.log('Press Ctrl+C to stop.');
});
