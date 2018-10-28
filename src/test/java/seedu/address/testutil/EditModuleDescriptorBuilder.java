package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class EditModuleDescriptorBuilder {

    private EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code Module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setModuleCode(module.getModuleCode());
        descriptor.setModuleTitle(module.getModuleTitle());
        descriptor.setAcademicYear(module.getAcademicYear());
        descriptor.setSemester(module.getSemester());
        descriptor.setTags(module.getTags());
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleCode(String name) {
        descriptor.setModuleCode(new ModuleCode(name));
        return this;
    }

    /**
     * Sets the {@code ModuleTitle} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withModuleTitle(String moduleTitle) {
        descriptor.setModuleTitle(new ModuleTitle(moduleTitle));
        return this;
    }

    /**
     * Sets the {@code AcademicYear} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withAcademicYear(String academicYear) {
        descriptor.setAcademicYear(new AcademicYear(academicYear));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(new Semester(semester));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditModuleDescriptor}
     * that we are building.
     */
    public EditModuleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditModuleDescriptor build() {
        return descriptor;
    }
}
