package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.project.Assignment;

public class TypicalAssignment {

    public static final Assignment OASIS = new AssignmentBuilder().withAssignmentName("OASIS").withAuthor("Amy Bee")
            .withDescription("Project Management System for all.").build();
    public static final Assignment FALCON = new AssignmentBuilder().withAssignmentName("FALCON").withAuthor("Bob Choo")
            .withDescription("Home Security Camera.").build();

    private TypicalAssignment() {} // prevents instantiation

    public static List<Assignment> getTypicalAssignments() {
        return new ArrayList<>(Arrays.asList(OASIS, FALCON));
    }
}
