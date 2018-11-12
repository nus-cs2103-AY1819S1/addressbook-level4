package seedu.address.ui;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.commons.events.ui.ContextChangeEvent;
import seedu.address.model.Context;

/**
 * An UI component that displays information of the {@code Context}.
 */
public class ContextIndicator extends UiPart<Region> {

    private static final String FXML = "ContextIndicator.fxml";

    @FXML
    private Label contextName;

    public ContextIndicator(String contextId) {
        super(FXML);
        setContextName(contextId);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleContextChangeEvent(ContextChangeEvent event) {
        setContextName(event.getNewContext());
    }

    private void setContextName(String contextId) {
        if (contextId.equals(Context.VOLUNTEER_CONTEXT_ID)) {
            contextName.setText(Context.VOLUNTEER_CONTEXT_NAME.substring(0, 1).toUpperCase()
                    + Context.VOLUNTEER_CONTEXT_NAME.substring(1));
        } else if (contextId.equals(Context.EVENT_CONTEXT_ID)) {
            contextName.setText(Context.EVENT_CONTEXT_NAME.substring(0, 1).toUpperCase()
                    + Context.EVENT_CONTEXT_NAME.substring(1));
        } else if (contextId.equals(Context.RECORD_CONTEXT_ID)) {
            contextName.setText(Context.RECORD_CONTEXT_NAME.substring(0, 1).toUpperCase()
                    + Context.RECORD_CONTEXT_NAME.substring(1));
        }
    }
}
