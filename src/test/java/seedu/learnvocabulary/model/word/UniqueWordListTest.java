package seedu.learnvocabulary.model.word;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.learnvocabulary.testutil.TypicalWords.ALICE;
import static seedu.learnvocabulary.testutil.TypicalWords.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.learnvocabulary.model.word.exceptions.DuplicateWordException;
import seedu.learnvocabulary.model.word.exceptions.WordNotFoundException;
import seedu.learnvocabulary.testutil.WordBuilder;

public class UniqueWordListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueWordList uniqueWordList = new UniqueWordList();

    @Test
    public void contains_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.contains(null);
    }

    @Test
    public void contains_wordNotInList_returnsFalse() {
        assertFalse(uniqueWordList.contains(ALICE));
    }

    @Test
    public void contains_wordInList_returnsTrue() {
        uniqueWordList.add(ALICE);
        assertTrue(uniqueWordList.contains(ALICE));
    }

    @Test
    public void contains_wordWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWordList.add(ALICE);
        Word editedAlice = new WordBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueWordList.contains(editedAlice));
    }

    @Test
    public void add_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.add(null);
    }

    @Test
    public void add_duplicateWord_throwsDuplicateWordException() {
        uniqueWordList.add(ALICE);
        thrown.expect(DuplicateWordException.class);
        uniqueWordList.add(ALICE);
    }

    @Test
    public void setWord_nullTargetWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWord(null, ALICE);
    }

    @Test
    public void setWord_nullEditedWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWord(ALICE, null);
    }

    @Test
    public void setWord_targetWordNotInList_throwsWordNotFoundException() {
        thrown.expect(WordNotFoundException.class);
        uniqueWordList.setWord(ALICE, ALICE);
    }

    @Test
    public void setWord_editedWordIsSameWord_success() {
        uniqueWordList.add(ALICE);
        uniqueWordList.setWord(ALICE, ALICE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(ALICE);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWord_editedWordHasSameIdentity_success() {
        uniqueWordList.add(ALICE);
        Word editedAlice = new WordBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueWordList.setWord(ALICE, editedAlice);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(editedAlice);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWord_editedWordHasDifferentIdentity_success() {
        uniqueWordList.add(ALICE);
        uniqueWordList.setWord(ALICE, BOB);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(BOB);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWord_editedWordHasNonUniqueIdentity_throwsDuplicateWordException() {
        uniqueWordList.add(ALICE);
        uniqueWordList.add(BOB);
        thrown.expect(DuplicateWordException.class);
        uniqueWordList.setWord(ALICE, BOB);
    }

    @Test
    public void remove_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.remove(null);
    }

    @Test
    public void remove_wordDoesNotExist_throwsWordNotFoundException() {
        thrown.expect(WordNotFoundException.class);
        uniqueWordList.remove(ALICE);
    }

    @Test
    public void remove_existingWord_removesWord() {
        uniqueWordList.add(ALICE);
        uniqueWordList.remove(ALICE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWords_nullUniqueWordList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWords((UniqueWordList) null);
    }

    @Test
    public void setWords_uniqueWordList_replacesOwnListWithProvidedUniqueWordList() {
        uniqueWordList.add(ALICE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(BOB);
        uniqueWordList.setWords(expectedUniqueWordList);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWords_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWords((List<Word>) null);
    }

    @Test
    public void setWords_list_replacesOwnListWithProvidedList() {
        uniqueWordList.add(ALICE);
        List<Word> wordList = Collections.singletonList(BOB);
        uniqueWordList.setWords(wordList);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(BOB);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWords_listWithDuplicateWords_throwsDuplicateWordException() {
        List<Word> listWithDuplicateWords = Arrays.asList(ALICE, ALICE);
        thrown.expect(DuplicateWordException.class);
        uniqueWordList.setWords(listWithDuplicateWords);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueWordList.asUnmodifiableObservableList().remove(0);
    }
}
