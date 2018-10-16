package seedu.address.model.test.matchtest;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_EARTH_FLAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.testutil.TypicalCards.Q_FLAT_EARTH;
import static seedu.address.testutil.TypicalCards.Q_GIT_COMMIT;

import org.junit.Test;

import seedu.address.model.card.Card;
import seedu.address.testutil.CardBuilder;

public class MatchAttemptTest {

    @Test
    public void testIncorrectAttempt() {
        // two obviously different cards
        assertFalse(new MatchAttempt(Q_FLAT_EARTH, Q_GIT_COMMIT).isCorrect());

        // different question but same answer and topic
        Card editedCard = new CardBuilder(Q_FLAT_EARTH).withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        assertFalse(new MatchAttempt(Q_FLAT_EARTH, editedCard).isCorrect());
    }

    @Test
    public void testCorrectAttempt() {
        // two obviously same cards
        assertTrue(new MatchAttempt(Q_FLAT_EARTH, Q_FLAT_EARTH).isCorrect());

        // different answer and topic but same question
        Card editedCard = new CardBuilder(Q_GIT_COMMIT).withQuestion(VALID_QUESTION_EARTH_FLAT).build();
        assertTrue(new MatchAttempt(Q_FLAT_EARTH, editedCard).isCorrect());
    }

    @Test
    public void equals() {
        MatchAttempt matchAttempt = new MatchAttempt(Q_FLAT_EARTH, Q_FLAT_EARTH);
        // same values -> returns true
        MatchAttempt matchAttemptCopy = new MatchAttempt(Q_FLAT_EARTH, Q_FLAT_EARTH);
        assertTrue(matchAttempt.equals(matchAttemptCopy));

        // same object -> returns true
        assertTrue(matchAttempt.equals(matchAttempt));

        // null -> returns false
        assertFalse(matchAttempt.equals(null));

        // different type -> returns false
        assertFalse(matchAttempt.equals(5));

        // different cards matched being tested -> returns false
        assertFalse(matchAttempt.equals(new MatchAttempt(Q_GIT_COMMIT, Q_GIT_COMMIT)));

    }

}
