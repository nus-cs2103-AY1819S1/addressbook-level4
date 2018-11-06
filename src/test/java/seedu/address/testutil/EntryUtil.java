package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CCA_NAME_BASKETBALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.commands.CreateCcaCommand;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.model.cca.Cca;
import seedu.address.model.transaction.Entry;

//@@author ericyjw

/**
 * A utility class for Transaction {@code Entry}.
 */
public class EntryUtil {

    /**
     * Returns a add entry command string for creating the {@code entry}.
     */
    public static String getAddTransactionCommand(String cca, Entry entry) {
        return AddTransactionCommand.COMMAND_WORD + " " + PREFIX_TAG + VALID_CCA_NAME_BASKETBALL + " " + getEntryDetails(entry);
    }

    /**
     * Returns the part of command string for the given {@code entry}'s details.
     */
    public static String getEntryDetails(Entry entry) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_DATE + entry.getDateValue() + " ");
        sb.append(PREFIX_AMOUNT + String.valueOf(entry.getAmountValue()) + " ");
        sb.append(PREFIX_REMARKS + entry.getRemarkValue());
        return sb.toString();
    }
}
