package seedu.superta.testutil;

import seedu.superta.logic.commands.UpdateAssignmentCommand.UpdateAssignmentDescriptor;
import seedu.superta.model.assignment.Assignment;
import seedu.superta.model.assignment.GradeBook;
import seedu.superta.model.assignment.Title;

/**
 * A utility class to help with building UpdateAssignmentDescriptor objects.
 */
public class UpdateAssignmentDescriptorBuilder {

    private UpdateAssignmentDescriptor descriptor;

    public UpdateAssignmentDescriptorBuilder() {
        descriptor = new UpdateAssignmentDescriptor();
    }

    public UpdateAssignmentDescriptorBuilder(UpdateAssignmentDescriptor descriptor) {
        this.descriptor = new UpdateAssignmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateAssignmentDescriptor} with fields containing {@code assignment}'s details
     */
    public UpdateAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new UpdateAssignmentDescriptor();
        descriptor.setAssignmentTitle(assignment.getTitle());
        descriptor.setMaxMarks(assignment.getMaxMarks());
        descriptor.setGradeBook(assignment.getGradebook());
    }

    /**
     * Sets the {@code assignmentTitle} of the {@code UpdateAssignmentDescriptor} that we are building.
     */
    public UpdateAssignmentDescriptorBuilder withAssignmentTitle(Title assignmentTitle) {
        descriptor.setAssignmentTitle(assignmentTitle);
        return this;
    }

    /**
     * Sets the {@code maxMarks} of the {@code UpdateAssignmentDescriptor} that we are building.
     */
    public UpdateAssignmentDescriptorBuilder withMaxMarks(Double maxMarks) {
        descriptor.setMaxMarks(maxMarks);
        return this;
    }

    /**
     * Sets the {@code gradeBook} of the {@code UpdateAssignmentDescriptor} that we are building.
     */
    public UpdateAssignmentDescriptorBuilder withGradeBook(GradeBook gradeBook) {
        descriptor.setGradeBook(gradeBook);
        return this;
    }

    public UpdateAssignmentDescriptor build() {
        return descriptor;
    }
}
