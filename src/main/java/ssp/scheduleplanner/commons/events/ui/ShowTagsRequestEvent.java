package ssp.scheduleplanner.commons.events.ui;

import ssp.scheduleplanner.commons.events.BaseEvent;

/**
 * Indicates a request to expand the list of tags under a specified category.
 */
public class ShowTagsRequestEvent extends BaseEvent {

    public final String category;

    public ShowTagsRequestEvent(String cat) {
        this.category = cat;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}
