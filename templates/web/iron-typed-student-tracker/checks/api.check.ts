import { HttpResponse, http } from 'msw';
import { setupServer } from 'msw/node';
import { afterAll, afterEach, beforeAll, describe, expect, it } from 'vitest';
import { fn, plainStudents } from './_support.js';

/* A fake server, so these checks never touch the real internet. */
const URL_OK = 'https://example.test/api/students';
const URL_BROKEN_SHAPE = 'https://example.test/api/broken';
const URL_SERVER_ERROR = 'https://example.test/api/boom';
const URL_DEAD = 'https://example.test/api/dead';

const server = setupServer(
  http.get(URL_OK, () => HttpResponse.json(plainStudents())),
  http.get(URL_BROKEN_SHAPE, () => {
    const bad = plainStudents();
    (bad[0] as Record<string, unknown>).subteam = 'Softwear';
    return HttpResponse.json(bad);
  }),
  http.get(URL_SERVER_ERROR, () => new HttpResponse(null, { status: 500 })),
  http.get(URL_DEAD, () => HttpResponse.error()),
);

beforeAll(() => server.listen({ onUnhandledRequest: 'error' }));
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

function fetchStudents(url: string): Promise<unknown> {
  return (fn(4, 'src/api.ts', 'fetchStudents') as (u: string) => Promise<unknown>)(url);
}

/**
 * Resolves fetchStudents before a test starts poking at error messages, so a
 * missing function reports as STEP 4 once rather than as three confused
 * failures further down.
 */
function requireFetchStudents(): (url: string) => Promise<unknown> {
  return fn(4, 'src/api.ts', 'fetchStudents') as (url: string) => Promise<unknown>;
}

async function messageFrom(call: Promise<unknown>): Promise<string> {
  try {
    await call;
    return '';
  } catch (error) {
    return String((error as Error)?.message ?? error);
  }
}

describe('STEP 4: fetchStudents', () => {
  it('fetches and returns the students', async () => {
    const result = (await fetchStudents(URL_OK)) as Array<{ name: string }>;

    expect(Array.isArray(result), 'STEP 4: fetchStudents should resolve to an array.').toBe(true);
    expect(result.length, 'STEP 4: the endpoint returns 14 students.').toBe(14);
    expect(result[0]?.name, 'STEP 4: the first student should be Ada Nwosu.').toBe('Ada Nwosu');
  });

  it('returns a promise', () => {
    const returned = fetchStudents(URL_OK);
    expect(typeof (returned as Promise<unknown>)?.then,
      'STEP 4: fetchStudents should be an async function, so calling it gives back a Promise.')
      .toBe('function');
    return returned;
  });
});

describe('STEP 5: the response is validated before it is trusted', () => {
  it('rejects data that does not match the schema', async () => {
    await expect(fetchStudents(URL_BROKEN_SHAPE),
      'STEP 5: this endpoint returns a student whose subteam is "Softwear". fetchStudents should '
      + 'reject it rather than hand it back. A zod schema catches this at runtime; a '
      + '`as Student[]` cast does not, because a cast only convinces the compiler.')
      .rejects.toThrow();
  });

  it('says the data was invalid, not something cryptic', async () => {
    const call = requireFetchStudents();
    const message = (await messageFrom(call(URL_BROKEN_SHAPE))).toLowerCase();
    expect(message.length > 0,
      'STEP 5: the error thrown for bad data should have a message on it.').toBe(true);
    expect(/invalid|validation|schema|shape|unexpected/.test(message),
      'STEP 5: the error message should say the data was invalid, so whoever reads the log knows '
      + `it was the server's fault and not the network's. Got: "${message}"`).toBe(true);
  });
});

describe('STEP 6: a failed request fails cleanly', () => {
  it('throws a readable error on a 500', async () => {
    const call = requireFetchStudents();
    const message = await messageFrom(call(URL_SERVER_ERROR));
    expect(message.length > 0,
      'STEP 6: a 500 response should throw an Error with a message.').toBe(true);
    expect(message.includes(URL_SERVER_ERROR),
      'STEP 6: the error message should mention the URL that failed, so the log says which '
      + `request died. Got: "${message}"`).toBe(true);
  });

  it('throws a readable error when the network is gone', async () => {
    const call = requireFetchStudents();
    const message = await messageFrom(call(URL_DEAD));
    expect(message.includes(URL_DEAD),
      'STEP 6: a network failure should throw your own Error mentioning the URL, not let axios\''
      + ` own error escape. Got: "${message}"`).toBe(true);
  });
});
