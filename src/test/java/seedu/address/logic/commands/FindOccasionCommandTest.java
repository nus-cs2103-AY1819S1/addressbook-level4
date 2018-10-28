package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_OCCASIONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandPersonTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_FIVE;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_FOUR;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_ONE;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_THREE;
import static seedu.address.testutil.TypicalOccasions.TYPICAL_OCCASION_TWO;
import static seedu.address.testutil.TypicalOccasions.getTypicalOccasionsAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.occasion.OccasionDateContainsKeywordsPredicate;
import seedu.address.model.occasion.OccasionLocationContainsKeywordsPredicate;
import seedu.address.model.occasion.OccasionNameContainsKeywordsPredicate;

public class FindOccasionCommandTest {
    private Model model = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalOccasionsAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        //test OccasionNameContainsKeywordsPredicate
        OccasionNameContainsKeywordsPredicate firstOccasionNamePredicate =
                new OccasionNameContainsKeywordsPredicate(Collections.singletonList("first"));
        OccasionNameContainsKeywordsPredicate secondOccasionNamePredicate =
                new OccasionNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindOccasionCommand findFirstOccasionNameCommand = new FindOccasionCommand(firstOccasionNamePredicate);
        FindOccasionCommand findSecondOccasionNameCommand = new FindOccasionCommand(secondOccasionNamePredicate);

        // same object -> returns true
        assertTrue(findFirstOccasionNameCommand.equals(findFirstOccasionNameCommand));

        // same values -> returns true
        FindOccasionCommand findFirstOccasionNameCommandCopy = new FindOccasionCommand(firstOccasionNamePredicate);
        assertTrue(findFirstOccasionNameCommand.equals(findFirstOccasionNameCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOccasionNameCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOccasionNameCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstOccasionNameCommand.equals(findSecondOccasionNameCommand));

        //test OccasionDateContainsKeywordsPredicate
        OccasionDateContainsKeywordsPredicate firstOccasionDatePredicate =
                new OccasionDateContainsKeywordsPredicate(Collections.singletonList("2018-01-01"));
        OccasionDateContainsKeywordsPredicate secondOccasionDatePredicate =
                new OccasionDateContainsKeywordsPredicate(Collections.singletonList("2018-01-02"));

        FindOccasionCommand findFirstOccasionDateCommand = new FindOccasionCommand(firstOccasionDatePredicate);
        FindOccasionCommand findSecondOccasionDateCommand = new FindOccasionCommand(secondOccasionDatePredicate);

        // same object -> returns true
        assertTrue(findFirstOccasionDateCommand.equals(findFirstOccasionDateCommand));

        // same values -> returns true
        FindOccasionCommand findFirstOccasionDateCommandCopy = new FindOccasionCommand(firstOccasionDatePredicate);
        assertTrue(findFirstOccasionDateCommand.equals(findFirstOccasionDateCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOccasionDateCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOccasionDateCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstOccasionDateCommand.equals(findSecondOccasionDateCommand));

        //test OccasionLocationContainsKeywordsPredicate
        OccasionLocationContainsKeywordsPredicate firstOccasionLocationPredicate =
                new OccasionLocationContainsKeywordsPredicate(Collections.singletonList("soc"));
        OccasionLocationContainsKeywordsPredicate secondOccasionLocationPredicate =
                new OccasionLocationContainsKeywordsPredicate(Collections.singletonList("utown"));

        FindOccasionCommand findFirstOccasionLocationCommand = new FindOccasionCommand(firstOccasionLocationPredicate);
        FindOccasionCommand findSecondOccasionLocationCommand =
                new FindOccasionCommand(secondOccasionLocationPredicate);

        // same object -> returns true
        assertTrue(findFirstOccasionLocationCommand.equals(findFirstOccasionLocationCommand));

        // same values -> returns true
        FindOccasionCommand findFirstOccasionLocationCommandCopy =
                new FindOccasionCommand(firstOccasionLocationPredicate);
        assertTrue(findFirstOccasionLocationCommand.equals(findFirstOccasionLocationCommandCopy));

        // different types -> returns false
        assertFalse(findFirstOccasionLocationCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstOccasionLocationCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstOccasionLocationCommand.equals(findSecondOccasionLocationCommand));
    }

    @Test
    public void execute_zeroKeywords_noOccasionFound() {
        String expectedMessage = String.format(MESSAGE_OCCASIONS_LISTED_OVERVIEW, 0);

        //test findOccasion using occasionName
        OccasionNameContainsKeywordsPredicate occasionNamePredicate = prepareOccasionNamePredicate(" ");
        FindOccasionCommand occasionNameCommand = new FindOccasionCommand(occasionNamePredicate);
        expectedModel.updateFilteredOccasionList(occasionNamePredicate);
        assertCommandSuccess(occasionNameCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOccasionList());

        //test findPerson using OccationDate
        OccasionDateContainsKeywordsPredicate occasionDatePredicate = prepareOccasionDatePredicate(" ");
        FindOccasionCommand occasionDateCommand = new FindOccasionCommand(occasionDatePredicate);
        expectedModel.updateFilteredOccasionList(occasionDatePredicate);
        assertCommandSuccess(occasionDateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOccasionList());

        //test findPerson using OccationLocation
        OccasionLocationContainsKeywordsPredicate occasionLocationPredicate = prepareOccasionLocationPredicate(" ");
        FindOccasionCommand occasionLocationCommand = new FindOccasionCommand(occasionLocationPredicate);
        expectedModel.updateFilteredOccasionList(occasionLocationPredicate);
        assertCommandSuccess(occasionLocationCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredOccasionList());

    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_OCCASIONS_LISTED_OVERVIEW, 3);

        //test findOccasion using occasionName
        OccasionNameContainsKeywordsPredicate occasionNamePredicate =
                prepareOccasionNamePredicate("tutorial submission christmas");
        FindOccasionCommand occasionNameCommand = new FindOccasionCommand(occasionNamePredicate);
        expectedModel.updateFilteredOccasionList(occasionNamePredicate);
        assertCommandSuccess(occasionNameCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_OCCASION_TWO, TYPICAL_OCCASION_THREE, TYPICAL_OCCASION_FOUR),
                model.getFilteredOccasionList());

