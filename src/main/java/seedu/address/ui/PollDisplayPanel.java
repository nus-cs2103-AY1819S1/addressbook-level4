package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.DisplayPollEvent;

import java.util.logging.Logger;

public class PollDisplayPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "PollDisplayPanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea pollDisplay;

    public PollDisplayPanel() {
        super(FXML);
        pollDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleNewResultAvailableEvent(DisplayPollEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> displayed.setValue(event.message));
    }
}
