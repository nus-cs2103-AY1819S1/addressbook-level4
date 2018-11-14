package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class ModuleDescriptorBuilder {

    private ModuleDescriptor descriptor;

    public ModuleDescriptorBuilder() {
        descriptor = new ModuleDescriptor();
    }

    public ModuleDescriptorBuilder(ModuleDescriptor descriptor) {
        this.descriptor = new ModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code Module}'s details
     */
    public ModuleDescriptorBuilder(Module module) {
        descriptor = new ModuleDescriptor();
        descriptor.setModuleCode(module.getModuleCode());
        descriptor.setModuleTitle(module.getModuleTitle());
        descriptor.setAcademicYear(module.getAcademicYear());
        descriptor.setSemester(module.getSemester());
        descriptor.setTags(module.getTags());
    }

    /**
     * Sets the {@code ModuleCode} of the {@code EditModuleDescriptor} that we are building.
     */
    public ModuleDescriptorBuilder withModuleCode(String name) {
        descriptor.setModuleCode(new ModuleCode(name));
        return this;
    }

    /**
     * Sets the {@code ModuleTitle} of the {@code EditModuleDescriptor} that we are building.
     */
    public ModuleDescriptorBuilder withModuleTitle(String moduleTitle) {
        descriptor.setModuleTitle(new ModuleTitle(moduleTitle));
        return this;
    }

    /**
     * Sets the {@code AcademicYear} of the {@code EditModuleDescriptor} that we are building.
     */
    public ModuleDescriptorBuilder withAcademicYear(String academicYear) {
        descriptor.setAcademicYear(new AcademicYear(academicYear));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleDescriptor} that we are building.
     */
    public ModuleDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(new Semester(semester));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditModuleDescriptor}
     * that we are building.
     */
    public ModuleDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public ModuleDescriptor build() {
        return descriptor;
    }
}
