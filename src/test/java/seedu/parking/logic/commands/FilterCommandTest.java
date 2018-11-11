package seedu.parking.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.parking.commons.core.Messages.MESSAGE_CARPARKS_LISTED_OVERVIEW;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.parking.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.parking.testutil.TypicalCarparks.BRAVO;
import static seedu.parking.testutil.TypicalCarparks.CHARLIE;
import static seedu.parking.testutil.TypicalCarparks.DELTA;
import static seedu.parking.testutil.TypicalCarparks.ECHO;
import static seedu.parking.testutil.TypicalCarparks.FOXTROT;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparkFinder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.parking.commons.core.Messages;
import seedu.parking.logic.CommandHistory;
import seedu.parking.logic.parser.CarparkTypeParameter;
import seedu.parking.logic.parser.FreeParkingParameter;
import seedu.parking.logic.parser.ParkingSystemTypeParameter;
import seedu.parking.model.Model;
import seedu.parking.model.ModelManager;
import seedu.parking.model.UserPrefs;
import seedu.parking.model.carpark.CarparkContainsKeywordsPredicate;
import seedu.parking.model.carpark.CarparkFilteringPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCarparkFinder(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {

        // Create two Filter commands for testing
        List<String> flagList = new ArrayList<>();
        flagList.add("n/");
        flagList.add("ct/");
        flagList.add("ps/");

        FreeParkingParameter freeParking = new FreeParkingParameter("SUN", new Date(2470200200L), new Date(3748239175L));

        CarparkTypeParameter parkingType = new CarparkTypeParameter("COVERED");

        ParkingSystemTypeParameter firstParkingSystem = new ParkingSystemTypeParameter("COUPON");
        ParkingSystemTypeParameter secondParkingSystem = new ParkingSystemTypeParameter("ELECTRONIC");

        FilterCommand firstFilterCommand = new FilterCommand(flagList,
                freeParking, parkingType, firstParkingSystem);

        FilterCommand secondFilterCommand = new FilterCommand(flagList,
                freeParking, parkingType, secondParkingSystem);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand firstFilterCommandCopy = new FilterCommand(flagList,
                freeParking, parkingType, firstParkingSystem);
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // different carpark -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));
    }

    @Test
    public void execute_freeParking() { // find sengkang, filter f/ sun 8.30am 5.30pm

        // Models updated by previous Find Command
        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        // Create predicate
        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("f/");
        FreeParkingParameter freeParking = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh.mmaa");
            Date inputStart = dateFormat.parse("8.30am");
            Date inputEnd = dateFormat.parse("5.30pm");
            freeParking = new FreeParkingParameter("SUN", inputStart, inputEnd);
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList, freeParking,
                null, null);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 1);
        FilterCommand command = new FilterCommand(flagList, freeParking, null,
                null);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(DELTA), model.getFilteredCarparkList());
    }

    @Test
    public void execute_nightParking() { // find sengkang, filter n/

        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("n/");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(flagList, null, null,
                null);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRAVO, DELTA), model.getFilteredCarparkList());
    }

    @Test
    public void execute_availableParking() { // find sengkang, filter a/

        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("a/");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(flagList, null, null,
                null);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRAVO, DELTA), model.getFilteredCarparkList());
    }

    @Test
    public void execute_carParkType() { // find sengkang, filter ct/ multistorey

        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("ct/");
        CarparkTypeParameter carparkType = new CarparkTypeParameter("MULTISTOREY");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, carparkType, null);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(flagList, null, carparkType,
                null);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRAVO, DELTA), model.getFilteredCarparkList());
    }

    @Test
    public void execute_parkingSystem() { // find sengkang, filter ps/ coupon

        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("ps/");
        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, parkingSystemType);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 0);
        FilterCommand command = new FilterCommand(flagList, null, null,
                parkingSystemType);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCarparkList());
    }

    @Test
    public void execute_shortTermParking() { // find sengkang, filter s/

        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("s/");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(flagList, null, null,
                null);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BRAVO, DELTA), model.getFilteredCarparkList());
    }

    @Test
    public void execute_multipleFlags() { // find sengkang, filter s/ ps/ coupon n/

        CarparkContainsKeywordsPredicate findPredicate =
                new CarparkContainsKeywordsPredicate(Collections.singletonList("sengkang"));
        model.updateLastPredicateUsedByFindCommand(findPredicate);
        expectedModel.updateLastPredicateUsedByFindCommand(findPredicate);

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("s/");
        flagList.add("ps/");
        flagList.add("n/");
        ParkingSystemTypeParameter parkingSystemType = new ParkingSystemTypeParameter("COUPON");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, parkingSystemType);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = String.format(MESSAGE_CARPARKS_LISTED_OVERVIEW, 0);
        FilterCommand command = new FilterCommand(flagList, null, null,
                parkingSystemType);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredCarparkList());
    }

    @Test
    public void execute_filterCommandNotExecutedBefore_throwsCommandException() { // filter s/

        List<String> locationKeywords = new ArrayList<>();
        locationKeywords.add("sengkang");
        List<String> flagList = new ArrayList<>();
        flagList.add("s/");
        CarparkFilteringPredicate predicate = new CarparkFilteringPredicate(locationKeywords, flagList,
                null, null, null);

        expectedModel.updateFilteredCarparkList(predicate);
        String expectedMessage = Messages.MESSAGE_FINDCOMMAND_NEEDS_TO_BE_EXECUTED_FIRST;
        FilterCommand command = new FilterCommand(flagList, null, null,
                null);
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }
}
