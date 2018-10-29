package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_FORCE_FORMULA;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.MatchTestUtil;

public class MatchCommandTest {
    private MatchTest matchTest;
    private Model model;

    private MatchTest expectedMatchTest;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTriviaBundle(), new TriviaResults(),
                new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalTriviaBundle(),
                new TriviaResults(), new UserPrefs());

        matchTest = new MatchTest(new Topic(VALID_TOPIC_PHYSICS), model.getTriviaBundle());
        model.startTriviaTest(matchTest);

        expectedMatchTest = new MatchTest(new Topic(VALID_TOPIC_PHYSICS), model.getTriviaBundle());
        expectedModel.startTriviaTest(expectedMatchTest);
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
    public void execute_matchSuccess() {
        Index[] expectedEarthIndexes = MatchTestUtil.getIndexes(expectedMatchTest, Q_EARTH_ROUND);
        Index[] actualEarthIndexes = MatchTestUtil.getIndexes(matchTest, Q_EARTH_ROUND);
        Index[] expectedForceIndexes = MatchTestUtil.getIndexes(expectedMatchTest, Q_FORCE_FORMULA);
        Index[] actualForceIndexes = MatchTestUtil.getIndexes(matchTest, Q_FORCE_FORMULA);

        // Matching for the first time
        assertMatchCommandSuccess(actualEarthIndexes, expectedEarthIndexes);

        // Matching for the second time
        assertMatchCommandSuccess(actualForceIndexes, expectedForceIndexes);
    }

    @Test
    public void execute_matchFailure() {
        Index wrongCardCombiQ = MatchTestUtil.getIndexes(matchTest, Q_EARTH_ROUND)[0];
        Index wrongCardCombiA = MatchTestUtil.getIndexes(matchTest, Q_FORCE_FORMULA)[1];
        assertCommandFailure(new MatchCommand(wrongCardCombiQ, wrongCardCombiA), model, commandHistory,
                MatchCommand.MESSAGE_MATCH_FAILURE);
    }

    @Test
    public void execute_matchError() {
        // Both question and answer's index not found -> Will throw question index not found.
        assertCommandFailure(new MatchCommand(Index.fromZeroBased(100), Index.fromZeroBased(105)), model,
                commandHistory, MatchCommand.MESSAGE_QUESTION_NOT_FOUND);

        // Only question index not found.
        assertCommandFailure(new MatchCommand(Index.fromZeroBased(100), Index.fromZeroBased(1)), model,
                commandHistory, MatchCommand.MESSAGE_QUESTION_NOT_FOUND);

        // Only answer index not found.
        assertCommandFailure(new MatchCommand(Index.fromZeroBased(1), Index.fromZeroBased(100)), model,
                commandHistory, MatchCommand.MESSAGE_ANSWER_NOT_FOUND);

    }

    /**
     * Execute the match command using the card's question and answer to simulate a matching success.
     */
    private void assertMatchCommandSuccess(Index[] actualIndexes, Index[] expectedIndexes) {
        expectedModel.matchQuestionAndAnswer(expectedIndexes[0], expectedIndexes[1]);

        assertCommandSuccess(new MatchCommand(actualIndexes[0], actualIndexes[1]), model, commandHistory,
                MatchCommand.MESSAGE_MATCH_SUCCESS, expectedModel);
    }
}
