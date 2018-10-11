package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.deliveryman.DeliverymanAddCommand;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * A utility class for Deliveryman testing.
 */
public class DeliverymanUtil {

    /**
     * Returns an add command string for adding the {@code deliveryman}.
     */
    public static String getDeliverymanAddCommand(Deliveryman deliveryman) {
        return DeliverymanAddCommand.COMMAND_WORD + " " + getDeliverymanDetails(deliveryman);
    }

    /**
     * Returns the part of the command string for the given {@code deliveryman}'s details.
     */
    public static String getDeliverymanDetails(Deliveryman deliveryman) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + deliveryman.getName().fullName + " ");
        return sb.toString();
    }
}
