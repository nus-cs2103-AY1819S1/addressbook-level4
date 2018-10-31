package seedu.learnvocabulary.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.logic.commands.CommandTestUtil.VALID_TAG_ABILITY;
import static seedu.learnvocabulary.testutil.TypicalWords.SUMO;
import static seedu.learnvocabulary.testutil.TypicalWords.getTypicalLearnVocabulary;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Word;
import seedu.learnvocabulary.model.word.exceptions.DuplicateWordException;
import seedu.learnvocabulary.testutil.WordBuilder;

public class LearnVocabularyTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final LearnVocabulary learnVocabulary = new LearnVocabulary();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), learnVocabulary.getWordList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        learnVocabulary.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLearnVocabulary_replacesData() {
        LearnVocabulary newData = getTypicalLearnVocabulary();
        learnVocabulary.resetData(newData);
        assertEquals(newData, learnVocabulary);
    }

    @Test
    public void resetData_withDuplicateWords_throwsDuplicateWordException() {
        // Two words with the same identity fields
        Word editedSumo = new WordBuilder(SUMO).withTags(VALID_TAG_ABILITY)
                .build();
        List<Word> newWords = Arrays.asList(SUMO, editedSumo);
        LearnVocabularyStub newData = new LearnVocabularyStub(newWords);

        thrown.expect(DuplicateWordException.class);
        learnVocabulary.resetData(newData);
    }

    @Test
    public void hasWord_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        learnVocabulary.hasWord(null);
    }

    @Test
    public void hasWord_wordNotInLearnVocabulary_returnsFalse() {
        assertFalse(learnVocabulary.hasWord(SUMO));
    }

    @Test
    public void hasWord_wordInLearnVocabulary_returnsTrue() {
        learnVocabulary.addWord(SUMO);
        assertTrue(learnVocabulary.hasWord(SUMO));
    }

    @Test
    public void hasWord_wordWithSameIdentityFieldsInLearnVocabulary_returnsTrue() {
        learnVocabulary.addWord(SUMO);
        Word editedSumo = new WordBuilder(SUMO).withTags(VALID_TAG_ABILITY)
                .build();
        assertTrue(learnVocabulary.hasWord(editedSumo));
    }

    @Test
    public void getWordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        learnVocabulary.getWordList().remove(0);
    }

    /**
     * A stub ReadOnlyLearnVocabulary whose words list can violate interface constraints.
     */
    private static class LearnVocabularyStub implements ReadOnlyLearnVocabulary {
        private final ObservableList<Word> words = FXCollections.observableArrayList();

        LearnVocabularyStub(Collection<Word> words) {
            this.words.setAll(words);
        }

        @Override
        public ObservableList<Word> getWordList() {
            return words;
        }

        @Override
        public Set<Tag> getTags() {
            return null;
        }
    }

}
