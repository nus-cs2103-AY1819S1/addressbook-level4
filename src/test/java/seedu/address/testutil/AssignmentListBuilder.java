package seedu.address.testutil;

import seedu.address.model.AssignmentList;
import seedu.address.model.project.Assignment;

/**
 * A utility class to help with building AssignmentList objects.
 * Example usage: <br>
 *     {@code AssignmentList assignmentlist = new AssignmentListBuilder().withAssignment("OASIS").build();}
 */
public class AssignmentListBuilder {

    private AssignmentList assignmentList;

    public AssignmentListBuilder() {
        assignmentList = new AssignmentList();
    }

    public AssignmentListBuilder(AssignmentList assignmentList) {
        this.assignmentList = assignmentList;
    }

    /**
     * Adds a new {@code Assignment} to the {@code AssignmentList} that we are building.
     */
    public AssignmentListBuilder withAssignment(Assignment assignment) {
        assignmentList.addAssignment(assignment);
        return this;
    }

    public AssignmentList build() {
        return assignmentList;
    }
}
