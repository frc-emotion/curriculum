// ============================================================
// RANK:        Web Copper: Student Filter
// STEPS HERE:  8, 10
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Web track > Copper tab)
// ============================================================
//
// index.html loads this file last, then calls printResults(). Put all your
// code inside printResults(). Everything you console.log shows up on the page:
// it's your window into your own code.

function printResults() {
  // STEP 8: Print the results of every function, with labels
  // WHAT:       Call each of your functions from studentUtils.js,
  //             printing the result with a label so a human can tell what they are
  //             looking at. Something like:
  //               Names: Ada Nwosu, Bo Tran, ...
  //               Software subteam: 5 students
  //             Then refresh index.html in your browser and read the output.
  // WHY:        Printing tells you what your code
  //             is actually doing, which is a different and more useful thing when
  //             you're stuck. Getting comfortable with "print it and look" now
  //             will save you hours at every later rank.
  // CONCEPTS:   console.log, reading your own output
  // DONE WHEN:  the page shows a labelled result for all seven functions and
  //             no red errors.

  console.log(`Loaded ${students.length} students.`);

  // STEP 10: Test the edge cases yourself
  // WHAT:       Call your functions on the awkward inputs and print the results:
  //             a subteam nobody is on, an id that doesn't exist, the student with
  //             no `contact`, and the two students tied on attendance. Then print
  //             the roster again to prove none of your functions changed it.
  // WHY:        Your reviewer will try exactly these. Code that works on the easy
  //             case and breaks on the edge case is the most common bug there is.
  //             Be ready to explain why you used `.find` in one place and
  //             `.filter` in another.
  // CONCEPTS:   Edge cases, testing your own code, immutability
  // DONE WHEN:  every edge case prints a sensible answer and the roster is unchanged.
}
