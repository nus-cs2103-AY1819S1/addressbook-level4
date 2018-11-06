package seedu.address.testutil;

import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_SEMESTER_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_SEMESTER_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_CALCULUS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.module.Module;

/**
 * A utility class containing a list of {@code Module} objects to be used in tests.
 */
public class TypicalModules {
    // Manually added - Module's details found in {@code CommandModuleTestUtil}
    public static final Module CS2100 = new ModuleBuilder().withModuleCode(VALID_MODULECODE_CS2100)
            .withModuleTitle(VALID_MODULETITLE_CS2100).withAcademicYear(VALID_ACADEMICYEAR_CS2100)
            .withSemester(VALID_SEMESTER_CS2100).withTags(VALID_TAG_BINARY).build();
    public static final Module ST2131 = new ModuleBuilder().withModuleCode(VALID_MODULECODE_ST2131)
            .withModuleTitle(VALID_MODULETITLE_ST2131).withAcademicYear(VALID_ACADEMICYEAR_ST2131)
            .withSemester(VALID_SEMESTER_ST2131)
            .withTags(VALID_TAG_CALCULUS).build();
    public static final Module TYPICAL_MODULE_ONE = new ModuleBuilder().withModuleCode("CS1101S")
            .withModuleTitle("Programming Methodology I").withAcademicYear("1819")
            .withSemester("1").build();
    public static final Module TYPICAL_MODULE_TWO = new ModuleBuilder().withModuleCode("CS1231")
            .withModuleTitle("Discrete Structures").withAcademicYear("1819")
            .withSemester("1")
            .withTags("proving").build();
    public static final Module TYPICAL_MODULE_THREE = new ModuleBuilder().withModuleCode("CS2103T")
            .withModuleTitle("Software Engineering").withAcademicYear("1819")
            .withSemester("1")
            .withTags("project").build();
    public static final Module TYPICAL_MODULE_FOUR = new ModuleBuilder().withModuleCode("CS3233")
            .withModuleTitle("Competitive Programming").withAcademicYear("1920")
            .withSemester("2")
            .withTags("difficult").build();
    public static final Module TYPICAL_MODULE_FIVE = new ModuleBuilder().withModuleCode("MA1101R")
            .withModuleTitle("Linear Algebra I").withAcademicYear("1718")
            .withSemester("1")
            .withTags("matrices").build();
    public static final Module TYPICAL_MODULE_SIX = new ModuleBuilder().withModuleCode("GER1000")
            .withModuleTitle("Quantitative Reasoning").withAcademicYear("1516")
            .withSemester("2")
            .withTags("Statistics").build();

    public static final String KEYWORD_MATCHING_MA1101R = "MA1101R"; // A keyword that matches MA1101R

    private TypicalModules() {} // prevents instantiation

    public static void addTypicalModules(AddressBook ab) {
        for (Module module : getTypicalModules()) {
            ab.addModule(module);
        }
    }

    /**
     * Returns an {@code AddressBook} with all the typical modules.
     */
    public static AddressBook getTypicalModulesAddressBook() {
        AddressBook ab = new AddressBook();
        addTypicalModules(ab);
        return ab;
    }

    public static List<Module> getTypicalModules() {
        return new ArrayList<>(Arrays.asList(TYPICAL_MODULE_ONE, TYPICAL_MODULE_TWO,
                TYPICAL_MODULE_THREE, TYPICAL_MODULE_FOUR, TYPICAL_MODULE_FIVE, TYPICAL_MODULE_SIX));
    }
}
