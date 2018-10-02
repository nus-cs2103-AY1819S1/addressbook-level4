package seedu.address.model.card;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GIT;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_GIT_CLONE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.CardBuilder;

public class CardTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Card card = new CardBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        card.getTags().remove(0);
    }

    @Test
    public void equals() {
        // same values -> returns true
        Card earthRoundQCopy = new CardBuilder(Q_EARTH_ROUND).build();
        assertTrue(Q_EARTH_ROUND.equals(earthRoundQCopy));

        // same object -> returns true
        assertTrue(Q_EARTH_ROUND.equals(Q_EARTH_ROUND));

        // null -> returns false
        assertFalse(Q_EARTH_ROUND.equals(null));

        // different type -> returns false
        assertFalse(Q_EARTH_ROUND.equals(5));

        // different card -> returns false
        assertFalse(Q_EARTH_ROUND.equals(Q_GIT_CLONE));

        // different question -> returns false
        Card editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        assertFalse(editedEarthRoundQ.equals(Q_EARTH_ROUND));

        // different answer -> returns false
        editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withQuestion(VALID_QUESTION_GIT_COMMIT).build();
        assertFalse(editedEarthRoundQ.equals(Q_EARTH_ROUND));

        // different tags -> returns true
        editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withTags(VALID_TAG_GIT).build();
        assertTrue(editedEarthRoundQ.equals(Q_EARTH_ROUND));
    }
}
