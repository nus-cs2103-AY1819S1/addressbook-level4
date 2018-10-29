package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * A flash to the matched answer and question cards, to indicate they have been matched correctly or wrongly.
 */
public class FlashMatchOutcomeEvent extends BaseEvent {
    public final int indexOfQuestion;
    public final int indexOfAnswer;
    public final boolean isCorrect;

    public FlashMatchOutcomeEvent(int indexOfQuestion, int indexOfAnswer, boolean isCorrect) {
        this.indexOfQuestion = indexOfQuestion;
        this.indexOfAnswer = indexOfAnswer;
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
