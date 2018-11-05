package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_WISHES;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.amount.Amount;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.WishDataUpdatedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wish.Wish;

/**
 * Saves specified amount to a specified wish.
 */
public class SaveCommand extends Command {
    public static final String COMMAND_WORD = "save";
    public static final String COMMAND_ALIAS = "sv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Channels saving amount to wish."
            + "Parameters: INDEX (must be a positive integer) "
            + "[SAVING_AMOUNT]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "108.50";

    public static final String MESSAGE_SAVE_SUCCESS = "Saved %1$s for wish %2$s%3$s.";
    public static final String MESSAGE_SAVE_UNUSED_FUNDS = "Saved $%1$s to Unused Funds. "
            + "Unused Funds now contains $%2$s.";
    public static final String MESSAGE_SAVE_EXCESS = " with $%1$s in excess. Unused Funds now contains $%2$s";
    public static final String MESSAGE_SAVE_DIFFERENCE = " with $%1$s left to completion";

    private final Index index;
    private final Amount amountToSave;
    private final boolean noWishSpecified;

    public SaveCommand(Index index, Amount amountToSave) {
        requireNonNull(index);
        requireNonNull(amountToSave);

        this.index = index;
        this.amountToSave = amountToSave;
        this.noWishSpecified = false;
    }

    /**
     * This constructor is for a {@Code SaveCommand} that directs the amount to unusedFunds.
     * @param amountToSave
     */
    public SaveCommand(Amount amountToSave) {
        requireNonNull(amountToSave);

        this.index = null;
        this.amountToSave = amountToSave;
        this.noWishSpecified = true;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Wish> lastShownList = model.getFilteredSortedWishList();

        if (this.noWishSpecified) {
            try {
                model.updateUnusedFunds(this.amountToSave);
                model.commitWishBook();
                return new CommandResult(String.format(MESSAGE_SAVE_UNUSED_FUNDS, amountToSave.toString(),
                        model.getUnusedFunds()));
            } catch (IllegalArgumentException iae) {
                throw new CommandException(iae.getMessage());
            }

        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WISH_DISPLAYED_INDEX);
        }

        Wish wishToEdit = lastShownList.get(index.getZeroBased());
        if (wishToEdit.isFulfilled()) {
            throw new CommandException(Messages.MESSAGE_WISH_FULFILLED);
        }

        String differenceString = "";

        try {
            Wish editedWish = Wish.createWishWithIncrementedSavedAmount(wishToEdit, amountToSave);

            Amount wishSavedDifference = editedWish.getSavedAmountToPriceDifference();
            if (wishSavedDifference.value > 0) {
                Amount amountToIncrement = wishToEdit.getSavedAmountToPriceDifference().getAbsoluteAmount();
                editedWish = Wish.createWishWithIncrementedSavedAmount(wishToEdit, amountToIncrement);
                model.updateUnusedFunds(wishSavedDifference.getAbsoluteAmount());
                differenceString = String.format(MESSAGE_SAVE_EXCESS, wishSavedDifference.getAbsoluteAmount(),
                        model.getWishBook().getUnusedFunds());
            } else {
                differenceString = String.format(MESSAGE_SAVE_DIFFERENCE, wishSavedDifference.getAbsoluteAmount());
            }

            model.updateWish(wishToEdit, editedWish);
            model.updateFilteredWishList(PREDICATE_SHOW_ALL_WISHES);
            model.commitWishBook();
            EventsCenter.getInstance().post(new WishDataUpdatedEvent(editedWish));
        } catch (IllegalArgumentException iae) {
            throw new CommandException(iae.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_SAVE_SUCCESS, amountToSave.toString(),
                index.getOneBased(), differenceString));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SaveCommand)) {
            return false;
        }

        SaveCommand saveCommand = (SaveCommand) other;

        if (this.noWishSpecified) {
            return (this.index == null)
                    && this.amountToSave.equals(saveCommand.amountToSave)
                    && (this.noWishSpecified == saveCommand.noWishSpecified);
        }

        return this.index.equals(saveCommand.index)
                && this.amountToSave.equals(saveCommand.amountToSave)
                && (this.noWishSpecified == saveCommand.noWishSpecified);
    }
}
