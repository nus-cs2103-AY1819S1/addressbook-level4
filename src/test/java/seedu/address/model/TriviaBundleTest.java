package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GIT;
import static seedu.address.testutil.TypicalCards.Q_CAPITAL_SG;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.getTypicalTriviaBundle;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.card.Card;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.testutil.CardBuilder;

public class TriviaBundleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TriviaBundle triviaBundle = new TriviaBundle();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), triviaBundle.getCardList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        triviaBundle.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTriviaBundle_replacesData() {
        TriviaBundle newData = getTypicalTriviaBundle();
        triviaBundle.resetData(newData);
        assertEquals(newData, triviaBundle);
    }

    @Test
    public void resetData_withDuplicateCards_throwsDuplicateCardException() {
        // Two cards with the same identity fields
        Card editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withAnswer(VALID_ANSWER_GIT_COMMIT)
                .withTags(VALID_TAG_GIT).build();
        List<Card> newCards = Arrays.asList(Q_EARTH_ROUND, editedEarthRoundQ);
        TriviaBundleStub newData = new TriviaBundleStub(newCards);

        thrown.expect(DuplicateCardException.class);
        triviaBundle.resetData(newData);
    }

    @Test
    public void hasCard_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        triviaBundle.hasCard(null);
    }

    @Test
    public void hasCard_cardNotInTriviaBundle_returnsFalse() {
        assertFalse(triviaBundle.hasCard(Q_CAPITAL_SG));
    }

    @Test
    public void hasCard_cardInTriviaBundle_returnsTrue() {
        triviaBundle.addCard(Q_EARTH_ROUND);
        assertTrue(triviaBundle.hasCard(Q_EARTH_ROUND));
    }

    @Test
    public void hasCard_cardWithSameIdentityFieldsInTriviaBundle_returnsTrue() {
        triviaBundle.addCard(Q_EARTH_ROUND);
        Card editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withAnswer(VALID_ANSWER_GIT_COMMIT)
                .withTags(VALID_TAG_GIT).build();
        assertTrue(triviaBundle.hasCard(editedEarthRoundQ));
    }

    @Test
    public void getCardList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        triviaBundle.getCardList().remove(0);
    }

    /**
     * A stub ReadOnlyTriviaBundle whose cards list can violate interface constraints.
     */
    private static class TriviaBundleStub implements ReadOnlyTriviaBundle {
        private final ObservableList<Card> cards = FXCollections.observableArrayList();

        TriviaBundleStub(Collection<Card> cards) {
            this.cards.setAll(cards);
        }

        @Override
        public ObservableList<Card> getCardList() {
            return cards;
        }
    }
}
