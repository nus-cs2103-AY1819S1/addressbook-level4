package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalRides.ACCELERATOR;
import static seedu.address.testutil.TypicalRides.BIG;
import static seedu.address.testutil.TypicalRides.CASTLE;
import static seedu.address.testutil.TypicalRides.DUMBO;
import static seedu.address.testutil.TypicalRides.ENCHANTED;
import static seedu.address.testutil.TypicalRides.FANTASY;
import static seedu.address.testutil.TypicalRides.getTypicalThanePark;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.RideContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalThanePark(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        RideContainsKeywordsPredicate firstPredicate =
                new RideContainsKeywordsPredicate(Collections.singletonList("first"));
        RideContainsKeywordsPredicate secondPredicate =
                new RideContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different ride -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noRideFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 0);
        RideContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredRideList());
    }

    @Test
    public void execute_multipleKeywords_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 3);
        RideContainsKeywordsPredicate predicate = preparePredicate("carrousel Enchanted final");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CASTLE, ENCHANTED, FANTASY), model.getFilteredRideList());
    }

    @Test
    public void execute_singleTag_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 3);
        Tag tag = new Tag("friends");
        String userInput = PREFIX_TAG + tag.tagName;
        Set<Tag> tags = new HashSet<>();
        tags.add(tag);
        RideContainsKeywordsPredicate predicate = preparePredicate(userInput, tags);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACCELERATOR, BIG, DUMBO), model.getFilteredRideList());
    }

    @Test
    public void execute_address_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 1);
        Address address = new Address("10th Street");
        String userInput = PREFIX_ADDRESS + address.value;
        RideContainsKeywordsPredicate predicate = preparePredicate(userInput, address);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DUMBO), model.getFilteredRideList());
    }

    /**
     * Parses {@code userInput} into a {@code RideContainsKeywordsPredicate}.
     */
    private RideContainsKeywordsPredicate preparePredicate(String userInput) {
        return new RideContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} and {@code object} into a {@code RideContainsKeywordsPredicate}.
     */
    private RideContainsKeywordsPredicate preparePredicate(String userInput, Object object) {
        return new RideContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")), Optional.ofNullable(object));
    }
}
