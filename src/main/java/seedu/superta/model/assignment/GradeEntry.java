package seedu.superta.model.assignment;

import seedu.superta.model.student.StudentId;

// @@author Caephler
/**
 * Wrapper class for each entry in the grade book.
 */
public class GradeEntry {
    public final StudentId studentId;
    public final Double marks;

    public GradeEntry(StudentId studentId, Double marks) {
        this.studentId = studentId;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return studentId.studentId + ": " + marks;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GradeEntry)) {
            return false;
        }
        GradeEntry otherEntry = (GradeEntry) other;
        return studentId.equals(otherEntry.studentId)
            && marks.equals(otherEntry.marks);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
