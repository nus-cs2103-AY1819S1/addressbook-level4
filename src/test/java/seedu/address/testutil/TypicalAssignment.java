package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AssignmentList;
import seedu.address.model.project.Assignment;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAssignment {

    public static final Assignment OASIS = new AssignmentBuilder().withAssignmentName("OASIS").withAuthor("Amy Bee")
            .withDescription("Project Management System for all.").build();
    public static final Assignment FALCON = new AssignmentBuilder().withAssignmentName("FALCON").withAuthor("Bob Choo")
            .withDescription("Home Security Camera.").build();

    private TypicalAssignment() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AssignmentList getTypicalAssignmentList() {
        AssignmentList aList = new AssignmentList();
        for (Assignment assignment : getTypicalAssignments()) {
            aList.addAssignment(assignment);
        }
        return aList;
    }

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(OASIS, FALCON));
    }
}
