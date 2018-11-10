package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_MODULECODE = "CS2103";
    public static final String DEFAULT_MODULETITLE = "SOFTWARE ENGINEERING";
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
        students = new UniquePersonList(new ArrayList<>());
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
        students = moduleToCopy.getStudents();
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
     * Sets the {@code students list} of the {@code Module} that we are building.
     */
    public ModuleBuilder withStudents(UniquePersonList students) {
        this.students = students;
        return this;
    }

    /**
     * Sets the {@code students list} of the {@code Module} that we are building
     * via another list.
     */
    public ModuleBuilder withStudents(List<Person> studentList) {
        for (Person person : studentList) {
            students.add(person);
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public ModuleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Module} as having no {@code moduleTitle}.
     */
    public ModuleBuilder withoutModuleTitle() {
        this.moduleTitle = new ModuleTitle();
        return this;
    }

    /**
     * Sets the {@code Module} as having no {@code academicYear} .
     */
    public ModuleBuilder withoutAcademicYear() {
        this.academicYear = new AcademicYear();
        return this;
    }

    /**
     * Sets the {@code semester} of the {@code Module} that we are building.
     */
    public ModuleBuilder withoutSemester() {
        this.semester = new Semester();
        return this;
    }

    /**
     * Sets the {@code Module} as having no {@code students list} .
     */
    public ModuleBuilder withoutStudents() {
        this.students = new UniquePersonList();
        return this;
    }

    /**
     * Builds a new module.
     * @return module which has been built.
     */
    public Module build() {
        return new Module(moduleCode, moduleTitle, academicYear, semester,
                students, tags);
    }

}
