package seedu.learnvocabulary.model;

import java.util.Set;

import javafx.collections.ObservableList;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Word;

/**
 * Unmodifiable view of LearnVocabulary.
 */
public interface ReadOnlyLearnVocabulary {
    /**
     * Returns an unmodifiable view of the words list.
     * This list will not contain any duplicate words.
     */
    ObservableList<Word> getWordList();
    Set<Tag> getTags();
}
