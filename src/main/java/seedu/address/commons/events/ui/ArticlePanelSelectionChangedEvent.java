package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.article.Article;

/**
 * Represents a selection change in the Article List Panel
 */
public class ArticlePanelSelectionChangedEvent extends BaseEvent {


    private final Article newSelection;

    public ArticlePanelSelectionChangedEvent(Article newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Article getNewSelection() {
        return newSelection;
    }
}
