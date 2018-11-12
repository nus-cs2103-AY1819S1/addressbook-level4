package seedu.address.logic.commands.deliveryman;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToDeliveryManListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * The Command that selects a deliveryman identified using its displayed index
 */
public class DeliverymanSelectCommand extends DeliverymanCommand {
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = DeliverymanCommand.COMMAND_WORD + " " + COMMAND_WORD
        + ": Selects the deliveryman identified by the index number used in the displayed deliveryman list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + DeliverymanCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_ORDER_SUCCESS = "Selected Deliveryman: %1$s";

    private final Index targetIndex;

    public DeliverymanSelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Deliveryman> filteredDeliverymanList = model.getFilteredDeliverymenList();

        if (targetIndex.getZeroBased() >= filteredDeliverymanList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DELIVERYMAN_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToDeliveryManListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_ORDER_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeliverymanSelectCommand // instanceof handles nulls
            && targetIndex.equals(((DeliverymanSelectCommand) other).targetIndex)); // state check
    }
}
