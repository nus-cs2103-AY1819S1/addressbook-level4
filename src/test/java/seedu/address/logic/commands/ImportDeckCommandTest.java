package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.model.deck.anakinexceptions.DuplicateDeckException;
import seedu.address.testutil.Assert;

public class ImportDeckCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();



    @Test
    public void importDuplicateDeck_throwsDuplicateDeckException() throws Exception{
        thrown.expect(DuplicateDeckException.class);
    }
}
