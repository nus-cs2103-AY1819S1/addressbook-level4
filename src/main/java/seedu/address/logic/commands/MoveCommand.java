package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
 * Moves a specified amount from one wish to another.
 */
public class MoveCommand extends Command {
    public static final String COMMAND_WORD = "move";
    public static final String COMMAND_ALIAS = "mv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Moves amount from [FROM_INDEX] to [TO_INDEX] "
            + "Parameters: [FROM_INDEX] (must be a positive integer) "
            + "[TO_INDEX] (must be a positive integer) "
            + "[SAVING_AMOUNT] (must be positive)\n"
            + "Example: " + COMMAND_WORD + " 1 " + " 2 "
            + "108.50";

    public static final String MESSAGE_MOVE_WISH_SUCCESS = "Moved %1$s from wish %2$s to wish %3$s.";
    public static final String MESSAGE_MOVE_FROM_UNUSED_FUNDS_SUCCESS = "Moved %1$s from unused funds to wish %2$s. "
            + "Unused Funds now contains $%3$s.";
    public static final String MESSAGE_MOVE_FROM_UNUSED_FUNDS_EXCESS = "Moved %1$s from unused funds to wish %2$s, with"
            + " %3$s in excess. Unused Funds now contains $%4$s.";
    public static final String MESSAGE_MOVE_TO_UNUSED_FUNDS_SUCCESS = "Moved %1$s from wish %2$s to unused funds. "
            + "Unused Funds now contains $%3$s.";
    public static final String MESSAGE_MOVE_EXCESS_TO_WISH = "Moved %1$s from wish %2$s to wish %3$s,"
            + " with %4$s in excess. Unused Funds now contains $%5$s.";
    public static final String MOVE_INVALID_SAME_INDEX = "Cannot move from and to the same index.";
    public static final String MOVE_INVALID_AMOUNT = "Amount to move must be greater than zero.";

    /**
     * Represents the types of move commands.
     */
    public enum MoveType { WISH_FROM_UNUSED_FUNDS, WISH_TO_UNUSED_FUNDS, WISH_TO_WISH }

    private final Index fromIndex;
    private final Index toIndex;
    private final Amount amountToMove;
    private final MoveType moveType;

