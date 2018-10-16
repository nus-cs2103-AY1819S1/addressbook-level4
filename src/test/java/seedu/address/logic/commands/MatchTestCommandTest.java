package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_NO_TOPIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;

public class MatchTestCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getTriviaBundle(), new UserPrefs());
    }

    @After
    public void cleanUp() {
        if (model.isInTestingState()) {
            model.stopTriviaTest();
        }
        if (expectedModel.isInTestingState()) {
            expectedModel.stopTriviaTest();
        }
    }

    @Test
    public void execute_testStartedSuccessfully() {
        expectedModel.startTriviaTest(new MatchTest(new Topic(VALID_TOPIC_PHYSICS), expectedModel.getTriviaBundle()));
        assertCommandSuccess(new MatchTestCommand(new Topic(VALID_TOPIC_PHYSICS)), model, commandHistory,
                MatchTestCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_testDidNotStart() {
        assertCommandFailure(new MatchTestCommand(new Topic(VALID_TOPIC_NO_TOPIC)), model, commandHistory,
                MatchTest.MESSAGE_MATCH_TEST_CONSTRAINS);
    }
}
