package seedu.address.testutil;

import seedu.address.logic.commands.EditLessonCommand.EditLessonDescriptor;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Session;
import seedu.address.model.attendance.SessionDate;

/**
 * A utility class to help with building EditLessonDescriptor objects.
 */
public class EditLessonDescriptorBuilder {

    private EditLessonDescriptor descriptor;

    public EditLessonDescriptorBuilder() {
        descriptor = new EditLessonDescriptor();
    }

    public EditLessonDescriptorBuilder(EditLessonDescriptor descriptor) {
        this.descriptor = new EditLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditLessonDescriptor} with fields containing {@code assignment}'s details
     */
    public EditLessonDescriptorBuilder(Attendance attendance) {
        descriptor = new EditLessonDescriptor();
        descriptor.setName(attendance.getSession());
        descriptor.setDate(attendance.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withSession(String name) {
        descriptor.setName(new Session(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditLessonDescriptor} that we are building.
     */
    public EditLessonDescriptorBuilder withSessionDate(String date) {
        descriptor.setDate(new SessionDate(date));
        return this;
    }

    public EditLessonDescriptor build() {
        return descriptor;
    }
}

