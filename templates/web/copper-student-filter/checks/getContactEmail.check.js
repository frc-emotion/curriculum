import { describe, expect, it } from 'vitest';
import { call, frozenStudents } from './_support.js';

const MISSING = 'no email on file';

describe('STEP 7: getContactEmail', () => {
  it('gives the email when there is one', () => {
    const ada = frozenStudents()[0];
    expect(call(7, 'getContactEmail', ada),
      'STEP 7: Ada has an email, so that is what should come back.').toBe('ada@example.com');
  });

  it('handles a student with no contact at all', () => {
    const emre = frozenStudents()[4];
    expect(call(7, 'getContactEmail', emre),
      `STEP 7: Emre has no \`contact\` object at all. Reading student.contact.email would throw `
      + `here — that is exactly what optional chaining (?.) is for. Expected "${MISSING}".`)
      .toBe(MISSING);
  });

  it('handles a contact with no email in it', () => {
    const gia = frozenStudents()[6];
    expect(call(7, 'getContactEmail', gia),
      `STEP 7: Gia has a \`contact\`, but only a phone number in it. Expected "${MISSING}".`)
      .toBe(MISSING);
  });

  it('treats an empty email as an email, not as missing', () => {
    const student = { id: 99, name: 'Test', grade: 9, subteam: 'Software', meetingsAttended: 0,
      contact: { email: '' } };
    expect(call(7, 'getContactEmail', student),
      'STEP 7: an empty string is a value, even if it is not a useful one, so ?? should keep it. '
      + 'If you got "no email on file" here you used || instead of ?? — || also replaces "", 0 '
      + 'and false, which is the bug this step exists to teach.').toBe('');
  });
});
