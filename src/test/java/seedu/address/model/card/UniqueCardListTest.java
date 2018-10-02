package seedu.address.model.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_GIT_COMMIT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_GIT;
import static seedu.address.testutil.TypicalCards.Q_EARTH_ROUND;
import static seedu.address.testutil.TypicalCards.Q_GIT_CLONE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.card.exceptions.CardNotFoundException;
import seedu.address.model.card.exceptions.DuplicateCardException;
import seedu.address.testutil.CardBuilder;


public class UniqueCardListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueCardList uniqueCardList = new UniqueCardList();

    @Test
    public void contains_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.contains(null);
    }

    @Test
    public void contains_cardNotInList_returnsFalse() {
        assertFalse(uniqueCardList.contains(Q_EARTH_ROUND));
    }

    @Test
    public void contains_cardInList_returnsTrue() {
        uniqueCardList.add(Q_EARTH_ROUND);
        assertTrue(uniqueCardList.contains(Q_EARTH_ROUND));
    }

    @Test
    public void contains_cardWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCardList.add(Q_EARTH_ROUND);
        Card editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withTags(VALID_TAG_GIT).build();
        assertTrue(uniqueCardList.contains(editedEarthRoundQ));
    }

    @Test
    public void add_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.add(null);
    }

    @Test
    public void add_duplicateCard_throwsDuplicateCardException() {
        uniqueCardList.add(Q_EARTH_ROUND);
        thrown.expect(DuplicateCardException.class);
        uniqueCardList.add(Q_EARTH_ROUND);
    }

    @Test
    public void setCard_nullTargetCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCard(null, Q_EARTH_ROUND);
    }

    @Test
    public void setCard_nullEditedCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCard(Q_EARTH_ROUND, null);
    }

    @Test
    public void setCard_targetCardNotInList_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        uniqueCardList.setCard(Q_EARTH_ROUND, Q_EARTH_ROUND);
    }

    @Test
    public void setCard_editedCardIsSameCard_success() {
        uniqueCardList.add(Q_EARTH_ROUND);
        uniqueCardList.setCard(Q_EARTH_ROUND, Q_EARTH_ROUND);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(Q_EARTH_ROUND);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasSameIdentity_success() {
        uniqueCardList.add(Q_EARTH_ROUND);
        Card editedEarthRoundQ = new CardBuilder(Q_EARTH_ROUND).withAnswer(VALID_ANSWER_GIT_COMMIT)
                .withTags(VALID_TAG_GIT).build();
        uniqueCardList.setCard(Q_EARTH_ROUND, editedEarthRoundQ);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(editedEarthRoundQ);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasDifferentIdentity_success() {
        uniqueCardList.add(Q_EARTH_ROUND);
        uniqueCardList.setCard(Q_EARTH_ROUND, Q_GIT_CLONE);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(Q_GIT_CLONE);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCard_editedCardHasNonUniqueIdentity_throwsDuplicateCardException() {
        uniqueCardList.add(Q_EARTH_ROUND);
        uniqueCardList.add(Q_GIT_CLONE);
        thrown.expect(DuplicateCardException.class);
        uniqueCardList.setCard(Q_EARTH_ROUND, Q_GIT_CLONE);
    }

    @Test
    public void remove_nullCard_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.remove(null);
    }

    @Test
    public void remove_cardDoesNotExist_throwsCardNotFoundException() {
        thrown.expect(CardNotFoundException.class);
        uniqueCardList.remove(Q_EARTH_ROUND);
    }

    @Test
    public void remove_existingCard_removesCard() {
        uniqueCardList.add(Q_EARTH_ROUND);
        uniqueCardList.remove(Q_EARTH_ROUND);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_nullUniqueCardList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCards((UniqueCardList) null);
    }

    @Test
    public void setCards_uniqueCardList_replacesOwnListWithProvidedUniqueCardList() {
        uniqueCardList.add(Q_EARTH_ROUND);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(Q_GIT_CLONE);
        uniqueCardList.setCards(expectedUniqueCardList);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueCardList.setCards((List<Card>) null);
    }

    @Test
    public void setCards_list_replacesOwnListWithProvidedList() {
        uniqueCardList.add(Q_EARTH_ROUND);
        List<Card> personList = Collections.singletonList(Q_GIT_CLONE);
        uniqueCardList.setCards(personList);
        UniqueCardList expectedUniqueCardList = new UniqueCardList();
        expectedUniqueCardList.add(Q_GIT_CLONE);
        assertEquals(expectedUniqueCardList, uniqueCardList);
    }

    @Test
    public void setCards_listWithDuplicateCards_throwsDuplicateCardException() {
        List<Card> listWithDuplicateCards = Arrays.asList(Q_EARTH_ROUND, Q_EARTH_ROUND);
        thrown.expect(DuplicateCardException.class);
        uniqueCardList.setCards(listWithDuplicateCards);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueCardList.asUnmodifiableObservableList().remove(0);
    }
}
