package seedu.learnvocabulary.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.learnvocabulary.model.Model.PREDICATE_SHOW_ALL_WORDS;
import static seedu.learnvocabulary.testutil.TypicalWords.DELIBERATE;
import static seedu.learnvocabulary.testutil.TypicalWords.SUMO;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.NameContainsKeywordsPredicate;
import seedu.learnvocabulary.testutil.LearnVocabularyBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasWord_nullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasWord(null);
    }

    @Test
    public void hasWord_wordNotInLearnVocabulary_returnsFalse() {
        assertFalse(modelManager.hasWord(SUMO));
    }

    @Test
    public void hasWord_wordInLearnVocabulary_returnsTrue() {
        modelManager.addWord(SUMO);
        assertTrue(modelManager.hasWord(SUMO));
    }

    @Test
    public void getFilteredWordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredWordList().remove(0);
    }

    @Test
    public void equals() {
        LearnVocabulary learnVocabulary = new LearnVocabularyBuilder().withWord(SUMO).withWord(DELIBERATE).build();
        LearnVocabulary differentLearnVocabulary = new LearnVocabulary();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(learnVocabulary, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(learnVocabulary, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different learnVocabulary -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLearnVocabulary, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = SUMO.getName().fullName.split("\\s+");
        modelManager.updateFilteredWordList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(learnVocabulary, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLearnVocabularyFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(learnVocabulary, differentUserPrefs)));
    }

    @Test
    public void hasTag() {
        assertTrue(modelManager.hasTag(new Tag("toLearn")));
    }
}
