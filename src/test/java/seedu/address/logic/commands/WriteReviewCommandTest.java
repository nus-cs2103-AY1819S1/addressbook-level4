package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.user.WriteReviewCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.WrittenReview;
import seedu.address.model.user.Username;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.model.util.SampleUserDataUtil;
import seedu.address.testutil.Assert;

public class WriteReviewCommandTest {

    private Model model = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private Model expectedModel = new ModelManager(SampleDataUtil.getSampleAddressBook(), new UserPrefs(),
            SampleUserDataUtil.getSampleUserData());
    private CommandHistory commandHistory = new CommandHistory();

    private WrittenReview firstRestaurantReview = new WrittenReview("I tried the Ultimate Beef Sushi"
            + " Experience and they were delicious.");
    private Rating firstRestaurantRating = new Rating(3);
    private String expectedMessage = WriteReviewCommand.MESSAGE_SUCCESS;

    @Test
    public void execute_withoutLogin() {
        WriteReviewCommand writeReviewCommand = new WriteReviewCommand(INDEX_FIRST_RESTAURANT,
                firstRestaurantRating, firstRestaurantReview);

        Assert.assertThrows(CommandException.class, () -> writeReviewCommand.execute(model, commandHistory));
    }

    @Test
    public void execute_loginWithValidInputs() {
        Restaurant restaurantForReviewAdding = expectedModel.getFilteredRestaurantList()
                .get(INDEX_FIRST_RESTAURANT.getZeroBased());

        WriteReviewCommand writeReviewCommand = new WriteReviewCommand(INDEX_FIRST_RESTAURANT,
                firstRestaurantRating, firstRestaurantReview);

        expectedModel.loginUser(new Username("navekom"));
        model.loginUser(new Username("navekom"));
        expectedModel.addUserReview(restaurantForReviewAdding, firstRestaurantRating, firstRestaurantReview);
        expectedModel.commitAddressBook();
        assertCommandSuccess(writeReviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
