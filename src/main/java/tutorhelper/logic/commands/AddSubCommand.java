package tutorhelper.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_SUBJECT;
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
 * Adds a subject for a student in the TutorHelper.
 */
public class AddSubCommand extends Command {

    public static final String COMMAND_WORD = "addsub";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a subject for a student in the TutorHelper.\n"
            + "Parameters: "
            + "STUDENT_INDEX (must be a positive integer) "
            + PREFIX_SUBJECT + "SUBJECT\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_SUBJECT + "Physics ";

    public static final String MESSAGE_ADDSUB_SUCCESS = "Added subject to student: %1$s";
    public static final String MESSAGE_DUPLICATE_SUBJECT = "Subject is already taken by student: %1$s";

    private final Index studentIndex;
    private final Subject subject;

    /**
     * Creates an AddSubCommand to add the {@code Subject} to the student at the {@code studentIndex}.
     */
    public AddSubCommand(Index studentIndex, Subject subject) {
        requireNonNull(studentIndex);
        requireNonNull(subject);
        this.studentIndex = studentIndex;
        this.subject = subject;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentTarget = lastShownList.get(studentIndex.getZeroBased());
        Set<Subject> newSubjects = addSubjectTo(studentTarget, subject);
        Student updatedStudent = createStudentWithNewSubjects(studentTarget, newSubjects);

        model.updateStudent(studentTarget, updatedStudent);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.commitTutorHelper();
        return new CommandResult(String.format(MESSAGE_ADDSUB_SUCCESS, studentTarget));
    }

    /**
     * Add a subject to a student.
     * @param studentTarget The student to add the subject to.
     * @param subject The subject to add to the student.
     * @return A new {@code Set<Subject>} with the specified subject added.
     */
    private Set<Subject> addSubjectTo(Student studentTarget, Subject subject)
            throws CommandException {
        List<Subject> subjects = new ArrayList<>(studentTarget.getSubjects());

        if (subjects.contains(subject)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_SUBJECT, studentTarget));
        }

        List<Subject> updatedSubjects = subjects;
        updatedSubjects.add(subject);
        return new HashSet<>(subjects);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSubCommand // instanceof handles nulls
                && studentIndex.equals(((AddSubCommand) other).studentIndex))
                && subject.equals(((AddSubCommand) other).subject); // state check
    }
}
