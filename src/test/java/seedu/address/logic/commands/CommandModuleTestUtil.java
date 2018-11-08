package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULEINDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleDescriptor;
import seedu.address.testutil.ModuleDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandModuleTestUtil {

    public static final String VALID_MODULECODE_CS2100 = "CS2100";
    public static final String VALID_MODULECODE_ST2131 = "ST2131";
    public static final String VALID_MODULETITLE_CS2100 = "Computer Organisation";
    public static final String VALID_MODULETITLE_ST2131 = "Probability";
    public static final String VALID_ACADEMICYEAR_CS2100 = "1617";
    public static final String VALID_ACADEMICYEAR_ST2131 = "1011";
    public static final String VALID_SEMESTER_CS2100 = "1";
    public static final String VALID_SEMESTER_ST2131 = "2";
    public static final String VALID_TAG_BINARY = "binary";
    public static final String VALID_TAG_CALCULUS = "calculus";
    public static final String VALID_INDEX = "1";

    public static final String MODULECODE_DESC_CS2100 = " " + PREFIX_MODULECODE + VALID_MODULECODE_CS2100;
    public static final String MODULECODE_DESC_ST2131 = " " + PREFIX_MODULECODE + VALID_MODULECODE_ST2131;
    public static final String MODULETITLE_DESC_CS2100 = " " + PREFIX_MODULETITLE + VALID_MODULETITLE_CS2100;
    public static final String MODULETITLE_DESC_ST2131 = " " + PREFIX_MODULETITLE + VALID_MODULETITLE_ST2131;
    public static final String ACADEMICYEAR_DESC_CS2100 = " " + PREFIX_ACADEMICYEAR + VALID_ACADEMICYEAR_CS2100;
    public static final String ACADEMICYEAR_DESC_ST2131 = " " + PREFIX_ACADEMICYEAR + VALID_ACADEMICYEAR_ST2131;
    public static final String SEMESTER_DESC_CS2100 = " " + PREFIX_SEMESTER + VALID_SEMESTER_CS2100;
    public static final String SEMESTER_DESC_ST2131 = " " + PREFIX_SEMESTER + VALID_SEMESTER_ST2131;
    public static final String TAG_DESC_BINARY = " " + PREFIX_TAG + VALID_TAG_BINARY;
    public static final String TAG_DESC_CALCULUS = " " + PREFIX_TAG + VALID_TAG_CALCULUS;
    public static final String VALID_INDEX_INSERT_MODULE = " " + PREFIX_MODULEINDEX + VALID_INDEX;

    public static final String INVALID_MODULECODE_DESC = " " + PREFIX_MODULECODE
            + "CS12345"; // only 4 numbers in code
    public static final String INVALID_MODULETITLE_DESC = " " + PREFIX_MODULETITLE
            + "OOP & FP"; // '&' not allowed in title
    public static final String INVALID_ACADEMICYEAR_DESC = " " + PREFIX_ACADEMICYEAR
            + "1234"; // years not consecutive
    public static final String INVALID_SEMESTER_FIVE = " " + PREFIX_SEMESTER + "5";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + ":P"; // ':' not allowed in tags
    public static final String INVALID_INDEX_INSERT_MODULE = " " + PREFIX_MODULEINDEX + "-1";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final ModuleDescriptor DESC_CS2100;
    public static final ModuleDescriptor DESC_ST2131;

    static {
        DESC_CS2100 = new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_CS2100)
                .withModuleCode(VALID_MODULECODE_CS2100).withAcademicYear(VALID_ACADEMICYEAR_CS2100)
                .withSemester(VALID_SEMESTER_CS2100).withTags(VALID_TAG_BINARY).build();
        DESC_ST2131 = new ModuleDescriptorBuilder().withModuleTitle(VALID_MODULETITLE_ST2131)
                .withModuleCode(VALID_MODULECODE_ST2131).withAcademicYear(VALID_ACADEMICYEAR_ST2131)
                .withSemester(VALID_SEMESTER_ST2131).withTags(VALID_TAG_CALCULUS).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Module> expectedFilteredList = new ArrayList<>(actualModel.getFilteredModuleList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredModuleList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        model.updateFilteredModuleList(module1 -> (module1.isSameModule(module)));

        assertEquals(1, model.getFilteredModuleList().size());
    }

    /**
     * Deletes the first module in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstModule(Model model) {
        Module firstModule = model.getFilteredModuleList().get(0);
        model.deleteModule(firstModule);
        model.commitAddressBook();
    }
}
