package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VICE_HEAD;

import seedu.address.logic.commands.CreateCcaCommand;
import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.model.cca.Cca;

//@@author ericyjw

/**
 * A utility class for Cca.
 */
public class CcaUtil {

    /**
     * Returns a create cca command string for creating the {@code cca}.
     */
    public static String getCreateCcaCommand(Cca cca) {
        return CreateCcaCommand.COMMAND_WORD + " " + getCcaDetails(cca);
    }

    /**
     * Returns the part of command string for the given {@code cca}'s details.
     */
    public static String getCcaDetails(Cca cca) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + cca.getCcaName() + " ");
        sb.append(PREFIX_BUDGET + String.valueOf(cca.getBudgetAmount()) + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCcaDescriptor}'s details.
     */
    public static String getEditCcaDescriptorDetails(EditCcaDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getCcaName().ifPresent(ccaName -> sb.append(PREFIX_NAME).append(ccaName.getNameOfCca()).append(" "));
        descriptor.getHead().ifPresent(head -> sb.append(PREFIX_HEAD).append(head.fullName).append(" "));
        descriptor.getViceHead().ifPresent(viceHead -> sb.append(PREFIX_VICE_HEAD).append(viceHead.fullName).append(
            " "));
        descriptor.getBudget().ifPresent(budget -> sb.append(PREFIX_BUDGET).append(budget.getBudgetValue()).append(" "
        ));
        descriptor.getEntryNum().ifPresent(num -> sb.append(PREFIX_TRANSACTION).append(num).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date).append(" "));
        descriptor.getAmount().ifPresent(amount -> sb.append(PREFIX_AMOUNT).append(amount).append(" "));
        descriptor.getRemarks().ifPresent(remarks -> sb.append(PREFIX_REMARKS).append(remarks).append(" "));
        return sb.toString();
    }
}
