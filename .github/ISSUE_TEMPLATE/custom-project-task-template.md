---
name: Custom project task template
about: Describe a project task.

---

**Issue Title Template:**
a descriptive title for a reasonable sized, standalone task. Such tasks should be able to be done by one person, in a few hours.

For example:
Update class diagram in the Developer Guide for v1.4
Implementing parser support for adding of floating tasks
Add support for the 'undo' command to the parser

**Issue Body Template:**
issue body is optional

**Assignment of Issue:**
Assign tasks (i.e., issues) to the corresponding team members using the assignees field.
Assign issues to milestones.

**From CS2103 website**:
Define project tasks as issues. When you start implementing a user story (or a feature), break it down to smaller tasks if necessary. Define reasonable sized, standalone tasks. Create issues for each of those tasks so that they can be tracked.e.g.

* A typical task should be able to done by one person, in a few hours.
 Bad (reasons: not a one-person task, not small enough): Write the Developer Guide
 Good: Update class diagram in the Developer Guide for v1.4

* There is no need to break things into VERY small tasks. Keep them as big as possible, but they should be no bigger than what you are going to assign a single person to do within a week. eg.,
 Bad:Implementing parser (reason: too big).
 Good:Implementing parser support for adding of floating tasks

* Do not track things taken for granted. e.g., push code to repo should not be a task to track. In the example given under the previous point, it is taken for granted that the owner will also (a) test the code and (b) push to the repo when it is ready. Those two need not be tracked as separate tasks.

* Write a descriptive title for the issue. e.g. Add support for the 'undo' command to the parser

* Omit redundant details. In some cases, the issue title is enough to describe the task. In that case, no need to repeat it in the issue description. There is no need for well-crafted and detailed descriptions for tasks. A minimal description is enough. Similarly, labels such as priority can be omitted if you think they don't help you.

* Assign tasks (i.e., issues) to the corresponding team members using the assignees field. Normally, there should be some ongoing tasks and some pending tasks against each team member at any point.

* Optionally, you can use status.ongoing label to indicate issues currently ongoing.
