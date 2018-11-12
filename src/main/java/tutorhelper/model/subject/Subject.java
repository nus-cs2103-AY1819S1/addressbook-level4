package tutorhelper.model.subject;

import static java.util.Objects.requireNonNull;
import static tutorhelper.commons.core.Messages.MESSAGE_INVALID_SYLLABUS_INDEX;
import static tutorhelper.commons.util.AppUtil.checkArgument;
import static tutorhelper.model.subject.SubjectType.convertStringToSubjectName;
import static tutorhelper.model.subject.SubjectType.isValidSubjectName;

import java.util.ArrayList;
import java.util.List;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.commands.exceptions.CommandException;

/**
 * Represents a Subject in the TutorHelper.
 * Guarantees: immutable; name is valid as declared in {@link SubjectType#isValidSubjectName(String)}
 */
public class Subject {

    public static final String MESSAGE_SUBJECT_CONSTRAINTS =
            "Subject name should match one of the following: " + SubjectType.listRepresentation();

    private final SubjectType subjectType;
    private final List<Syllabus> subjectContent = new ArrayList<>();
    private final float completionRate;

    /**
     * Constructor to guarantee immutability.
     * @param subjectType the subject type
     * @param subjectContent the content of the subject
     * @param completionRate the completion rate of subject
     */
    public Subject(SubjectType subjectType, List<Syllabus> subjectContent, float completionRate) {
        requireNonNull(subjectType);
        this.subjectType = subjectType;
        this.subjectContent.addAll(subjectContent);
        this.completionRate = completionRate;
    }

    /**
     * Constructs a new {@code Subject} from {@code String subjectName}.
     *
     * @param subjectName Subjects that the student is taking.
     */
    public static Subject makeSubject (String subjectName) {
        requireNonNull(subjectName);
        checkArgument(isValidSubjectName(subjectName), MESSAGE_SUBJECT_CONSTRAINTS);
        Subject subject = new Subject(convertStringToSubjectName(subjectName), new ArrayList<>(), 0);
        return subject;
    }

    /**
     * Returns subjectType as {@code SubjectType}.
     */
    public SubjectType getSubjectType() {
        return subjectType;
    }

    /**
     * Returns subjectType in string in {@code String}.
     */
    public String getSubjectName() {
        return subjectType.stringRepresentation;
    }

    public List<Syllabus> getSubjectContent() {
        return subjectContent;
    }

