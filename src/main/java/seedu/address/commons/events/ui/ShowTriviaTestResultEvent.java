package seedu.address.commons.events.ui;

import java.util.function.Supplier;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.test.TriviaTestResultPage;

/**
 * An event requesting to show the result of trivia test page.
 */
public class ShowTriviaTestResultEvent extends BaseEvent {
    private final Supplier<? extends TriviaTestResultPage> page;

    public ShowTriviaTestResultEvent(Supplier<? extends TriviaTestResultPage> page) {
        this.page = page;
    }

    public Supplier<? extends TriviaTestResultPage> getTriviaTestResultPage() {
        return page;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