    /**
     * COnstructor for moving {@code amountToMove} from wish at {@code fromIndex} to {@code toIndex}
     * @param fromIndex wish index that funds is being moved from.
     * @param toIndex wish index that funds is being moved to.
     * @param amountToMove amount to transfer between the two wishes.
     * @param moveType Type of move command.
     */
    public MoveCommand(Index fromIndex, Index toIndex, Amount amountToMove, MoveType moveType) {
        requireNonNull(amountToMove);
        requireNonNull(moveType);

        this.amountToMove = amountToMove;
        this.moveType = moveType;

        if (this.moveType.equals(MoveType.WISH_FROM_UNUSED_FUNDS)) {
            requireNonNull(toIndex);
            this.fromIndex = null;
            this.toIndex = toIndex;
        } else if (this.moveType.equals(MoveType.WISH_TO_UNUSED_FUNDS)) {
            requireNonNull(fromIndex);
            this.fromIndex = fromIndex;
            this.toIndex = null;
        } else {
            requireNonNull(fromIndex);
            requireNonNull(toIndex);
            this.fromIndex = fromIndex;
            this.toIndex = toIndex;
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        List<Wish> lastShownList = model.getFilteredSortedWishList();

        /* Amount must be greater than 0. */
        if (this.amountToMove.value <= 0.0) {
            throw new CommandException(MOVE_INVALID_AMOUNT);
        }

        CommandResult commandResult;

        /* Transfer from unused funds to wish. */
        if (this.moveType.equals(MoveType.WISH_FROM_UNUSED_FUNDS)) {
            checkValidIndex(lastShownList, this.toIndex);
            Wish toWish = lastShownList.get(toIndex.getZeroBased());
            checkWishFulfilled(toWish);

            try {
                Wish editedToWish = new Wish(toWish.getName(), toWish.getPrice(), toWish.getDate(),
                        toWish.getUrl(), toWish.getSavedAmount().incrementSavedAmount(amountToMove),
                        toWish.getRemark(), toWish.getTags(), toWish.getId());

                Amount wishSavedDifference = editedToWish.getSavedAmountToPriceDifference();

                model.updateUnusedFunds(amountToMove.getNegatedAmount());
                /* Transfer excess amount from unused funds. */
                if (wishSavedDifference.value > 0) {
                    editedToWish = new Wish(toWish.getName(), toWish.getPrice(), toWish.getDate(),
                            toWish.getUrl(), toWish.getSavedAmount()
                            .incrementSavedAmount(toWish.getSavedAmountToPriceDifference().getAbsoluteAmount()),
                            toWish.getRemark(), toWish.getTags(), toWish.getId());
                    commandResult = new CommandResult(String.format(MESSAGE_MOVE_FROM_UNUSED_FUNDS_EXCESS,
                            toWish.getSavedAmountToPriceDifference().toString(), this.toIndex,
                            wishSavedDifference.getAbsoluteAmount(), model.getUnusedFunds()));
                    model.updateUnusedFunds(wishSavedDifference.getAbsoluteAmount());
                } else {
                    commandResult = new CommandResult(String.format(MESSAGE_MOVE_FROM_UNUSED_FUNDS_SUCCESS,
                            amountToMove.toString(), this.toIndex.getOneBased(), model.getUnusedFunds()));
                }
                model.updateWish(toWish, editedToWish);
                model.commitWishBook();
                EventsCenter.getInstance().post(new WishDataUpdatedEvent(editedToWish));
            } catch (IllegalArgumentException iae) {
                throw new CommandException(iae.getMessage());
            }
        } else if (this.moveType.equals(MoveType.WISH_TO_UNUSED_FUNDS)) {
            /* Transfer from wish to unused funds. */
            checkValidIndex(lastShownList, this.fromIndex);
            Wish fromWish = lastShownList.get(fromIndex.getZeroBased());
            checkWishFulfilled(fromWish);

            try {
                Wish editedFromWish = new Wish(fromWish.getName(), fromWish.getPrice(), fromWish.getDate(),
                        fromWish.getUrl(),
                        fromWish.getSavedAmount().incrementSavedAmount(amountToMove.getNegatedAmount()),
                        fromWish.getRemark(), fromWish.getTags(), fromWish.getId());

                model.updateUnusedFunds(amountToMove);

                model.updateWish(fromWish, editedFromWish);
                model.commitWishBook();
                EventsCenter.getInstance().post(new WishDataUpdatedEvent(editedFromWish));
                commandResult = new CommandResult(String.format(MESSAGE_MOVE_TO_UNUSED_FUNDS_SUCCESS,
                        this.amountToMove.toString(), this.fromIndex.getOneBased(), model.getUnusedFunds()));
            } catch (IllegalArgumentException iae) {
                throw new CommandException(iae.getMessage());
            }
        } else {
            /* Transfer from wish to wish. */
            /* Cannot transfer from and to same index. */
            if (this.fromIndex.equals(this.toIndex)) {
                throw new CommandException(MOVE_INVALID_SAME_INDEX);
            }

            checkValidIndex(lastShownList, this.toIndex);
            checkValidIndex(lastShownList, this.fromIndex);

            Wish fromWish = lastShownList.get(fromIndex.getZeroBased());
            Wish toWish = lastShownList.get(toIndex.getZeroBased());

            checkWishFulfilled(fromWish);
            checkWishFulfilled(toWish);

            try {
                Wish editedFromWish = new Wish(fromWish.getName(), fromWish.getPrice(), fromWish.getDate(),
                        fromWish.getUrl(),
                        fromWish.getSavedAmount().incrementSavedAmount(amountToMove.getNegatedAmount()),
                        fromWish.getRemark(), fromWish.getTags(), fromWish.getId());
                Wish editedToWish = new Wish(toWish.getName(), toWish.getPrice(), toWish.getDate(),
                        toWish.getUrl(), toWish.getSavedAmount().incrementSavedAmount(amountToMove),
                        toWish.getRemark(), toWish.getTags(), toWish.getId());
                Amount wishSavedDifference = editedToWish.getSavedAmountToPriceDifference();

                /* Transfer excess amount to unused funds. */
                if (wishSavedDifference.value > 0) {
                    editedToWish = new Wish(toWish.getName(), toWish.getPrice(), toWish.getDate(),
                            toWish.getUrl(), toWish.getSavedAmount()
                            .incrementSavedAmount(toWish.getSavedAmountToPriceDifference().getAbsoluteAmount()),
                            toWish.getRemark(), toWish.getTags(), toWish.getId());
                    model.updateUnusedFunds(wishSavedDifference.getAbsoluteAmount());
                    commandResult = new CommandResult(String.format(MESSAGE_MOVE_EXCESS_TO_WISH,
                            toWish.getSavedAmountToPriceDifference().toString(),
                            this.fromIndex.getOneBased(), this.toIndex.getOneBased(), wishSavedDifference.toString(),
                            model.getUnusedFunds()));
                } else {
                    commandResult = new CommandResult(String.format(MESSAGE_MOVE_WISH_SUCCESS, amountToMove.toString(),
                            this.fromIndex.getOneBased(), this.toIndex.getOneBased()));
                }
                model.updateWish(fromWish, editedFromWish);
                model.updateWish(toWish, editedToWish);
                model.commitWishBook();
                EventsCenter.getInstance().post(new WishDataUpdatedEvent(editedFromWish));
                EventsCenter.getInstance().post(new WishDataUpdatedEvent(editedToWish));
            } catch (IllegalArgumentException iae) {
                throw new CommandException(iae.getMessage());
            }
        }
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MoveCommand)) {
            return false;
        }

        MoveCommand moveCommand = (MoveCommand) other;
        boolean areIndexesEqual;

        if (this.moveType.equals(MoveType.WISH_FROM_UNUSED_FUNDS)) {
            areIndexesEqual = (this.fromIndex == null) && this.toIndex.equals(moveCommand.toIndex);
        } else if (this.moveType.equals(MoveType.WISH_TO_UNUSED_FUNDS)) {
            areIndexesEqual = this.fromIndex.equals(moveCommand.fromIndex) && (this.toIndex == null);
        } else {
            areIndexesEqual = this.fromIndex.equals(moveCommand.fromIndex) && this.toIndex.equals(moveCommand.toIndex);
        }

        return areIndexesEqual
                && (this.amountToMove.equals(moveCommand.amountToMove))
                && this.moveType.equals(moveCommand.moveType);
    }

    private void checkValidIndex(List<Wish> wishList, Index index) throws CommandException {
        if (index.getZeroBased() >= wishList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WISH_DISPLAYED_INDEX);
        }
    }

    private void checkWishFulfilled(Wish wish) throws CommandException {
        if (wish.isFulfilled()) {
            throw new CommandException(Messages.MESSAGE_WISH_FULFILLED);
        }
    }
}
