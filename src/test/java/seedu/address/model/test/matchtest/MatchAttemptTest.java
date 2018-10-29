package seedu.address.model.test.matchtest;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_EARTH_FLAT;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.testutil.TypicalCards.Q_GIT_COMMIT;
import static seedu.address.testutil.TypicalCards.Q_TALLEST_BUILDING;

import org.junit.Test;

import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.model.card.IndexedQuestion;

public class MatchAttemptTest {
    @Test
    public void testIncorrectAttempt() {
        // two different answer
        assertFalse(generateMatchAttempt(Q_FLAT_EARTH, Q_GIT_COMMIT.getAnswer()).isCorrect());
        assertFalse(generateMatchAttempt(Q_FLAT_EARTH, Q_TALLEST_BUILDING.getAnswer()).isCorrect());
    }

    @Test
    public void testCorrectAttempt() {
        // two same answer
        assertTrue(generateMatchAttempt(Q_FLAT_EARTH, Q_FLAT_EARTH.getAnswer()).isCorrect());
        assertTrue(generateMatchAttempt(Q_FLAT_EARTH, new Answer(VALID_ANSWER_EARTH_FLAT)).isCorrect());
    }

    @Test
    public void equals() {
        MatchAttempt matchAttempt = generateMatchAttempt(Q_FLAT_EARTH, Q_FLAT_EARTH.getAnswer());
        // same values -> returns true
        MatchAttempt matchAttemptCopy = generateMatchAttempt(Q_FLAT_EARTH, Q_FLAT_EARTH.getAnswer());
        assertTrue(matchAttempt.equals(matchAttemptCopy));

        // same object -> returns true
        assertTrue(matchAttempt.equals(matchAttempt));

        // null -> returns false
        assertFalse(matchAttempt.equals(null));

        // different type -> returns false
        assertFalse(matchAttempt.equals(5));

        // different cards matched being tested -> returns false
        assertFalse(matchAttempt.equals(generateMatchAttempt(Q_GIT_COMMIT, Q_GIT_COMMIT.getAnswer())));

    }

    private MatchAttempt generateMatchAttempt(Card cardAttempted, Answer answer) {
        return new MatchAttempt(cardAttempted, new IndexedQuestion(cardAttempted.getQuestion(), 0),
                new IndexedAnswer(answer, 0));
    }
}
