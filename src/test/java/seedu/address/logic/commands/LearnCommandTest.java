package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CARD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.TopicIsKeywordPredicate;
import seedu.address.model.test.TriviaResults;

/**
 * Contains integration tests (interaction with the Model) and unit tests for LearnCommand.
 */
public class LearnCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new TriviaResults(),
                new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(),
                new TriviaResults(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new LearnCommand(), model, commandHistory,
                String.format(LearnCommand.MESSAGE_SUCCESS, "all"), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCardAtIndex(model, INDEX_FIRST_CARD);
        assertCommandSuccess(new LearnCommand(), model, commandHistory,
                String.format(LearnCommand.MESSAGE_SUCCESS, "all"), expectedModel);
    }

    @Test
    public void execute_listFilteredByTopic() {
        expectedModel.updateFilteredCardList(new TopicIsKeywordPredicate(VALID_TOPIC_PHYSICS));
        assertCommandSuccess(new LearnCommand(new TopicIsKeywordPredicate(VALID_TOPIC_PHYSICS)), model, commandHistory,
                String.format(LearnCommand.MESSAGE_SUCCESS, VALID_TOPIC_PHYSICS), expectedModel);
    }
}
