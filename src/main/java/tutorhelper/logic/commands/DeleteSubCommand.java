package tutorhelper.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_SUBJECT_INDEX;
import static tutorhelper.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static tutorhelper.model.util.SubjectsUtil.createStudentWithNewSubjects;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.CommandHistory;
import tutorhelper.logic.commands.exceptions.CommandException;
import tutorhelper.model.Model;
import tutorhelper.model.student.Student;
import tutorhelper.model.subject.Subject;

/**
 * Deletes a subject for a student in the TutorHelper.
 */
public class DeleteSubCommand extends Command {

    public static final String COMMAND_WORD = "deletesub";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a subject for a student in the TutorHelper.\n"
            + "Parameters: "
            + "STUDENT_INDEX (must be a positive integer) "
            + "SUBJECT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + "2";

    public static final String MESSAGE_DELETESUB_SUCCESS = "Deleted subject from student: %1$s";
    public static final String MESSAGE_DELETE_ONLY_SUBJECT = "At least one subject must be studied by student: %1$s";

    private final Index studentIndex;
    private final Index subjectIndex;

    /**
     * Creates a DeleteSubCommand to delete the subject at the {@code subjectIndex}
     * from the student at the {@code studentIndex}.
     */
    public DeleteSubCommand(Index studentIndex, Index subjectIndex) {
        this.studentIndex = studentIndex;
        this.subjectIndex = subjectIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentTarget = lastShownList.get(studentIndex.getZeroBased());
        Set<Subject> newSubjects = removeSubjectFrom(studentTarget);
        Student updatedStudent = createStudentWithNewSubjects(studentTarget, newSubjects);

        model.updateStudent(studentTarget, updatedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.commitTutorHelper();
        return new CommandResult(String.format(MESSAGE_DELETESUB_SUCCESS, studentTarget));
    }

    /**
     * Deletes a subject from a student.
     * @param studentTarget The student to add the subject to.
     * @return A new {@code Set<Subject>} without the subject.
     */
    private Set<Subject> removeSubjectFrom(Student studentTarget)
            throws CommandException {
        List<Subject> subjects = new ArrayList<>(studentTarget.getSubjects());

        if (isSubjectIndexOutOfBounds(subjects)) {
            throw new CommandException(MESSAGE_INVALID_SUBJECT_INDEX);
        }

        if (hasOneSubject(subjects)) {
            throw new CommandException(String.format(MESSAGE_DELETE_ONLY_SUBJECT, studentTarget));
        }

        List<Subject> updatedSubjects = subjects;
        updatedSubjects.remove(subjectIndex.getZeroBased());
        return new HashSet<>(subjects);
    }

    private boolean isSubjectIndexOutOfBounds(List<Subject> subjects) {
        return subjectIndex.getOneBased() > subjects.size();
    }

    private boolean hasOneSubject(List<Subject> subjects) {
        return subjects.size() == 1;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteSubCommand // instanceof handles nulls
                && studentIndex.equals(((DeleteSubCommand) other).studentIndex))
                && subjectIndex.equals(((DeleteSubCommand) other).subjectIndex); // state check
    }
}
