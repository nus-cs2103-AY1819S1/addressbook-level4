package seedu.thanepark.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.thanepark.commons.core.Messages.MESSAGE_RIDES_LISTED_OVERVIEW;
import static seedu.thanepark.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE_FULL;
import static seedu.thanepark.testutil.TypicalRides.ACCELERATOR;
import static seedu.thanepark.testutil.TypicalRides.BIG;
import static seedu.thanepark.testutil.TypicalRides.CASTLE;
import static seedu.thanepark.testutil.TypicalRides.DUMBO;
import static seedu.thanepark.testutil.TypicalRides.ENCHANTED;
import static seedu.thanepark.testutil.TypicalRides.FANTASY;
import static seedu.thanepark.testutil.TypicalRides.getTypicalThanePark;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import seedu.thanepark.logic.CommandHistory;
import seedu.thanepark.model.Model;
import seedu.thanepark.model.ModelManager;
import seedu.thanepark.model.UserPrefs;
import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;

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
        Tag tag1 = new Tag("rollerCoaster");
        Tag tag2 = new Tag("heightRestrictions");
        String userInput = PREFIX_TAG + tag1.tagName + " " + PREFIX_TAG_FULL + tag2.tagName;
        Set<Tag> tags = new HashSet<>(Arrays.asList(tag1, tag2));
        RideContainsKeywordsPredicate predicate = preparePredicate(userInput, tags);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ACCELERATOR, BIG, DUMBO), model.getFilteredRideList());
    }

    @Test
    public void execute_address_multipleRidesFound() {
        String expectedMessage = String.format(MESSAGE_RIDES_LISTED_OVERVIEW, 1);
        Zone zone = new Zone("10th Street");
        String userInput = PREFIX_ZONE + zone.value;
        RideContainsKeywordsPredicate predicate = preparePredicate(userInput, zone);
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredRideList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DUMBO), model.getFilteredRideList());

        userInput = PREFIX_ZONE_FULL + zone.value;
        predicate = preparePredicate(userInput, zone);
        command = new FindCommand(predicate);
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
