// ============================================================
// RANK:        Web Copper: Student Filter
// FILE:        src/studentUtils.js
// STEPS HERE:  1 to 7
// GUIDE:       GUIDE_URL  (section "Web Copper")
// RUN:         npm start        CHECK: npm run check
// PASSES WHEN: all checks pass, there is no `var` or `==`, and the original
//              students array is never modified.
// ============================================================
//
// Seven small functions over the team roster. Each one is the kind of thing you
// will write a hundred times on a real app, and each one has a matching array
// method that does most of the work — the skill is picking the right one.
//
// Every function must be EXPORTED, by exactly the name in its step. The checks
// import them by name.
//
// One rule that runs through all of this: NEVER change the array you were
// given. Make a new one instead. The checks freeze the inputs, so if you try to
// modify one, you will find out immediately. That rule exists because in React
// — two ranks from now — changing data in place is the single most common
// reason a screen refuses to update.
//
// The data lives in src/students.js. Go read it first.

// STEP 1: Write getStudentNames
// WHAT:       Export a function `getStudentNames(students)` that returns an
//             array of just the names, in the same order.
//             getStudentNames(students) -> ['Ada Nwosu', 'Bo Tran', ...]
// WHY:        Turning a list of things into a list of one piece of those things
//             is the most common operation in front-end code. Every dropdown,
//             every list of chips, every "who's here today" is this.
// CONCEPTS:   Array.prototype.map, arrow functions, export
// READ:       Guide > Web Copper > Resources #3
// CHECKED BY: getStudentNames.check.js
// DONE WHEN:  you get 12 names back, in the original order, and the original
//             array is untouched.

// STEP 2: Write getStudentsBySubteam
// WHAT:       Export `getStudentsBySubteam(students, subteam)` that returns only
//             the students on that subteam. Return the student objects, not just
//             their names. If nobody matches, return an empty array — not null,
//             not undefined.
// WHY:        "Show me only the ones that..." is what every filter control on
//             every page is doing underneath.
// CONCEPTS:   Array.prototype.filter, comparison, returning a new array
// READ:       Guide > Web Copper > Resources #3
// CHECKED BY: getStudentsBySubteam.check.js
// DONE WHEN:  'Software' gives you 5 students, 'Robotics' gives you an empty
//             array, and the original array is untouched.

// STEP 3: Write findStudentById
// WHAT:       Export `findStudentById(students, id)` that returns the one
//             student with that id, or `undefined` if there isn't one.
// WHY:        This is what happens every time someone taps a row and a detail
//             screen opens. Note the difference from step 2: one thing, not a
//             list of things.
// CONCEPTS:   Array.prototype.find, the difference between find and filter,
//             undefined
// READ:       Guide > Web Copper > Resources #3
// CHECKED BY: findStudentById.check.js
// DONE WHEN:  id 3 gives you Chidi Park and id 99 gives you undefined.

// STEP 4: Write countByGrade
// WHAT:       Export `countByGrade(students)` that returns an object counting
//             how many students are in each grade:
//               { 9: 3, 10: 3, 11: 3, 12: 3 }
//             Only include grades that actually appear.
// WHY:        Turning a list into a summary is how every dashboard number gets
//             made. You'll do exactly this at Platinum for attendance counts.
// CONCEPTS:   Objects as lookups, building an object up, Array.prototype.reduce
//             (or a loop — either is fine here)
// READ:       Guide > Web Copper > Resources #2 and #3
// CHECKED BY: countByGrade.check.js
// DONE WHEN:  the counts add up to 12 and an empty array gives you an empty
//             object.

// STEP 5: Write getRegularAttendees
// WHAT:       Export `getRegularAttendees(students, minimum)` that returns the
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
// READ:       Guide > Web Copper > Resources #3
// CHECKED BY: getRegularAttendees.check.js
// DONE WHEN:  with a minimum of 15 you get 7 students, the three on 15 come out
//             alphabetically, and the original array is untouched.

// STEP 6: Write formatStudent
// WHAT:       Export `formatStudent(student)` that returns a string in exactly
//             this shape:
//               Ada Nwosu (Grade 11, Software)
//             Pull the pieces out with a DESTRUCTURED parameter, and build the
//             string with a template literal.
// WHY:        Destructuring makes the function's needs obvious at a glance: you
//             can see it wants a name, a grade and a subteam without reading the
//             body. You will see this in every React component you write.
// CONCEPTS:   Object destructuring in parameters, template literals
// READ:       Guide > Web Copper > Resources #1 and #2
// CHECKED BY: formatStudent.check.js, sourceScan.check.js (the parameter must be
//             destructured)
// DONE WHEN:  the string matches exactly, including the comma and the brackets.

// STEP 7: Write getContactEmail
// WHAT:       Export `getContactEmail(student)` that returns the student's email
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
// READ:       Guide > Web Copper > Resources #2
// CHECKED BY: getContactEmail.check.js, sourceScan.check.js (both ?. and ?? must
//             appear)
// DONE WHEN:  Ada gives her email, Emre (no contact) and Gia (contact but no
//             email) both give 'no email on file', and nothing throws.
