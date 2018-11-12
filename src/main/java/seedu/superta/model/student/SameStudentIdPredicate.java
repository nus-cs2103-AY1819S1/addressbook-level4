package seedu.superta.model.student;

import java.util.function.Predicate;

/**
 * Tests that the {@code Student} has the same {@code StudentId} as the given student Id.
 */
public class SameStudentIdPredicate implements Predicate<Student> {
    private final String studentId;

    public SameStudentIdPredicate(String studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean test(Student student) {
        return studentId.equalsIgnoreCase(student.getStudentId().toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SameStudentIdPredicate // instanceof handles nulls
                && studentId.equals(((SameStudentIdPredicate) other).studentId)); // state check
    }
}
