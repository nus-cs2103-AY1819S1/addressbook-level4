package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.storage.TemplateLoadedEvent;
import seedu.address.model.template.Template;

/**
 * A ui for the display of the loaded template.
 */
public class TemplatePanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(TemplatePanel.class);
    private static final String FXML = "TemplatePanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private Label templateLabel;
    @FXML
    private TextArea templatePanelDisplay;

    public TemplatePanel() {
        super(FXML);
        templatePanelDisplay
                .textProperty()
                .bind(displayed);
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleTemplateLoadedEvent(TemplateLoadedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            Template t = event.getTemplate();
            templateLabel.setText(String.format("Current Template: %s", t.getFilepath()));
            displayed.setValue(t.toString());
        });
    }

}
