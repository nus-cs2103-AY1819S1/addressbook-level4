package tutorhelper.logic.commands;

import static java.util.Objects.requireNonNull;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_SUBJECT_INDEX;
import static tutorhelper.model.util.SubjectsUtil.copySubjectFrom;
import static tutorhelper.model.util.SubjectsUtil.createStudentWithNewSubjects;
import static tutorhelper.model.util.SubjectsUtil.findSubjectIndex;
import static tutorhelper.model.util.SubjectsUtil.hasSubject;

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
 * Copies a subject and its syllabus topics from one student to another.
 */
public class CopySubCommand extends Command {

    public static final String COMMAND_WORD = "copysub";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies a subject and its syllabus topics from one student to another. "
            + "New syllabus topics will be appended in if the student is already taking the subject.\n"
            + "Parameters: "
            + "SOURCE_STUDENT_INDEX (must be a positive integer) "
            + "SUBJECT_INDEX (must be a positive integer) "
            + "TARGET_STUDENT_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 2 4";

    public static final String MESSAGE_COPYSUB_SUCCESS = "Copied syllabus to Student: %1$s";
    public static final String MESSAGE_COPYSUB_FAILED_SAME_STUDENT =
            "Copying subject to the same student is not allowed: %1$s";

    private final Index sourceStudentIndex;
    private final Index subjectIndex;
    private final Index targetStudentIndex;

    /**
     * Creates a CopySubCommand to copy the subject at the {@code subjectIndex} from the student at the
     * {@code sourceStudentIndex} to the student at the {@code targetStudentIndex}.
     */
    public CopySubCommand(Index sourceStudentIndex, Index subjectIndex, Index targetStudentIndex) {
        this.sourceStudentIndex = sourceStudentIndex;
        this.subjectIndex = subjectIndex;
        this.targetStudentIndex = targetStudentIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Student> lastShownList = model.getFilteredStudentList();

        if (sourceStudentIndex.getZeroBased() >= lastShownList.size()
            || targetStudentIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        Student studentSource = lastShownList.get(sourceStudentIndex.getZeroBased());
        Student studentTarget = lastShownList.get(targetStudentIndex.getZeroBased());

        if (sourceStudentIndex.equals(targetStudentIndex)) {
            throw new CommandException(String.format(MESSAGE_COPYSUB_FAILED_SAME_STUDENT, studentSource));
        }

        if (subjectIndex.getZeroBased() >= studentSource.getSubjects().size()) {
            throw new CommandException(MESSAGE_INVALID_SUBJECT_INDEX);
        }

        Subject selectedSubject = copySubjectFrom(studentSource, subjectIndex);
        Set<Subject> updatedSubjects = updateSubjectsFor(studentTarget, selectedSubject);
        Student studentUpdated = createStudentWithNewSubjects(studentTarget, updatedSubjects);

        model.updateStudent(studentTarget, studentUpdated);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        model.commitTutorHelper();
        return new CommandResult(String.format(MESSAGE_COPYSUB_SUCCESS, studentUpdated));
    }

    /**
     * Add subject into studentTarget. If studentTarget already has the same subject, append the content
     * instead.
     * @param studentTarget the student to be updated
     * @param newSubject the subject to be added
     * @return a new {@code Set<Subject>} with updated subjects
     */
    private Set<Subject> updateSubjectsFor(Student studentTarget, Subject newSubject) {
        List<Subject> targetSubjects = new ArrayList<>(studentTarget.getSubjects());

        if (hasSubject(studentTarget, newSubject.getSubjectType())) {
            Index index = findSubjectIndex(studentTarget, newSubject.getSubjectType()).get();
            Subject updatedSubject = targetSubjects.get(index.getZeroBased())
                                                   .append(newSubject.getSubjectContent());
            targetSubjects.set(index.getZeroBased(), updatedSubject);
        } else {
            targetSubjects.add(newSubject);
        }

        return new HashSet<>(targetSubjects);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopySubCommand // instanceof handles nulls
                && sourceStudentIndex.equals(((CopySubCommand) other).sourceStudentIndex)
                && targetStudentIndex.equals(((CopySubCommand) other).targetStudentIndex)
                && subjectIndex.equals(((CopySubCommand) other).subjectIndex));
    }
}
