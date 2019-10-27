package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.UpdateExpandedEntryRequestEvent;
import seedu.address.model.entry.ResumeEntry;

/**
 * An UI Part to show the specified expanded entry.
 */
public class ExpandedEntryPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ExpandedEntryPanel.class);
    private static final String FXML = "ExpandedEntryPanel.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private Label title;
    @FXML
    private Label subtitle;
    @FXML
    private Label duration;
    @FXML
    private Label categoryTag;
    @FXML
    private FlowPane tags;
    @FXML
    private TextArea expandedEntryText;


    public ExpandedEntryPanel() {
        super(FXML);
        expandedEntryText.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
        title.setWrapText(true);
        subtitle.setWrapText(true);
        duration.setWrapText(true);
        categoryTag.setVisible(false); // prevent bug where empty tag shows before an entry is selected
        expandedEntryText.setWrapText(true);
    }

    @Subscribe
    private void handleUpdateExpandedEntryRequestEvent(UpdateExpandedEntryRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        Platform.runLater(() -> {
            ResumeEntry entry = event.getUpdatedEntry();
            title.setText(entry.getEntryInfo().getTitle());
            subtitle.setText(entry.getEntryInfo().getSubHeader());
            duration.setText(entry.getEntryInfo().getDuration());
            categoryTag.setVisible(true);
            categoryTag.setText(entry.getCategory().cateName);
            tags.getChildren().clear();
            entry.getTags().forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
            displayed.setValue(entry.getDescription().toString());
        });
    }
}
