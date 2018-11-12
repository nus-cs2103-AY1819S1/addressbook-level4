package seedu.superta.model.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.superta.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.stream.Collectors;

import seedu.superta.model.student.Student;
import seedu.superta.model.student.StudentId;

/**
 * Represents an Assignment in the client.
 * Guarantees: immutable.
 */
public class Assignment {

    public static final String MESSAGE_GRADE_CONSTRAINTS =
        "Grade should be above zero and within max marks.";

    public static final String MESSAGE_MAXMARKS_CONSTRAINTS =
        "Max marks should be above zero.";

    private final Title title;
    private final Double maxMarks;
    private final GradeBook gradebook;

    /**
     * Constructs a {@code Assignment}.
     */
    public Assignment(Title title, Double maxMarks) {
        requireAllNonNull(title, maxMarks);
        this.title = title;
        this.maxMarks = maxMarks;
        this.gradebook = new GradeBook();
    }

    public Assignment(Title title, Double maxMarks, GradeBook gradebook) {
        requireAllNonNull(title, maxMarks, gradebook);
        this.title = title;
        this.maxMarks = maxMarks;
        this.gradebook = gradebook;
    }

    public Assignment(Assignment toClone) {
        requireNonNull(toClone);
        this.title = toClone.title;
        this.maxMarks = toClone.maxMarks;
        gradebook = new GradeBook();
        toClone.gradebook.stream()
            .forEach(e -> gradebook.addGrade(e.studentId, e.marks));
    }


    public Title getTitle() {
        return title;
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public GradeBook getGradebook() {
        return gradebook;
    }

    /**
     * Returns true if both assignments of the same title have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two assignments.
     */
    public boolean isSameAssignment(Assignment otherAssignment) {
        if (otherAssignment == this) {
            return true;
        }

        return otherAssignment != null
            && otherAssignment.getTitle().equals(getTitle());
    }

    /**
     * Grades a certain student with specified marks.
     * @param studentId the student id.
     * @param marks the marks given.
     */
    public void grade(StudentId studentId, Double marks) {
        // TODO: Enforce marks < maxMarks, if not throw exception
        requireAllNonNull(studentId, marks);

        gradebook.addGrade(studentId, marks);
    }

    /**
     * Returns the average for this assignment.
     */
    public double getAverage() {
        return gradebook.getAverage();
    }

    /**
     * Returns the median for this assignment.
     */
    public double getMedian() {
        return gradebook.getMedian();
    }

    /**
     * Returns the projected difficulty for this assignment.
     * Projected difficulty is currently 1 - average percentage scored.
     */
    public double getProjectedDifficulty() {
        if (gradebook.asUnmodifiableObservableList().size() == 0) {
            return 0.0;
        }
        double average = gradebook.getAverage();
        double averagePercent = average / maxMarks;

        return (1 - averagePercent) * 10;
    }

    /**
     * Removes a students reference from the grade book, if student is inside.
     */
    public void removeStudentReferences(Student target) {
        gradebook.removeStudentReference(target);
    }


    /**
     * Returns true if both assignments have the same identity and data fields.
     * This defines a stronger notion of equality between two assignments.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Assignment)) {
            return false;
        }

        Assignment otherStudent = (Assignment) other;
        return otherStudent.getTitle().equals(getTitle())
            && otherStudent.getMaxMarks().equals(getMaxMarks())
            && otherStudent.getGradebook().equals(getGradebook());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, maxMarks, gradebook);
    }

    @Override
    public String toString() {
        return "[Assignment: " + title + "]"
            + " [Max Marks: " + maxMarks + "]\n"
            + gradebook.stream()
                .map(GradeEntry::toString)
                .collect(Collectors.joining("\n"));
    }
}
