package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.TypeUtil;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;


/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULECODE = "CS2103";
    public static final String DEFAULT_MODULETITLE = "SOFTWARE ENGINEERIG";
    public static final String DEFAULT_ACADEMICYEAR = "1718";
    public static final String DEFAULT_SEMESTER = "1";

    private ModuleCode moduleCode;
    private ModuleTitle moduleTitle;
    private AcademicYear academicYear;
    private Semester semester;
    private UniquePersonList students;
    private Set<Tag> tags;

    public ModuleBuilder() {
        moduleCode = new ModuleCode(DEFAULT_MODULECODE);
        moduleTitle = new ModuleTitle(DEFAULT_MODULETITLE);
        academicYear = new AcademicYear(DEFAULT_ACADEMICYEAR);
        semester = new Semester(DEFAULT_SEMESTER);
        students = new UniquePersonList();
        tags = new HashSet<>();
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code personToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        moduleCode = moduleToCopy.getModuleCode();
        moduleTitle = moduleToCopy.getModuleTitle();
        academicYear = moduleToCopy.getAcademicYear();
        semester = moduleToCopy.getSemester();
        students = new UniquePersonList();
        tags = moduleToCopy.getTags();
    }

    /**
     * Sets the {@code moduleCode} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleCode(String moduleCode) {
        this.moduleCode = new ModuleCode(moduleCode);
        return this;
    }

    /**
     * Sets the {@code moduleTitle} of the {@code Module} that we are building.
     */
    public ModuleBuilder withModuleTitle(String moduleTitle) {
        this.moduleTitle = new ModuleTitle(moduleTitle);
        return this;
    }

    /**
     * Sets the {@code academicYear} of the {@code Module} that we are building.
     */
    public ModuleBuilder withAcademicYear(String academicYear) {
        this.academicYear = new AcademicYear(academicYear);
        return this;
    }

    /**
     * Sets the {@code semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withSemester(String semester) {
        this.semester = new Semester(semester);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ModuleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    public Module build() {
        return new Module(moduleCode, moduleTitle, academicYear, semester, students, tags, TypeUtil.MODULE);
    }

}