        //test findOccasion using occasionDate
        OccasionDateContainsKeywordsPredicate occasionDatePredicate =
                prepareOccasionDatePredicate("2018-10-31 2018-11-13 2018-12-25");
        FindOccasionCommand occasionDateCommand = new FindOccasionCommand(occasionDatePredicate);
        expectedModel.updateFilteredOccasionList(occasionDatePredicate);
        assertCommandSuccess(occasionDateCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_OCCASION_TWO, TYPICAL_OCCASION_THREE, TYPICAL_OCCASION_FOUR),
                model.getFilteredOccasionList());

        //test findOccasion using OccasionLocation
        OccasionLocationContainsKeywordsPredicate occasionLocationPredicate =
                prepareOccasionLocationPredicate("utown room mongolia");
        FindOccasionCommand occasionLocationCommand = new FindOccasionCommand(occasionLocationPredicate);
        expectedModel.updateFilteredOccasionList(occasionLocationPredicate);
        assertCommandSuccess(occasionLocationCommand, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(TYPICAL_OCCASION_ONE, TYPICAL_OCCASION_TWO, TYPICAL_OCCASION_FIVE),
                model.getFilteredOccasionList());

    }

    /**
     * Parses {@code userInput} into a {@code OccasionNameContainsKeywordsPredicate}.
     */
    private OccasionNameContainsKeywordsPredicate prepareOccasionNamePredicate(String userInput) {
        return new OccasionNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code OcacsionDateContainsKeywordsPredicate}.
     */
    private OccasionDateContainsKeywordsPredicate prepareOccasionDatePredicate(String userInput) {
        return new OccasionDateContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }


    /**
     * Parses {@code userInput} into a {@code OccasionLocationContainsKeywordsPredicate}.
     */
    private OccasionLocationContainsKeywordsPredicate prepareOccasionLocationPredicate(String userInput) {
        return new OccasionLocationContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

}