    public float getCompletionRate() {
        return completionRate;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getSubjectName() + ": \n\n");
        builder.append(contentToString());
        return builder.toString();
    }

    /**
     * Returns a string containing information regarding the syllabus topics of the subject.
     * @return The string representation of the syllabus topics.
     */
    public String contentToString() {
        final StringBuilder builder = new StringBuilder();
        Index numbering;

        for (int i = 0; i < getSubjectContent().size(); i++) {
            numbering = Index.fromZeroBased(i);
            builder.append(numbering.getOneBased() + ". ")
                    .append(getSubjectContent().get(i).toString()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns true if both subjects have the same {@code SubjectType} and content
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Subject // instanceof handles nulls
                && subjectType.equals(((Subject) other).subjectType));
    }

    /**
     * Returns true if both subjects have the same {@code SubjectType}.
     * Defines a weaker notion of equal for class {@code Subject}.
     */
    public boolean hasTypeOf(SubjectType type) {
        return getSubjectType().equals(type);
    }


    @Override
    public int hashCode() {
        return subjectType.hashCode();
    }

    /**
     * Add a {@code Syllabus} to the current subject and returns
     * a new {@code Subject} containing the newly added syllabus.
     * @param syllabus the {@code Syllabus} to be added
     * @return a new {@code Subject} containing the newly added syllabus
     */
    public Subject add(Syllabus syllabus) {
        List<Syllabus> newSubjectContent = new ArrayList<>(getSubjectContent());
        newSubjectContent.add(syllabus);
        return new Subject(getSubjectType(), newSubjectContent, getCompletionRate()).updateCompletionRate();
    }

    /**
     * Removes a {@code Syllabus} from the current subject and returns
     * a new {@code Subject} with the syllabus at given index removed.
     * @param index the index of syllabus to be removed
     * @return a new {@code Subject} with the syllabus at given index removed.
     *
     * @throws CommandException if {@code index} is out of bound of the subjectContent.
     */
    public Subject remove(Index index) throws CommandException {
        if (index.getOneBased() > getSubjectContent().size()) {
            throw new CommandException(MESSAGE_INVALID_SYLLABUS_INDEX);
        }

        List<Syllabus> newSubjectContent = new ArrayList<>(getSubjectContent());
        newSubjectContent.remove(index.getZeroBased());
        return new Subject(getSubjectType(), newSubjectContent, getCompletionRate()).updateCompletionRate();
    }

    /**
     * Append a {@code List<Syllabus>} to the current subject and returns
     * a new {@code Subject} containing the newly added syllabus list.
     * @param syllabusList the {@code List<Syllabus>} to be added
     * @return a new {@code Subject} containing the newly added syllabus list.
     */
    public Subject append(List<Syllabus> syllabusList) {
        List<Syllabus> newSubjectContent = new ArrayList<>(getSubjectContent());
        for (Syllabus newSyllabus: syllabusList) {
            if (!newSubjectContent.contains(newSyllabus)) {
                newSubjectContent.add(newSyllabus);
            }
        }
        return new Subject(getSubjectType(), newSubjectContent, getCompletionRate()).updateCompletionRate();

    }

    /**
     * Find a {@code List<Syllabus>} to the current subject and returns
     * a new {@code Subject} containing the newly added syllabus list.
     * @param syllabus the {@code List<Syllabus>} to be added.
     * @param index the index to replace old syllabus.
     * @return a new {@code Subject} containing the newly added syllabus list.
     */
    public Subject edit(Syllabus syllabus, Index index) throws CommandException {
        if (index.getOneBased() > getSubjectContent().size()) {
            throw new CommandException(MESSAGE_INVALID_SYLLABUS_INDEX);
        }
        List<Syllabus> newSubjectContent = new ArrayList<>(getSubjectContent());
        newSubjectContent.set(index.getZeroBased(), syllabus);
        return new Subject(getSubjectType(), newSubjectContent, getCompletionRate()).updateCompletionRate();

    }

    public boolean contains(Syllabus syllabus) {
        return getSubjectContent().contains(syllabus);
    }

    /**
     * Return a new {@code Subject} with the state of the syllabus
     * identified by the {@code Index index} flipped.
     * @param index the index of syllabus
     * @return new {@code Subject} with the changed syllabus
     * @throws CommandException if index is out of bound of the subjectContent list
     */
    public Subject toggleState(Index index) throws CommandException {
        if (index.getOneBased() > getSubjectContent().size()) {
            throw new CommandException(MESSAGE_INVALID_SYLLABUS_INDEX);
        }

        List<Syllabus> newSubjectContent = new ArrayList<>(getSubjectContent());
        Syllabus oldSyllabus = newSubjectContent.get(index.getZeroBased());
        Syllabus newSyllabus = new Syllabus(oldSyllabus.syllabus, !oldSyllabus.state);
        newSubjectContent.set(index.getZeroBased(), newSyllabus);

        return new Subject(getSubjectType(), newSubjectContent, getCompletionRate()).updateCompletionRate();
    }

    /**
     * Recalculate the completion rate of the subject.
     * @return a new {@code Subject} with updated completion rate.
     */
    public Subject updateCompletionRate() {
        int subjectContentLength = getSubjectContent().size();
        int numOfSyllabusCompleted = 0;

        for (Syllabus syllabus: getSubjectContent()) {
            if (syllabus.state) {
                numOfSyllabusCompleted++;
            }
        }
        float completionRate = (float) numOfSyllabusCompleted / subjectContentLength;
        return new Subject(getSubjectType(), getSubjectContent(), completionRate);
    }
}
