package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.module.Module;


/**
 * Contains helper methods for testing commands.
 */
public class CommandModuleTestUtil extends CommandTestUtil {

    public static final String VALID_MODULECODE_CS2100 = "CS2100";
    public static final String VALID_MODULECODE_ST2131 = "ST2131";
    public static final String VALID_MODULETITLE_CS2100 = "Computer Organisation";
    public static final String VALID_MODULETITLE_ST2131 = "Probability";
    public static final String VALID_ACADEMICYEAR_CS2100 = "1617";
    public static final String VALID_ACADEMICYEAR_ST2131 = "1011";
    public static final String VALID_TAG_BINARY = "binary";
    public static final String VALID_TAG_CALCULUS = "needs calculus";

    public static final String MODULECODE_DESC_CS2100 = " " + PREFIX_MODULECODE + VALID_MODULECODE_CS2100;
    public static final String MODULECODE_DESC_ST2131 = " " + PREFIX_MODULECODE + VALID_MODULECODE_ST2131;
    public static final String MODULETITLE_DESC_CS2100 = " " + PREFIX_MODULETITLE + VALID_MODULETITLE_CS2100;
    public static final String MODULETITLE_DESC_ST2131 = " " + PREFIX_MODULETITLE + VALID_MODULETITLE_ST2131;
    public static final String ACADEMICYEAR_DESC_CS2100 = " " + PREFIX_ACADEMICYEAR + VALID_ACADEMICYEAR_CS2100;
    public static final String ACADEMICYEAR_DESC_ST2131 = " " + PREFIX_ACADEMICYEAR + VALID_ACADEMICYEAR_ST2131;
    public static final String TAG_DESC_BINARY = " " + PREFIX_TAG + VALID_TAG_BINARY;
    public static final String TAG_DESC_CALCULUS = " " + PREFIX_TAG + VALID_TAG_CALCULUS;

    public static final String INVALID_MODULECODE_DESC = " " + PREFIX_MODULECODE
            + "CS12345"; // only 4 numbers in code
    public static final String INVALID_MODULETITLE_DESC = " " + PREFIX_MODULETITLE
            + "OOP & FP"; // '&' not allowed in title
    public static final String INVALID_ACADEMICYEAR_DESC = " " + PREFIX_EMAIL
            + "1234"; // years not consecutive
    public static final String INVALID_SEMESTER_FIVE = "5";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + ":P"; // ':' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredModuleList().size());

        Module module = model.getFilteredModuleList().get(targetIndex.getZeroBased());
        model.updateFilteredModuleList(module1 -> (module1.getModuleCode().fullModuleCode.equals(
                module.getModuleCode().fullModuleCode)));

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
