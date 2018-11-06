package systemtests;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandModuleTestUtil.ACADEMICYEAR_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.ACADEMICYEAR_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_ACADEMICYEAR_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_MODULECODE_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_MODULETITLE_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_SEMESTER_FIVE;
import static seedu.address.logic.commands.CommandModuleTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULECODE_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULECODE_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULETITLE_DESC_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.MODULETITLE_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.SEMESTER_DESC_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.TAG_DESC_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.TAG_DESC_CALCULUS;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_ACADEMICYEAR_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULECODE_ST2131;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_MODULETITLE_CS2100;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_BINARY;
import static seedu.address.logic.commands.CommandModuleTestUtil.VALID_TAG_CALCULUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MODULES;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.address.testutil.TypicalModules.KEYWORD_MATCHING_MA1101R;
import static seedu.address.testutil.TypicalModules.ST2131;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.module.AcademicYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleTitle;
import seedu.address.model.module.Semester;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ModuleBuilder;

public class EditModuleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown -------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_MODULE;
        String command = " " + EditModuleCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + MODULECODE_DESC_ST2131 + "  " + MODULETITLE_DESC_ST2131 + " " + ACADEMICYEAR_DESC_ST2131
                + "  " + SEMESTER_DESC_ST2131 + " " + TAG_DESC_CALCULUS + " ";
        Module editedModule = new ModuleBuilder(ST2131).withTags(VALID_TAG_CALCULUS).build();
        assertCommandSuccess(command, index, editedModule);

        /* Case: undo editing the last module in the list -> last module restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last module in the list -> last module edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateModule(
                getModel().getFilteredModuleList().get(INDEX_FIRST_MODULE.getZeroBased()), editedModule);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a module with new values same as existing values -> edited */
        command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + MODULECODE_DESC_ST2131
                + MODULETITLE_DESC_ST2131 + ACADEMICYEAR_DESC_ST2131 + SEMESTER_DESC_ST2131 + TAG_DESC_CALCULUS;
        assertCommandSuccess(command, index, ST2131);

        /* Case: edit a module with new values same as another module's values but with different name -> edited */
        assertTrue(getModel().getAddressBook().getModuleList().contains(ST2131));
        index = INDEX_SECOND_MODULE;
        assertNotEquals(getModel().getFilteredModuleList().get(index.getZeroBased()), ST2131);
        command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + MODULECODE_DESC_CS2100
                + MODULETITLE_DESC_ST2131 + ACADEMICYEAR_DESC_ST2131 + SEMESTER_DESC_ST2131 + TAG_DESC_BINARY;
        editedModule = new ModuleBuilder(ST2131).withModuleCode(VALID_MODULECODE_CS2100)
                .withTags(VALID_TAG_BINARY).build();
        assertCommandSuccess(command, index, editedModule);

        /* Case: edit a module with new values same as another module's values but with different phone and email
         * -> edited
         */
        index = INDEX_SECOND_MODULE;
        command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + MODULECODE_DESC_ST2131
                + MODULETITLE_DESC_CS2100 + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_ST2131 + TAG_DESC_CALCULUS;
        editedModule = new ModuleBuilder(ST2131).withModuleTitle(VALID_MODULETITLE_CS2100)
                .withAcademicYear(VALID_ACADEMICYEAR_CS2100).withTags(VALID_TAG_CALCULUS).build();
        assertCommandSuccess(command, index, editedModule);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_MODULE;
        command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        Module moduleToEdit = getModel().getFilteredModuleList().get(index.getZeroBased());
        editedModule = new ModuleBuilder(moduleToEdit).withTags().build();
        assertCommandSuccess(command, index, editedModule);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------ */

        /* Case: filtered module list, edit index within bounds of address book and module list -> edited */
        showModulesWithTitle(KEYWORD_MATCHING_MA1101R);
        index = INDEX_FIRST_MODULE;
        assertTrue(index.getZeroBased() < getModel().getFilteredModuleList().size());
        command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + " " + MODULECODE_DESC_ST2131;
        moduleToEdit = getModel().getFilteredModuleList().get(index.getZeroBased());
        editedModule = new ModuleBuilder(moduleToEdit).withModuleCode(VALID_MODULECODE_ST2131).build();
        assertCommandSuccess(command, index, editedModule);

        /* Case: filtered module list, edit index within bounds of address book but out of bounds of module list
         * -> rejected
         */
        showModulesWithTitle(KEYWORD_MATCHING_MA1101R);
        int invalidIndex = getModel().getAddressBook().getModuleList().size();
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + invalidIndex + MODULECODE_DESC_ST2131,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a module card is selected --------------------- */

        /* Case: selects first card in the module list, edit a module -> edited, card selection
         * remains unchanged but browser url changes
         */
        // TODO: -> uncomment test back when select works.
        //showAllModules();
        //index = INDEX_FIRST_MODULE;
        //selectModule(index);
        //command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + MODULECODE_DESC_CS2100
        //        + MODULETITLE_DESC_CS2100 + ACADEMICYEAR_DESC_CS2100 + SEMESTER_DESC_CS2100 + TAG_DESC_BINARY;
        // this can be misleading: card selection actually remains unchanged but the
        // browser's url is updated to reflect the new module's name
        //assertCommandSuccess(command, index, CS2100, index);

        /* --------------------------------- Performing invalid edit operation -------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " 0" + MODULECODE_DESC_ST2131,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " -1" + MODULECODE_DESC_ST2131,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredModuleList().size() + 1;
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + invalidIndex + MODULECODE_DESC_ST2131,
                Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + MODULECODE_DESC_ST2131,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased(),
                EditModuleCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid name -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased()
                + INVALID_MODULECODE_DESC, ModuleCode.MESSAGE_MODULECODE_CONSTRAINTS);

        /* Case: invalid moduleTitle -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased()
                + INVALID_MODULETITLE_DESC, ModuleTitle.MESSAGE_MODULETITLE_CONSTRAINTS);

        /* Case: invalid academicYear -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased()
                + INVALID_ACADEMICYEAR_DESC, AcademicYear.MESSAGE_ACADEMICYEAR_CONSTRAINTS);

        /* Case: invalid semester -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased()
                + INVALID_SEMESTER_FIVE, Semester.MESSAGE_SEMESTER_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditModuleCommand.COMMAND_WORD + " " + INDEX_FIRST_MODULE.getOneBased()
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // TODO: -> uncomment test back when select works.
        /* Case: edit a module with new values same as another module's values -> rejected */
        //executeCommand(ModuleUtil.getAddCommand(ST2131));
        //assertTrue(getModel().getAddressBook().getModuleList().contains(ST2131));
        //index = INDEX_FIRST_MODULE;
        //assertFalse(getModel().getFilteredModuleList().get(index.getZeroBased()).equals(ST2131));
        //command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + MODULECODE_DESC_ST2131
        //        + MODULETITLE_DESC_ST2131 + ACADEMICYEAR_DESC_ST2131 + SEMESTER_DESC_ST2131 + TAG_DESC_BINARY
        //        + TAG_DESC_CALCULUS;
        //assertCommandFailure(command, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);

        // TODO: -> create new module other than ST2131 because not enough different modules used.
        /* Case: edit a module with new values same as another module's values but with different tags -> rejected */
        //command = EditModuleCommand.COMMAND_WORD + " " + index.getOneBased() + MODULECODE_DESC_ST2131
        //        + MODULETITLE_DESC_ST2131 + ACADEMICYEAR_DESC_ST2131 + SEMESTER_DESC_ST2131 + TAG_DESC_BINARY;
        //assertCommandFailure(command, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, Module, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditModuleCommandSystemTest#assertCommandSuccess(String, Index, Module, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Module editedModule) {
        assertCommandSuccess(command, toEdit, editedModule, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,
     * <br>
     * 1. Asserts that result display box displays the success message of executing {@code EditModuleCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the module at index {@code toEdit} being
     * updated to values specified {@code editedModule}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditModuleCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, Module editedModule,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        expectedModel.updateModule(expectedModel.getFilteredModuleList().get(toEdit.getZeroBased()), editedModule);
        expectedModel.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);


        assertCommandSuccess(command, expectedModel,
                String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditModuleCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpectedModule(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpectedModule(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedModuleCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        assertApplicationDisplaysExpectedModule("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedModuleCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 4. Asserts that the command box has the error style.<br>
     * Verifications 1 and 2 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpectedModule(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpectedModule(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpectedModule(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
