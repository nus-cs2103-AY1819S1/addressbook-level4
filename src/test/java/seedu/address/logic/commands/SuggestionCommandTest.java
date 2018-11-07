package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SuggestionCommandTest {


    @Test
    public void equals() {
        String first = "Alice";
        SuggestionCommand suggestionFirstCommand = new SuggestionCommand(first);
        // same object -> returns true
        assertTrue(suggestionFirstCommand.equals(suggestionFirstCommand));

        // same values -> returns true
        SuggestionCommand suggestionFirstCommandCopy = new SuggestionCommand(first);
        assertTrue(suggestionFirstCommand.equals(suggestionFirstCommandCopy));

        // different types -> returns false
        assertFalse(suggestionFirstCommand.equals(1));

    }


    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    public NameContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}

