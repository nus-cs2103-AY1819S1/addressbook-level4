package seedu.address.testutil;

import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.assignment.AssignmentName;
import seedu.address.model.assignment.Deadline;
import seedu.address.model.assignment.Weight;
import seedu.address.model.common.Mark;

/**
 * A utility class to help with building EditAssignmentDescriptor objects.
 */
public class EditAssignmentDescriptorBuilder {

    private EditAssignmentDescriptor descriptor;

    public EditAssignmentDescriptorBuilder() {
        descriptor = new EditAssignmentDescriptor();
    }

    public EditAssignmentDescriptorBuilder(EditAssignmentDescriptor descriptor) {
        this.descriptor = new EditAssignmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAssignmentDescriptor} with fields containing {@code assignment}'s details
     */
    public EditAssignmentDescriptorBuilder(Assignment assignment) {
        descriptor = new EditAssignmentDescriptor();
        descriptor.setName(assignment.getName());
        descriptor.setDeadline(assignment.getDeadline());
        descriptor.setMaxMark(assignment.getMaxMark());
        descriptor.setWeight(assignment.getWeight());
    }

    /**
     * Sets the {@code Name} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withName(String name) {
        descriptor.setName(new AssignmentName(name));
        return this;
    }

    /**
     * Sets the {@code Deadline} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withDeadline(String deadline) {
        descriptor.setDeadline(new Deadline(deadline));
        return this;
    }

    /**
     * Sets the {@code MaxMark} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withMaxMark(String mark) {
        descriptor.setMaxMark(new Mark(mark));
        return this;
    }

    /**
     * Sets the {@code Weight} of the {@code EditAssignmentDescriptor} that we are building.
     */
    public EditAssignmentDescriptorBuilder withWeight(String address) {
        descriptor.setWeight(new Weight(address));
        return this;
    }

    public EditAssignmentDescriptor build() {
        return descriptor;
    }
}
