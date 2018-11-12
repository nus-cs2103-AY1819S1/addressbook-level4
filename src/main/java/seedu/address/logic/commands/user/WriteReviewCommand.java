package seedu.address.logic.commands.user;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.WrittenReview;

/**
 * Adds a review from a user to the restaurant.
 * The review is stored in both the User and RestaurantAddressBook.
 */
public class WriteReviewCommand extends Command {

    public static final String COMMAND_WORD = "writeReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Gives a review of the restaurant identified "
            + "by the index number used in the displayed restaurant list.\n"
            + "A review consists of a rating from 1 to 5 and a written review of the experience at the restaurant.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_RATING + "RATING "
            + PREFIX_REVIEW + "REVIEW\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RATING + "3 "
            + PREFIX_REVIEW + "I tried the cold noodles and pork bulgogi for dinner. They were delicious. ";
    public static final String MESSAGE_SUCCESS = "Successfully Wrote Review";
    public static final String MESSAGE_NOT_LOGGED_IN = "You must login before adding a review";

    private final Index index;
    private final Rating rating;
    private final WrittenReview writtenReview;

    /**
     * Creates a WriteReview to add the specified {@code Integer} review, that ranges from 1 - 5.
     */
    public WriteReviewCommand(Index index, Rating rating, WrittenReview writtenReview) {
        requireNonNull(index);
        requireNonNull(rating);
        requireNonNull(writtenReview);

        this.index = index;
        this.rating = rating;
        this.writtenReview = writtenReview;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (!model.isCurrentlyLoggedIn()) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantToReview = lastShownList.get(index.getZeroBased());

        model.addUserReview(restaurantToReview, rating, writtenReview);
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WriteReviewCommand // instanceof handles nulls
                && index.equals(((WriteReviewCommand) other).index)
                && rating.equals(((WriteReviewCommand) other).rating)
                && writtenReview.equals(((WriteReviewCommand) other).writtenReview));
    }
}
