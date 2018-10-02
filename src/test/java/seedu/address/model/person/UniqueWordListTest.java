package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.word.UniqueWordList;
import seedu.address.model.word.Word;
import seedu.address.model.word.exceptions.DuplicatePersonException;
import seedu.address.model.word.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueWordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueWordList uniqueWordList = new UniqueWordList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.contains(null);
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueWordList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueWordList.add(ALICE);
        assertTrue(uniqueWordList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWordList.add(ALICE);
        Word editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueWordList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.add(null);
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueWordList.add(ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueWordList.add(ALICE);
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWord(null, ALICE);
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWord(ALICE, null);
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueWordList.setWord(ALICE, ALICE);
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueWordList.add(ALICE);
        uniqueWordList.setWord(ALICE, ALICE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(ALICE);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueWordList.add(ALICE);
        Word editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueWordList.setWord(ALICE, editedAlice);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(editedAlice);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueWordList.add(ALICE);
        uniqueWordList.setWord(ALICE, BOB);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(BOB);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueWordList.add(ALICE);
        uniqueWordList.add(BOB);
        thrown.expect(DuplicatePersonException.class);
        uniqueWordList.setWord(ALICE, BOB);
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.remove(null);
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        uniqueWordList.remove(ALICE);
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueWordList.add(ALICE);
        uniqueWordList.remove(ALICE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWords((UniqueWordList) null);
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueWordList.add(ALICE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(BOB);
        uniqueWordList.setWords(expectedUniqueWordList);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWords((List<Word>) null);
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueWordList.add(ALICE);
        List<Word> wordList = Collections.singletonList(BOB);
        uniqueWordList.setWords(wordList);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(BOB);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Word> listWithDuplicateWords = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicatePersonException.class);
        uniqueWordList.setWords(listWithDuplicateWords);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueWordList.asUnmodifiableObservableList().remove(0);
    }
}
