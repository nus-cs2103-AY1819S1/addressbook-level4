package seedu.learnvocabulary.testutil;

import seedu.learnvocabulary.model.LearnVocabulary;
import seedu.learnvocabulary.model.word.Word;

/**
 * A utility class to help with building LearnVocabulary objects.
 * Example usage: <br>
 *     {@code LearnVocabulary ab = new LearnVocabularyBuilder().withWord("John", "Doe").build();}
 */
public class LearnVocabularyBuilder {

    private LearnVocabulary learnVocabulary;

    public LearnVocabularyBuilder() {
        learnVocabulary = new LearnVocabulary();
    }

    public LearnVocabularyBuilder(LearnVocabulary learnVocabulary) {
        this.learnVocabulary = learnVocabulary;
    }

    /**
     * Adds a new {@code Word} to the {@code LearnVocabulary} that we are building.
     */
    public LearnVocabularyBuilder withWord(Word word) {
        learnVocabulary.addWord(word);
        return this;
    }

    public LearnVocabulary build() {
        return learnVocabulary;
    }
}
