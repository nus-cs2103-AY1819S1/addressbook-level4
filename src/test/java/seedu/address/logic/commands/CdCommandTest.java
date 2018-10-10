package seedu.address.logic.commands;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code CdCommand}.
 */
public class CdCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        Path currPath = Paths.get(".");
        Path nextPath = Paths.get("Desktop");
        Path backPath = Paths.get("..");

        CdCommand currDirectory = new CdCommand(currPath);
        CdCommand nextDirectory = new CdCommand(nextPath);
        CdCommand prevDirectory = new CdCommand(backPath);

        String homeDirectory = System.getProperty("user.home");
        String currDir = homeDirectory + "/" + currDirectory.getPath().toString();
        String nextDir = homeDirectory + "/" + nextDirectory.getPath().toString();
        String prevDir = homeDirectory + "/" + prevDirectory.getPath().toString();

        // same destination -> returns true
        assertTrue(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));

        model.updateUserPrefs(model.getCurrDirectory().resolve("Desktop").normalize());
        assertTrue(Paths.get(nextDir).normalize().equals(model.getCurrDirectory()));

        model.updateUserPrefs(model.getCurrDirectory().resolve("..").normalize());
        assertTrue(Paths.get(currDir).normalize().equals(model.getCurrDirectory()));

        model.updateUserPrefs(model.getCurrDirectory().resolve("..").normalize());
        assertTrue(Paths.get(prevDir).normalize().equals(model.getCurrDirectory()));

        /*// same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));*/
    }

    /*@Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        NameContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        NameContainsKeywordsPredicate predicate = preparePredicate("Kurz Elle Kunz");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPersonList());
    }

    *//**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     *//*
    private NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }*/
}
