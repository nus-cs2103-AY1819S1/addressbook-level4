package seedu.address.model.test.matchtest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_GIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_NO_TOPIC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.testutil.TypicalCards.Q_DENSITY_FORMULA;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_FORCE_FORMULA;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.topic.Topic;
import seedu.address.testutil.MatchTestUtil;
import seedu.address.testutil.TypicalCards;
import seedu.address.testutil.TypicalPersons;

public class MatchTestTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager model;
    private MatchTest matchTest;

    /** The indexes of matching question and answer from the test. */
    private Index[] earthRoundIndexes;
    private Index[] forceFormulaIndexes;
    private Index[] densityFormulaIndexes;

    @Before
    public void setUp() {
        model = new ModelManager(TypicalPersons.getTypicalAddressBook(),
                TypicalCards.getTypicalTriviaBundle(), new UserPrefs());

        // There will be 3 cards in this matchTest
        matchTest = new MatchTest(new Topic(VALID_TOPIC_PHYSICS), model.getTriviaBundle());

        earthRoundIndexes = MatchTestUtil.getIndexes(matchTest, Q_EARTH_ROUND);
        forceFormulaIndexes = MatchTestUtil.getIndexes(matchTest, Q_FORCE_FORMULA);
        densityFormulaIndexes = MatchTestUtil.getIndexes(matchTest, Q_DENSITY_FORMULA);

        model.startTriviaTest(matchTest);
    }

    @After
    public void cleanUp() {
        if (model.isInTestingState()) {
            model.stopTriviaTest();
        }
    }

    @Test
    public void invalid_matchTest() {
        thrown.expect(IllegalArgumentException.class);
        matchTest = new MatchTest(new Topic(VALID_TOPIC_NO_TOPIC), model.getTriviaBundle());
    }

    @Test
    public void test_matchPass() {
        assertTrue(model.matchQuestionAndAnswer(earthRoundIndexes[0], earthRoundIndexes[1]));

        assertTrue(model.matchQuestionAndAnswer(forceFormulaIndexes[0], forceFormulaIndexes[1]));

        assertTrue(model.matchQuestionAndAnswer(densityFormulaIndexes[0], densityFormulaIndexes[1]));

        assertEquals(matchTest.getAttempts().size(), 3);
    }

    @Test
    public void test_matchFail() {
        assertFalse(model.matchQuestionAndAnswer(earthRoundIndexes[0], forceFormulaIndexes[1]));
        assertFalse(model.matchQuestionAndAnswer(forceFormulaIndexes[0], densityFormulaIndexes[1]));
        assertFalse(model.matchQuestionAndAnswer(densityFormulaIndexes[0], earthRoundIndexes[1]));

    }

    @Test
    public void test_matchOutOfBoundsIndexes() {
        thrown.expect(IndexOutOfBoundsException.class);
        model.matchQuestionAndAnswer(Index.fromOneBased(100), Index.fromOneBased(100));
    }

    @Test
    public void test_responseToCorrectMatchAttempt() {
        matchTest.respondToCorrectAttempt(new MatchAttempt(Q_EARTH_ROUND, Q_EARTH_ROUND));
        assertEquals(matchTest.getQuestions().size(), 2);
        assertEquals(matchTest.getAnswers().size(), 2);
        assertFalse(matchTest.isCompleted());

        matchTest.respondToCorrectAttempt(new MatchAttempt(Q_DENSITY_FORMULA, Q_DENSITY_FORMULA));
        matchTest.respondToCorrectAttempt(new MatchAttempt(Q_FORCE_FORMULA, Q_FORCE_FORMULA));
        assertTrue(matchTest.isCompleted());
    }

    @Test
    public void test_isCompletedFlagFalse() {
        // if the ongoing test isn't stopped, isCompleted will be false
        assertFalse(matchTest.isCompleted());

        // If there are existing unanswered questions, isCompleted flag should be false.
        matchTest.getQuestions().remove(0);
        matchTest.getAnswers().remove(0);
        model.stopTriviaTest();
        assertFalse(matchTest.isCompleted());
    }

    @Test
    public void test_isCompletedFlagTrue() {
        // If there are no more existing unanswered questions, isCompleted flag should be true.
        matchTest.getQuestions().clear();
        matchTest.getAnswers().clear();
        model.stopTriviaTest();
        assertTrue(matchTest.isCompleted());
    }

    @Test
    public void equals() {
        // same values -> returns true
        MatchTest matchTestCopy = new MatchTest(matchTest.getTopic(), model.getTriviaBundle());
        assertTrue(matchTest.equals(matchTestCopy));

        // same object -> returns true
        assertTrue(matchTest.equals(matchTest));

        // null -> returns false
        assertFalse(matchTest.equals(null));

        // different type -> returns false
        assertFalse(matchTest.equals(5));

        // different cards being tested -> returns false
        assertFalse(matchTest.equals(new MatchTest(new Topic(VALID_TOPIC_GIT), model.getTriviaBundle())));

    }
}
