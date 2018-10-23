package seedu.address.logic.commands.edit;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ACADEMICYEAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULETITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

/**
 * Edits the details of an existing module in the address book.
 *
 * @author alistair
 */
public class EditModuleCommand {

    public static final String COMMAND_WORD = "editModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the module identified "
            + "by the module code used in the displayed module list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODULECODE + "MODULE CODE] "
            + "[" + PREFIX_MODULETITLE + "MODULE TITLE] "
            + "[" + PREFIX_ACADEMICYEAR + "ACADEMIC YEAR] "
            + "[" + PREFIX_SEMESTER + "SEMESTER] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_MODULECODE + "CS1101S "
            + PREFIX_EMAIL + "johndoe@example.com";
}
