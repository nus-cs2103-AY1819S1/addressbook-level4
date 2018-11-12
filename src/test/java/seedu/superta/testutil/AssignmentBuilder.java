package seedu.superta.testutil;

import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.GradeBook;
import seedu.superta.model.assignment.Title;

/**
 * A utility class to help with building Assignment objects.
 */
public class AssignmentBuilder {

    public static final String DEFAULT_TITLE = "Tutorial Worksheet";
    public static final Double DEFAULT_MAXMARKS = 100.0;

    private Title title;
    private Double maxMarks;
    private GradeBook gradebook;

    public AssignmentBuilder() {
        title = new Title(DEFAULT_TITLE);
        maxMarks = DEFAULT_MAXMARKS;
        gradebook = new GradeBook();
    }

    /**
     * Initializes the AssignmentBuilder with the data of {@code assignmentToCopy}.
     */
    public AssignmentBuilder(Assignment assignmentToCopy) {
        title = assignmentToCopy.getTitle();
        maxMarks = assignmentToCopy.getMaxMarks();
        gradebook = assignmentToCopy.getGradebook();
    }

    /**
     * Sets the {@code Title} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withTitle(String title) {
        this.title = new Title(title);
        return this;
    }

    /**
     * Sets the {@code maxmarks} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
        return this;
    }

    /**
     * Sets the {@code GradeBook} of the {@code Assignment} that we are building.
     */
    public AssignmentBuilder withGradeBook(GradeBook gradeBook) {
        this.gradebook = gradeBook;
        return this;
    }

    public Assignment build() {
        return new Assignment(title, maxMarks, gradebook);
    }

}
