package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RATING_A;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REVIEW_A;
import static seedu.address.logic.commands.CommandTestUtil.RATING_A;
import static seedu.address.logic.commands.CommandTestUtil.REVIEW_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RATING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REVIEW;
import static seedu.address.testutil.TestUtil.getRestaurant;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalUsers.BENNY;

import org.junit.Test;

import seedu.address.logic.commands.user.WriteReviewCommand;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.User;

public class WriteReviewCommandSystemTest extends AddressBookSystemTest {

    private User currentUser = BENNY;

    @Test
    public void writeReview() {
        addUser(currentUser);
        login(currentUser);
        Model model = getModel();
        model.addUser(currentUser);
        model.loginUser(currentUser);

        // Valid Review, Valid Rating
        String command = WriteReviewCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RESTAURANT.getOneBased() + " "
                + RATING_A + " " + REVIEW_A;
        Restaurant targetRestaurant = getRestaurant(model, INDEX_FIRST_RESTAURANT);
        assertCommandSuccess(command, model, targetRestaurant, new Rating(Integer.parseInt(VALID_RATING)),
                new WrittenReview(VALID_REVIEW));

        // Invalid Rating
        command = WriteReviewCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RESTAURANT.getOneBased() + " "
                + INVALID_RATING_A + " " + REVIEW_A;
        assertCommandFailure(command, model, Rating.MESSAGE_RATING_CONSTRAINTS);

        // Invalid Review
        command = WriteReviewCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RESTAURANT.getOneBased() + " "
                + RATING_A + " " + INVALID_REVIEW_A;
        assertCommandFailure(command, model, WrittenReview.MESSAGE_REVIEW_CONSTRAINTS);

        // Invalid Restaurant Index
        command = WriteReviewCommand.COMMAND_WORD + " "
                + 0 + " "
                + RATING_A + " " + REVIEW_A;
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                WriteReviewCommand.MESSAGE_USAGE));

        // Valid Rating and Review Swapped
        command = WriteReviewCommand.COMMAND_WORD + " "
                + INDEX_FIRST_RESTAURANT.getOneBased() + " "
                + REVIEW_A + " " + RATING_A;
        assertCommandSuccess(command, model, targetRestaurant, new Rating(Integer.parseInt(VALID_RATING)),
                new WrittenReview(VALID_REVIEW));

        // Missing Rating
        command = WriteReviewCommand.COMMAND_WORD + " "
                + 0 + " "
                + REVIEW_A;
        assertCommandFailure(command, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                WriteReviewCommand.MESSAGE_USAGE));
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Restaurant)}.
     * Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, Model expectedModel, Restaurant toRate,
                                      Rating rating, WrittenReview writtenReview) {
        expectedModel.addUserReview(toRate, rating, writtenReview);
        String expectedResultMessage = WriteReviewCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as
     * {@code assertCommandSuccess(String, Restaurant)} except asserts that the,<br>
     * Result display box displays {@code expectedResultMessage}.<br>
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code RestaurantListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
