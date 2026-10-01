// ============================================================
// RANK:        Web Copper: Student Filter
// STEPS HERE:  1 to 7
// GUIDE:       https://claude.ai/code/artifact/5fdfb435-43a4-4db4-90f5-c843171497ee  (Web track > Copper tab)
// ============================================================
//
// Seven small functions over the team roster. Each one is the kind of thing you
// will write a hundred times on a real app, and each one has a matching array
// method that does most of the work — the skill is picking the right one.
//
// Use exactly the function name in each step. index.html loads this file after
// students.js, so your functions can use `students`, and index.js can call yours.
//
// One rule that runs through all of this: NEVER change the array you were
// given. Make a new one instead. Print the roster after calling your functions
// to make sure it never changed. That rule exists because in React
// — two ranks from now — changing data in place is the single most common
// reason a screen refuses to update.
//
// The data lives in students.js. Go read it first.

// STEP 9: Clean up (applies to this whole file)
// WHAT:       No `var` anywhere: use `const`, and `let` only when a value really
//             changes. No `==` or `!=`: use `===` and `!==`. No variables that
//             nothing reads.
// WHY:        `var` behaves surprisingly, and `==` converts types before comparing,
//             so "" == 0 is true. Unused variables are leftovers or typos.
// CONCEPTS:   const vs let, strict equality, clean code
// DONE WHEN:  a search of this file finds no `var`, no `==` and nothing unused.

// STEP 1: Write getStudentNames
// WHAT:       Write a function `getStudentNames(students)` that returns an
//             array of just the names, in the same order.
//             getStudentNames(students) -> ['Ada Nwosu', 'Bo Tran', ...]
// WHY:        Turning a list of things into a list of one piece of those things
//             is the most common operation in front-end code. Every dropdown,
//             every list of chips, every "who's here today" is this.
// CONCEPTS:   Array.prototype.map, arrow functions
// DONE WHEN:  you get 12 names back, in the original order, and the original
//             array is untouched.

// STEP 2: Write getStudentsBySubteam
// WHAT:       Write `getStudentsBySubteam(students, subteam)` that returns only
//             the students on that subteam. Return the student objects, not just
//             their names. If nobody matches, return an empty array — not null,
//             not undefined.
// WHY:        "Show me only the ones that..." is what every filter control on
//             every page is doing underneath.
// CONCEPTS:   Array.prototype.filter, comparison, returning a new array
// DONE WHEN:  'Software' gives you 5 students, 'Robotics' gives you an empty
//             array, and the original array is untouched.

// STEP 3: Write findStudentById
// WHAT:       Write `findStudentById(students, id)` that returns the one
//             student with that id, or `undefined` if there isn't one.
// WHY:        This is what happens every time someone taps a row and a detail
//             screen opens. Note the difference from step 2: one thing, not a
//             list of things.
// CONCEPTS:   Array.prototype.find, the difference between find and filter,
//             undefined
// DONE WHEN:  id 3 gives you Chidi Park and id 99 gives you undefined.

// STEP 4: Write countByGrade
// WHAT:       Write `countByGrade(students)` that returns an object counting
//             how many students are in each grade:
//               { 9: 3, 10: 3, 11: 3, 12: 3 }
//             Only include grades that actually appear.
// WHY:        Turning a list into a summary is how every dashboard number gets
//             made. You'll do exactly this at Platinum for attendance counts.
// CONCEPTS:   Objects as lookups, building an object up, Array.prototype.reduce
//             (or a loop — either is fine here)
// DONE WHEN:  the counts add up to 12 and an empty array gives you an empty
//             object.

// STEP 5: Write getRegularAttendees
// WHAT:       Write `getRegularAttendees(students, minimum)` that returns the
//             students who attended at least `minimum` meetings, sorted by
//             meetingsAttended from highest to lowest. When two students have
//             the same count, put them in alphabetical order by name.
// WHY:        The tie-break is the real lesson. Without one, two students with
//             15 meetings each come out in whatever order the sort happened to
//             leave them — which can change between runs and makes a list that
//             jumps around for no reason.
//             Also: `.sort()` changes the array it is called on. You were given
//             that array. Make a copy first.
// CONCEPTS:   Array.prototype.sort, comparator functions, tie-breaking,
//             mutation, copying an array
// DONE WHEN:  with a minimum of 15 you get 7 students, the three on 15 come out
//             alphabetically, and the original array is untouched.

// STEP 6: Write formatStudent
// WHAT:       Write `formatStudent(student)` that returns a string in exactly
//             this shape:
//               Ada Nwosu (Grade 11, Software)
//             Pull the pieces out with a DESTRUCTURED parameter, and build the
//             string with a template literal.
// WHY:        Destructuring makes the function's needs obvious at a glance: you
//             can see it wants a name, a grade and a subteam without reading the
//             body. You will see this in every React component you write.
// CONCEPTS:   Object destructuring in parameters, template literals
// DONE WHEN:  the string matches exactly, including the comma and the brackets.

// STEP 7: Write getContactEmail
// WHAT:       Write `getContactEmail(student)` that returns the student's email
//             address, or the string 'no email on file' when there isn't one.
//             It must not crash on a student with no `contact` at all.
//             Use optional chaining (?.) and the nullish coalescing operator
//             (??) rather than a stack of if statements.
// WHY:        Real data has holes in it. An API returns a user with no profile,
//             a roster row was half filled in. `student.contact.email` throws
//             the moment `contact` is missing, and that single line has taken
//             down more screens than any algorithm.
//             Use `??` and not `||` here. Ask yourself what `||` would do with
//             an empty string.
// CONCEPTS:   Optional chaining (?.), nullish coalescing (??), the difference
//             between ?? and ||, undefined vs null
// DONE WHEN:  Ada gives her email, Emre (no contact) and Gia (contact but no
//             email) both give 'no email on file', and nothing throws.
