import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Component, type ReactNode } from 'react';
import { afterAll, afterEach, beforeAll, describe, expect, it, vi } from 'vitest';
import App from '../src/App.tsx';
import * as themeModule from '../src/context/ThemeContext.tsx';
import { server } from './handlers.ts';

beforeAll(() => server.listen({ onUnhandledRequest: 'error' }));
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

/**
 * Catches an error thrown during render, so that deliberately breaking a
 * component doesn't surface as an uncaught error in the check output.
 */
class Boundary extends Component<{ children: ReactNode; onError: () => void }, { failed: boolean }> {
  state = { failed: false };

  static getDerivedStateFromError() {
    return { failed: true };
  }

  componentDidCatch() {
    this.props.onError();
  }

  render() {
    return this.state.failed ? null : this.props.children;
  }
}

function themed(): HTMLElement | null {
  return document.querySelector('[data-theme]');
}

function themeToggle(): HTMLElement | undefined {
  return screen
    .queryAllByRole('button')
    .find((button) => /theme|dark|light|☀|🌙/i.test(button.textContent ?? ''));
}

describe('STEP 6: the theme context', () => {
  it('exports a ThemeProvider and a useTheme hook', () => {
    const module = themeModule as Record<string, unknown>;
    expect(typeof module.ThemeProvider,
      'STEP 6: I couldn\'t find an exported `ThemeProvider` in src/context/ThemeContext.tsx.')
      .toBe('function');
    expect(typeof module.useTheme,
      'STEP 6: I couldn\'t find an exported `useTheme` hook in src/context/ThemeContext.tsx. '
      + 'Wrapping useContext in your own hook is what lets you throw a clear error when it is '
      + 'used in the wrong place.').toBe('function');
  });

  it('useTheme complains when used outside the provider', () => {
    const useTheme = (themeModule as Record<string, unknown>).useTheme as () => unknown;
    let threw = false;
    function Orphan() {
      useTheme();
      return null;
    }
    // This check makes a component blow up on purpose. The error boundary keeps
    // React from reporting it as an uncaught error, and console.error is muted,
    // so the check output stays readable.
    const quiet = vi.spyOn(console, 'error').mockImplementation(() => {});
    // React re-dispatches the error as a window error event for DevTools, which
    // jsdom would otherwise print. Marking it handled keeps the output clean.
    const swallow = (event: ErrorEvent) => event.preventDefault();
    window.addEventListener('error', swallow);
    try {
      render(
        <Boundary onError={() => { threw = true; }}>
          <Orphan />
        </Boundary>,
      );
    } catch {
      threw = true;
    } finally {
      window.removeEventListener('error', swallow);
      quiet.mockRestore();
    }
    expect(threw,
      'STEP 6: calling useTheme() outside a ThemeProvider should throw a clear error. Without '
      + 'that, the mistake shows up much later as an undefined value in a component that has '
      + 'nothing to do with the theme.').toBe(true);
  });

  it('the page carries a data-theme attribute', async () => {
    render(<App />);
    await waitFor(() => {
      expect(themed(),
        'STEP 6: put `data-theme={theme}` on the outer element in App. The CSS switches palettes '
        + 'on that attribute.').toBeTruthy();
    }, { timeout: 3000 });
  });

  it('a toggle button switches the theme', async () => {
    render(<App />);
    await waitFor(() => {
      expect(themeToggle(),
        'STEP 6: I couldn\'t find a theme toggle button. Give it text mentioning the theme — '
        + '"Dark mode", "Switch to light", something like that.').toBeTruthy();
    }, { timeout: 3000 });

    const before = themed()?.getAttribute('data-theme');
    await userEvent.click(themeToggle() as HTMLElement);

    await waitFor(() => {
      expect(themed()?.getAttribute('data-theme'),
        'STEP 6: pressing the toggle should change data-theme from '
        + `"${before}" to the other one.`).not.toBe(before);
    });
  });
});
