package seedu.address.model.module;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;

/**
 * Stores the details of a module to be added or edited. Each non-empty field value will replace the
 * corresponding field value of the module.
 */
public class ModuleDescriptor {
    // Identity fields
    private ModuleCode moduleCode;
    private ModuleTitle moduleTitle;
    private AcademicYear academicYear;
    private Semester semester;
    private UniquePersonList students;

    // Data fields
    private Set<Tag> tags;

    public ModuleDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public ModuleDescriptor(ModuleDescriptor toCopy) {
        setModuleCode(toCopy.moduleCode);
        setModuleTitle(toCopy.moduleTitle);
        setAcademicYear(toCopy.academicYear);
        setSemester(toCopy.semester);
        setTags(toCopy.tags);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(moduleCode, moduleTitle, academicYear, semester, tags);
    }

    public void setModuleCode(ModuleCode moduleCode) {
        this.moduleCode = moduleCode;
    }

    public Optional<ModuleCode> getModuleCode() {
        return Optional.ofNullable(moduleCode);
    }

    public void setModuleTitle(ModuleTitle moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public Optional<ModuleTitle> getModuleTitle() {
        return Optional.ofNullable(moduleTitle);
    }

    public void setAcademicYear(AcademicYear academicYear) {
        this.academicYear = academicYear;
    }

    public Optional<AcademicYear> getAcademicYear() {
        return Optional.ofNullable(academicYear);
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Optional<Semester> getSemester() {
        return Optional.ofNullable(semester);
    }

    public void setStudents(UniquePersonList students) {
        this.students = students;
    }

    public Optional<UniquePersonList> getStudents() {
        return Optional.ofNullable(students);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModuleDescriptor)) {
            return false;
        }

        // state check
        ModuleDescriptor e = (ModuleDescriptor) other;

        return getModuleCode().equals(e.getModuleCode())
                && getModuleTitle().equals(e.getModuleTitle())
                && getAcademicYear().equals(e.getAcademicYear())
                && getSemester().equals(e.getSemester())
                && getStudents().equals(e.getStudents())
                && getTags().equals(e.getTags());
    }
}


