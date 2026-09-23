# Bonus Challenges

Optional. Not assessed, not required, not worth any rank. Purely for the fun of it.

Do these if a rank left you wanting to keep going, or if you are waiting on a review and
bored. Nothing here unlocks anything.

If you finish one, open a PR into `bonus/<your-username>/<challenge-name>/` and say what you
did. A lead will look at it because they want to, not because they have to.

---

## Robot track

**Copper+ — The deadband, visualised.**
Print an ASCII bar chart of what `decideDirection` returns for joystick values from -1.0 to
1.0 in steps of 0.05. Look at where the STOP band actually sits. Is it the width you
expected?

**Iron+ — Slew rate limiter.**
Instead of ramping to a target once, write a `SlewRateLimiter` class: you hand it a target
every loop, and it returns a value that never changes faster than a limit you set. Compare
yours against WPILib's `SlewRateLimiter`.

**Gold+ — The scheduler.**
Write a tiny command scheduler for your mock robot: commands with `initialize`, `execute`,
`isFinished` and `end`, a list of scheduled ones, and a rule that two commands can't hold the
same subsystem at once. You will understand WPILib's scheduler far better afterwards.

**Platinum+ — Motor characterisation.**
Ramp the bench motor slowly from 0 to full and record the applied output against the measured
velocity. Plot it in a spreadsheet. Where does it stop being a straight line, and why?

**Diamond+ — Soft limits in software.**
Add encoder-based soft limits to your subsystem — stop before the limit switch rather than at
it. Then work out what happens if the encoder is wrong, and decide whether you trust it.

**Emerald+ — Add a D term.**
You tuned with P alone. Add D and find out what it does to overshoot, and what it does when
your sensor is noisy. Write down which mattered more.

---

## Web track

**Copper+ — Sort in two directions.**
Extend `getRegularAttendees` to take a direction, without duplicating the comparator. Harder
than it sounds to do cleanly.

**Iron+ — Retry with backoff.**
Make `fetchStudents` retry a failed request three times, waiting longer each time. Then work
out which failures are worth retrying and which aren't. (A 500? A 404? A timeout?)

**Gold+ — Keyboard support.**
Make the whole attendance list usable with only a keyboard: tab to a card, space to toggle,
visible focus. Then try it with your screen reader turned on.

**Platinum+ — Optimistic updates.**
Pretend toggling attendance sends it to a server. Update the screen immediately, and put it
back if the request fails. This is what TanStack Query's mutations do for you.

**Diamond+ — Offline queue.**
Store attendance changes made while offline and send them when the connection comes back.
Decide what happens when two devices disagree — there is no single right answer, which is the
interesting part.

---

## Either track

**Teach one.**
Take a concept you found hard and write the explanation you wish you'd had. Two paragraphs.
If it's good, it goes in the guide with your name on it.

**Fix the templates.**
Found a typo, a confusing STEP comment, a check with a bad error message? Open a PR against
this repo. Making the training better for the next person is genuinely the most useful thing
on this page.
