package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstPerson;
import static seedu.address.testutil.TypicalCards.*;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.tag.Tag;

import java.util.HashSet;
import java.util.Set;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        /*
        model.addCard(new Card(new Question("how are you"), new Answer("I am fine"), new HashSet<Tag>()));
        model.addCard(new Card(new Question("how are you2"), new Answer("I am fine2"), new HashSet<Tag>()));
        expectedModel.addCard(new Card(new Question("how are you"), new Answer("I am fine"), new HashSet<Tag>()));
        expectedModel.addCard(new Card(new Question("how are you2"), new Answer("I am fine2"), new HashSet<Tag>()));
        */
        model.addCard(Q_FLAT_EARTH);
        model.addCard(Q_GIT_COMMIT);
        expectedModel.addCard(Q_FLAT_EARTH);
        expectedModel.addCard(Q_GIT_COMMIT);
    }

    /*
    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoTriviaBundle();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoTriviaBundle();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
    */
}
