package seedu.learnvocabulary.commons.events.model;

import seedu.learnvocabulary.commons.events.BaseEvent;
import seedu.learnvocabulary.model.ReadOnlyLearnVocabulary;

/** Indicates the LearnVocabulary in the model has changed*/
public class LearnVocabularyChangedEvent extends BaseEvent {

    public final ReadOnlyLearnVocabulary data;

    public LearnVocabularyChangedEvent(ReadOnlyLearnVocabulary data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "number of words " + data.getWordList().size();
    }
}
