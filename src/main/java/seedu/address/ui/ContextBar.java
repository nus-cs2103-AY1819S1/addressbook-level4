package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ContextUpdateEvent;

/**
 * A ui for the context bar that is displayed at the header of the application.
 */
public class ContextBar extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ContextBar.class);
    private static final String FXML = "ContextBar.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea contextDisplay;

    public ContextBar() {
        super(FXML);
        contextDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleContextUpdateEvent(ContextUpdateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }

}
