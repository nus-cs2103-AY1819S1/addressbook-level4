package tutorhelper.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_SUBJECT_INDEX;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_SYLLABUS;
import static tutorhelper.model.Model.PREDICATE_SHOW_ALL_STUDENTS;
import static tutorhelper.model.util.SubjectsUtil.createStudentWithNewSubjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.CommandHistory;
import tutorhelper.logic.commands.exceptions.CommandException;
import tutorhelper.model.Model;
import tutorhelper.model.student.Student;
import tutorhelper.model.subject.Subject;
import tutorhelper.model.subject.Syllabus;

/**
 * Adds a syllabus topic to a subject for a student in the TutorHelper.
 */
public class AddSyllCommand extends Command {

    public static final String COMMAND_WORD = "addsyll";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a syllabus topic to a subject for a student in the TutorHelper.\n"
            + "Parameters: "
            + "STUDENT_INDEX (must be a positive integer) "
            + "SUBJECT_INDEX (must be a positive integer) "
            + PREFIX_SYLLABUS + "SYLLABUS, [MORE SYLLABUSES]...\n"
            + "Example: " + COMMAND_WORD + " 1 1 " + PREFIX_SYLLABUS + "Integration";

    public static final String MESSAGE_ADDSYLL_SUCCESS = "Added syllabus to Student: %1$s";
    public static final String MESSAGE_DUPLICATE_SYLLABUS_IN_STUDENT = "Syllabus is already in Student: %1$s";
    public static final String MESSAGE_DUPLICATE_SYLLABUS_IN_ARGUMENT = "Duplicate syllabuses are not allowed";

    private final Index studentIndex;
    private final Index subjectIndex;
    private final List<Syllabus> syllabuses;

    public AddSyllCommand(Index studentIndex, Index subjectIndex, List<Syllabus> syllabuses) {
        requireNonNull(studentIndex);
        requireNonNull(subjectIndex);
        requireNonNull(syllabuses);
        this.studentIndex = studentIndex;
        this.subjectIndex = subjectIndex;
        this.syllabuses = syllabuses;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (studentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentTarget = lastShownList.get(studentIndex.getZeroBased());

        if (subjectIndex.getZeroBased() >= studentTarget.getSubjects().size()) {
            throw new CommandException(MESSAGE_INVALID_SUBJECT_INDEX);
        }

        Set<Subject> addedSubjectContent = addSubjectContentTo(studentTarget, subjectIndex, syllabuses);
        Student studentSubjUpdated = createStudentWithNewSubjects(studentTarget, addedSubjectContent);

        model.updateStudentInternalField(studentTarget, studentSubjUpdated);
        model.updateFilteredStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        model.commitTutorHelper();
        return new CommandResult(String.format(MESSAGE_ADDSYLL_SUCCESS, studentSubjUpdated));
    }

    /**
     * Add syllabus to the student.
     * @param studentTarget The student to add to.
     * @param subjectIndex The index of subject to add to.
     * @param syllabuses The list of syllabus to add.
     * @return a new {@code Set<Subject>} with the specified syllabus added
     */
    private Set<Subject> addSubjectContentTo(Student studentTarget, Index subjectIndex, Collection<Syllabus> syllabuses)
        throws CommandException {
        checkForDuplicates(syllabuses);
        List<Subject> subjects = new ArrayList<>(studentTarget.getSubjects());
        Subject selectedSubject = subjects.get(subjectIndex.getZeroBased());
        Subject updatedSubject = selectedSubject;
        for (Syllabus syllabus: syllabuses) {
            if (selectedSubject.contains(syllabus)) {
                throw new CommandException(String.format(MESSAGE_DUPLICATE_SYLLABUS_IN_STUDENT, studentTarget));
            }
            updatedSubject = updatedSubject.add(syllabus);
        }

        subjects.set(subjectIndex.getZeroBased(), updatedSubject);
        return new HashSet<>(subjects);
    }

    /**
     * @throws CommandException if duplicates exist within the {@code syllabuses}.
     */
    private void checkForDuplicates(Collection<Syllabus> syllabuses) throws CommandException {
        Set<Syllabus> duplicateCheck = new HashSet<>();
        for (Syllabus syllabus : syllabuses) {
            if (!duplicateCheck.add(syllabus)) {
                throw new CommandException(MESSAGE_DUPLICATE_SYLLABUS_IN_ARGUMENT);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddSyllCommand // instanceof handles nulls
                && studentIndex.equals(((AddSyllCommand) other).studentIndex))
                && syllabuses.equals(((AddSyllCommand) other).syllabuses); // state check
    }
}
