package seedu.address.testutil;

import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Name;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.AssignmentName;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {
    public static final String DEFAULT_ASSIGNMENT_NAME = "OASIS";
    public static final String DEFAULT_AUTHOR = "Amy Bee";
    public static final String DEFAULT_DESCRIPTION = "Project Management System for all.";

    private AssignmentName pName;
    private Name author;
    private Description description;

    public AssignmentBuilder() {
        pName = new AssignmentName(DEFAULT_ASSIGNMENT_NAME);
        author = new Name(DEFAULT_AUTHOR);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the AssignmentBuilder with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        pName = assignmentToCopy.getAssignmentName();
        author = assignmentToCopy.getAuthor();
        description = assignmentToCopy.getDescription();
    }

    /**
     * Sets the {@code Description} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAuthor(String author) {
        this.author = new Name(author);
        return this;
    }

    /**
     * Sets the {@code AssignmentName} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withAssignmentName(String pName) {
        this.pName = new AssignmentName(pName);
        return this;
    }

    public Assignment build() {
        return new Assignment(pName, author, description);
    }
}
