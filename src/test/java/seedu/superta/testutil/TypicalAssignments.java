package seedu.superta.testutil;

import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.GradeBook;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment TUT = new AssignmentBuilder().withTitle("Tutorial Worksheet")
        .withMaxMarks(100.0)
        .withGradeBook(new GradeBook()).build();

    public static final Assignment LAB = new AssignmentBuilder().withTitle("Lab Worksheet")
        .withMaxMarks(50.0)
        .withGradeBook(new GradeBook()).build();
}
