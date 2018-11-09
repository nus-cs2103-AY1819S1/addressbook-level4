package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Module within the address book.
 * @author waytan
 */
public class Module {

    // Identity fields
    private final ModuleCode moduleCode;
    private final ModuleTitle moduleTitle;
    private final AcademicYear academicYear;
    private final Semester semester;
    private final UniquePersonList students;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();
    /**
     * Every field must be present and not null.
     */
    public Module(ModuleCode moduleCode, ModuleTitle moduleTitle, AcademicYear academicYear,
                  Semester semester, UniquePersonList students, Set<Tag> tags) {
        requireAllNonNull(moduleCode, moduleTitle, academicYear, semester, tags);
        this.moduleCode = moduleCode;
        this.moduleTitle = moduleTitle;
        this.academicYear = academicYear;
        this.semester = semester;
        this.students = students;
        this.tags.addAll(tags);
    }

    /**
     * Create a module from a moduleDescriptor
     * @param moduleDescriptor
     */
    public Module(ModuleDescriptor moduleDescriptor) {
        requireNonNull(moduleDescriptor);
        this.moduleCode = moduleDescriptor.getModuleCode().orElse(new ModuleCode());
        this.moduleTitle = moduleDescriptor.getModuleTitle().orElse(new ModuleTitle());
        this.academicYear = moduleDescriptor.getAcademicYear().orElse(new AcademicYear());
        this.semester = moduleDescriptor.getSemester().orElse(new Semester());
        this.students = moduleDescriptor.getStudents().orElse(new UniquePersonList());
        this.tags.addAll(moduleDescriptor.getTags().orElse(new HashSet<Tag>()));
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code moduleToEdit}
     * edited with {@code editModuleDescriptor}.
     */
    public static Module createEditedModule(Module moduleToEdit, ModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleCode updatedModuleCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        ModuleTitle updatedModuleTitle = editModuleDescriptor.getModuleTitle().orElse(moduleToEdit.getModuleTitle());
        AcademicYear updatedAcademicYear =
                editModuleDescriptor.getAcademicYear().orElse(moduleToEdit.getAcademicYear());
        Semester updatedSemester = editModuleDescriptor.getSemester().orElse(moduleToEdit.getSemester());
        UniquePersonList updatedStudents = editModuleDescriptor.getStudents().orElse(moduleToEdit.getStudents());
        Set<Tag> updatedTags = editModuleDescriptor.getTags().orElse(moduleToEdit.getTags());

        return new Module(updatedModuleCode, updatedModuleTitle, updatedAcademicYear, updatedSemester,
                updatedStudents, updatedTags);
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public ModuleTitle getModuleTitle() {
        return moduleTitle;
    }

    public AcademicYear getAcademicYear() {
        return academicYear;
    }

    public Semester getSemester() {
        return semester;
    }

    public UniquePersonList getStudents() {
        return students == null ? new UniquePersonList(new ArrayList<>()) : students;
    }

    public Property moduleCodeProperty() {
        return new SimpleStringProperty(moduleCode.fullModuleCode);
    }

    public Property moduleTitleProperty() {
        return new SimpleStringProperty(moduleTitle.fullModuleTitle);
    }

    /**
     * Makes an identical deep copy of this module.
     */
    public Module makeDeepDuplicate() {
        ModuleCode newCode = this.moduleCode.makeDeepDuplicate();
        ModuleTitle newTitle = this.moduleTitle.makeDeepDuplicate();
        AcademicYear newYear = this.academicYear.makeDeepDuplicate();
        Semester newSem = this.semester.makeDeepDuplicate();
        UniquePersonList newList = this.students.makeDeepDuplicate();
        Set<Tag> newTag = this.tags.stream().map(value -> value.makeDeepDuplicate()).collect(Collectors.toSet());
        return new Module(newCode, newTitle, newYear, newSem, newList, newTag);
    }

    /**
     * Makes an identical copy of this module with an empty person list.
     */
    public Module makeShallowDuplicate() {
        ModuleCode newCode = this.moduleCode.makeDeepDuplicate();
        ModuleTitle newTitle = this.moduleTitle.makeDeepDuplicate();
        AcademicYear newYear = this.academicYear.makeDeepDuplicate();
        Semester newSem = this.semester.makeDeepDuplicate();
        UniquePersonList newList = new UniquePersonList();
        Set<Tag> newTag = this.tags.stream().map(value -> value.makeDeepDuplicate()).collect(Collectors.toSet());
        return new Module(newCode, newTitle, newYear, newSem, newList, newTag);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both modules have the same code, academic year and semester.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getAcademicYear().equals(getAcademicYear())
                && otherModule.getSemester().equals(getSemester());
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getModuleTitle().equals(getModuleTitle())
                && otherModule.getAcademicYear().equals(getAcademicYear())
                && otherModule.getSemester().equals(getSemester());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(moduleCode, moduleTitle, academicYear, semester, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getModuleCode())
                .append(" ")
                .append(getModuleTitle())
                .append(" ")
                .append(getAcademicYear())
                .append(" ")
                .append(getSemester())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
