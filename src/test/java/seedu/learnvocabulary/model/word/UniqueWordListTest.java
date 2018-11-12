package seedu.learnvocabulary.model.word;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_ABILITY;
import static seedu.learnvocabulary.testutil.TypicalWords.LEVITATE;
import static seedu.learnvocabulary.testutil.TypicalWords.SUMO;

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
        assertFalse(uniqueWordList.contains(SUMO));
    }

    @Test
    public void contains_wordInList_returnsTrue() {
        uniqueWordList.add(SUMO);
        assertTrue(uniqueWordList.contains(SUMO));
    }

    @Test
    public void contains_wordWithSameIdentityFieldsInList_returnsTrue() {
        uniqueWordList.add(SUMO);
        Word editedSumo = new WordBuilder(SUMO).withTags(VALID_TAG_ABILITY)
                .build();
        assertTrue(uniqueWordList.contains(editedSumo));
    }

    @Test
    public void add_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.add(null);
    }

    @Test
    public void add_duplicateWord_throwsDuplicateWordException() {
        uniqueWordList.add(SUMO);
        thrown.expect(DuplicateWordException.class);
        uniqueWordList.add(SUMO);
    }

    @Test
    public void setWord_nullTargetWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWord(null, SUMO);
    }

    @Test
    public void setWord_nullEditedWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.setWord(SUMO, null);
    }

    @Test
    public void setWord_targetWordNotInList_throwsWordNotFoundException() {
        thrown.expect(WordNotFoundException.class);
        uniqueWordList.setWord(SUMO, SUMO);
    }

    @Test
    public void setWord_editedWordIsSameWord_success() {
        uniqueWordList.add(SUMO);
        uniqueWordList.setWord(SUMO, SUMO);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(SUMO);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWord_editedWordHasSameIdentity_success() {
        uniqueWordList.add(SUMO);
        Word editedSumo = new WordBuilder(SUMO).withTags(VALID_TAG_ABILITY)
                .build();
        uniqueWordList.setWord(SUMO, editedSumo);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(editedSumo);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWord_editedWordHasDifferentIdentity_success() {
        uniqueWordList.add(SUMO);
        uniqueWordList.setWord(SUMO, LEVITATE);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(LEVITATE);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWord_editedWordHasNonUniqueIdentity_throwsDuplicateWordException() {
        uniqueWordList.add(SUMO);
        uniqueWordList.add(LEVITATE);
        thrown.expect(DuplicateWordException.class);
        uniqueWordList.setWord(SUMO, LEVITATE);
    }

    @Test
    public void remove_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueWordList.remove(null);
    }

    @Test
    public void remove_wordDoesNotExist_throwsWordNotFoundException() {
        thrown.expect(WordNotFoundException.class);
        uniqueWordList.remove(SUMO);
    }

    @Test
    public void remove_existingWord_removesWord() {
        uniqueWordList.add(SUMO);
        uniqueWordList.remove(SUMO);
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
        uniqueWordList.add(SUMO);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(LEVITATE);
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
        uniqueWordList.add(SUMO);
        List<Word> wordList = Collections.singletonList(LEVITATE);
        uniqueWordList.setWords(wordList);
        UniqueWordList expectedUniqueWordList = new UniqueWordList();
        expectedUniqueWordList.add(LEVITATE);
        assertEquals(expectedUniqueWordList, uniqueWordList);
    }

    @Test
    public void setWords_listWithDuplicateWords_throwsDuplicateWordException() {
        List<Word> listWithDuplicateWords = Arrays.asList(SUMO, SUMO);
        thrown.expect(DuplicateWordException.class);
        uniqueWordList.setWords(listWithDuplicateWords);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueWordList.asUnmodifiableObservableList().remove(0);
    }
}
