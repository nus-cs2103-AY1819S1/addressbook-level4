package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.*;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagKey;
import seedu.address.model.tag.TagMap;
import seedu.address.model.tag.TagValue;

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

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    // TODO change all places where getTags is used.
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public boolean isSameModule(Module other) {
        return this.equals(other);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
                && otherModule.getSemester().equals(getSemester())
                && otherModule.getStudents().equals(getStudents());
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
