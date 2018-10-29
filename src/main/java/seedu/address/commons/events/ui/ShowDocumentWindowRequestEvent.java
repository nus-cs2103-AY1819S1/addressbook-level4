package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.document.Document;

public class ShowDocumentWindowRequestEvent extends BaseEvent {

    private Document document;

    public ShowDocumentWindowRequestEvent(Document document) {
        this.document = document;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Document getDocument() {
        return document;
    }
}
