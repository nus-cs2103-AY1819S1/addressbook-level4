package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Takes in a String as user input, and displays the correct answer after that
 */
public class OpenEndedTestShowAnswerEvent extends BaseEvent {

    private String userAnswer;

    public OpenEndedTestShowAnswerEvent(String userInput) {
        this.userAnswer = userInput;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public String getUserAnswer() {
        return userAnswer;
    }
}
