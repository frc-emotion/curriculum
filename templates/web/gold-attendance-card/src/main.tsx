// ============================================================
// RANK:        Web Gold: Attendance Card
// FILE:        src/main.tsx
// STEPS HERE:  none — this file is plumbing
// GUIDE:       GUIDE_URL  (section "Web Gold")
// RUN:         npm run dev        CHECK: npm run check
// PASSES WHEN: the cards toggle correctly, the filter works, there are no key
//              warnings in the console, and state is never mutated directly.
// ============================================================
//
// The one place React connects to the actual web page. It finds the empty
// <div id="root"> in index.html and puts your App inside it.
//
// StrictMode is a development-only helper that deliberately runs some of your
// code twice, to shake out bugs caused by code that assumes it only runs once.
// It does not do that in a real build.
//
// You never need to change this file.

import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import App from './App.tsx';
import './index.css';

const container = document.getElementById('root');
if (!container) {
  throw new Error('No #root element in index.html — did it get deleted?');
}

createRoot(container).render(
  <StrictMode>
    <App />
  </StrictMode>,
);
