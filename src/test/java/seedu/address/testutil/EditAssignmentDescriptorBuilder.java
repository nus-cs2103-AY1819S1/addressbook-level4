package seedu.address.testutil;

import seedu.address.logic.commands.EditAssignmentCommand;
import seedu.address.model.leaveapplication.Description;
import seedu.address.model.person.Name;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.ProjectName;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditAssignmentDescriptorBuilder {

    private EditAssignmentCommand.EditAssignmentDescriptor descriptor;

    public EditAssignmentDescriptorBuilder() {
        descriptor = new EditAssignmentCommand.EditAssignmentDescriptor();
    }

    public EditAssignmentDescriptorBuilder(EditAssignmentCommand.EditAssignmentDescriptor descriptor) {
        this.descriptor = new EditAssignmentCommand.EditAssignmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAssignmentDescriptor} with fields containing {@code assignment}'s details
     */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentCommand.EditAssignmentDescriptor();
        descriptor.setAssignmentName(assignment.getProjectName());
        descriptor.setAuthor(assignment.getAuthor());
        descriptor.setDescription(assignment.getDescription());
    }

    /**
     * Sets the {@code AssignmentName} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withAssignmentName(String name) {
        descriptor.setAssignmentName(new ProjectName(name));
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Name(author));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditAssignmentCommand.EditAssignmentDescriptor build() {
        return descriptor;
    }
}
