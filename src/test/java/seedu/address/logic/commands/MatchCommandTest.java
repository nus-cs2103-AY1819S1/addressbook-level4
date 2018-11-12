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
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.test.TriviaResults;
import seedu.address.model.test.matchtest.MatchTest;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.MatchIndexPair;

public class MatchCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void constructor_nullAnswer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new MatchCommand(Index.fromZeroBased(1), null);
    }

    @Test
    public void execute_correctMatch() {
        MatchIndexPair expectedEarthIndexes = new MatchIndexPair(expectedMatchTest, Q_EARTH_ROUND);
        MatchIndexPair actualEarthIndexes = new MatchIndexPair(matchTest, Q_EARTH_ROUND);
        MatchIndexPair expectedForceIndexes = new MatchIndexPair(expectedMatchTest, Q_FORCE_FORMULA);
        MatchIndexPair actualForceIndexes = new MatchIndexPair(matchTest, Q_FORCE_FORMULA);

        // correct matches with input indexes that match correctly.
        assertMatchCommandCorrectMatch(actualEarthIndexes, expectedEarthIndexes);
        assertMatchCommandCorrectMatch(actualForceIndexes, expectedForceIndexes);
    }

    @Test
    public void execute_wrongMatch() {
        MatchIndexPair actualEarthIndexes = new MatchIndexPair(matchTest, Q_EARTH_ROUND);
        MatchIndexPair actualForceIndexes = new MatchIndexPair(matchTest, Q_FORCE_FORMULA);
        MatchIndexPair expectedEarthIndexes = new MatchIndexPair(expectedMatchTest, Q_EARTH_ROUND);
        MatchIndexPair expectedForceIndexes = new MatchIndexPair(expectedMatchTest, Q_FORCE_FORMULA);

        Index wrongCardCombiQ = actualEarthIndexes.getQIndex();
        Index wrongCardCombiA = actualForceIndexes.getAIndex();

        Index expectedWrongCardCombiQ = expectedEarthIndexes.getQIndex();
        Index expectedWrongCardCombiA = expectedForceIndexes.getAIndex();

        // wrong match with the answer index not matching the question index.
        assertMatchCommandWrongMatch(wrongCardCombiQ, wrongCardCombiA,
                expectedWrongCardCombiQ, expectedWrongCardCombiA);
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
     * Execute the match command using the card's question and answer to simulate a correct match.
     */
    private void assertMatchCommandCorrectMatch(MatchIndexPair actualIndexes, MatchIndexPair expectedIndexes) {
        expectedModel.matchQuestionAndAnswer(expectedIndexes.getQIndex(), expectedIndexes.getAIndex());

        assertCommandSuccess(new MatchCommand(actualIndexes.getQIndex(), actualIndexes.getAIndex()),
                model, commandHistory, MatchCommand.MESSAGE_CORRECT_MATCH, expectedModel);
    }

    /**
     * Execute the match command using the card's question and answer to simulate a wrong match.
     */
    private void assertMatchCommandWrongMatch(Index actualIndexQ, Index actualIndexA,
                                              Index expectedIndexQ, Index expectedIndexA) {
        expectedModel.matchQuestionAndAnswer(expectedIndexQ, expectedIndexA);

        assertCommandSuccess(new MatchCommand(actualIndexQ, actualIndexA), model, commandHistory,
                MatchCommand.MESSAGE_WRONG_MATCH, expectedModel);
    }
}
