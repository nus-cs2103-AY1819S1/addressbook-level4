package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_CCA;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.DeleteCcaCommand.MESSAGE_DELETE_CCA_SUCCESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TestUtil.getCca;
import static seedu.address.testutil.TypicalCcas.SOFTBALL;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.DeleteCcaCommand;
import seedu.address.model.Model;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;

//@@author ericyjw
public class DeleteCcaCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void delete_cca() {
        /* Case: delete the first cca in the list, command with leading spaces and trailing spaces -> deleted */
        Cca toBeDeleted = getModel().getFilteredCcaList().get(0);
        Model expectedModel = getModel();
        String command =
            "     " + DeleteCcaCommand.COMMAND_WORD + "      " + PREFIX_TAG + toBeDeleted.getCcaName() + "   ";
        Cca deletedCca = removeCca(expectedModel, toBeDeleted.getName());
        String expectedResultMessage = String.format(MESSAGE_DELETE_CCA_SUCCESS, deletedCca);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Undo delete */
        addCca(toBeDeleted);

        /* Case: invalid cca -> rejected */
        try {
            command =
                "     " + DeleteCcaCommand.COMMAND_WORD + "      " + PREFIX_TAG + SOFTBALL.getCcaName() + "M   ";
            assertCommandFailure(command, MESSAGE_INVALID_CCA);
        } catch (IndexOutOfBoundsException e) {
            assertCommandFailure(command, MESSAGE_INVALID_CCA);
        }

        /* Case: No cca specified -> rejected */
        command = "     " + DeleteCcaCommand.COMMAND_WORD + "      ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCcaCommand.MESSAGE_USAGE));

    }

    /**
     * Removes the {@code Person} at the specified {@code index} in {@code model}'s address book.
     *
     * @return the removed person
     */
    private Cca removeCca(Model model, CcaName ccaName) {
        List<Cca> lastShownList = model.getFilteredCcaList();

        int index = 0;
        for (Cca c : lastShownList) {
            if (c.getCcaName().equals(ccaName.getNameOfCca())) {
                break;
            }
            index++;
        }
        Cca targetCca = getCca(model, index);
        model.deleteCca(targetCca);
        return targetCca;
    }

    /**
     * Assert success when the command of the correct format is entered to delete a CCA.
     *
     * @param command a valid command
     * @param expectedModel the expected model after the cca is deleted
     * @param expectedResultMessage the expected message displayed after the cca is deleted
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Assert failure when the command enter is incorrect.
     * @param command the invalid command to delete a cca
     * @param expectedResultMessage the expected message when the command fail
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
    }
}
