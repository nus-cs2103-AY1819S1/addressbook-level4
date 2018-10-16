package seedu.address.commons.events.ui;

import java.util.function.Supplier;

import seedu.address.commons.events.BaseEvent;
import seedu.address.ui.test.TriviaTestPage;

/**
 * An event requesting to show the trivia test page.
 */
public class ShowTriviaTestViewEvent extends BaseEvent {
    private Supplier<? extends TriviaTestPage> page;

    public ShowTriviaTestViewEvent(Supplier<? extends TriviaTestPage> page) {
        this.page = page;
    }

    public Supplier<? extends TriviaTestPage> getTriviaTestPage() {
        return page;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
