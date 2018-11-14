package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.assignment.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignments {

    public static final Assignment ASSIGNMENT_AA = new AssignmentBuilder().withName("Assignment AA")
            .withDeadline("01/01/2019")
            .withWeight("10")
            .withMaxMark("10").build();
    public static final Assignment ASSIGNMENT_BB = new AssignmentBuilder().withName("Assignment BB")
            .withDeadline("02/02/2019")
            .withWeight("20")
            .withMaxMark("20").build();
    public static final Assignment ASSIGNMENT_CC = new AssignmentBuilder().withName("Assignment CC")
            .withDeadline("03/03/2019")
            .withWeight("30")
            .withMaxMark("30").build();

    // Manually added - Assignment's details found in {@code CommandTestUtil}
    public static final Assignment ASSIGNMENT_A = new AssignmentBuilder().withName("Assignment A")
            .withDeadline("04/04/2019")
            .withWeight("40")
            .withMaxMark("40").build();
    public static final Assignment ASSIGNMENT_B = new AssignmentBuilder().withName("Assignment B")
            .withDeadline("05/05/2019")
            .withWeight("50")
            .withMaxMark("50").build();

    private TypicalAssignments() {} // prevents instantiation

    public static List<Assignment> getTypicalAssignment() {
        return new ArrayList<>(Arrays.asList(ASSIGNMENT_AA, ASSIGNMENT_BB, ASSIGNMENT_CC));
    }
}
