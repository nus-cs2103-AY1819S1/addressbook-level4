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
import seedu.address.commons.events.ui.NewResultAvailableEvent;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    public static final String SUCCESS_STYLE_CLASS = "success";

    private static final Logger logger = LogsCenter.getLogger(ResultDisplay.class);
    private static final String FXML = "ResultDisplay.fxml";

    private final StringProperty displayed = new SimpleStringProperty("");

    @FXML
    private TextArea resultDisplay;

    public ResultDisplay() {
        super(FXML);
        resultDisplay.textProperty().bind(displayed);
        registerAsAnEventHandler(this);
    }

    //@@author jroberts91-reused
    //Reused from https://github.com/se-edu/addressbook-level4/pull/799/files with minor modifications
    @Subscribe
    private void handleNewResultAvailableEvent(NewResultAvailableEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        Platform.runLater(() -> {
            displayed.setValue(event.message);

            if (event.isSuccessful) {
                setStyleForCommandSuccess();
            } else {
                setStyleForCommandFailure();
            }
        });
    }
    //@@author

    //Solution below adapted from https://github.com/se-edu/addressbook-level4/pull/799/files
    /**
     * Sets Result display text style to green to show Success command.
     */
    private void setStyleForCommandSuccess() {
        resultDisplay.getStyleClass().remove(ERROR_STYLE_CLASS);
        resultDisplay.getStyleClass().add(SUCCESS_STYLE_CLASS);
    }

    //Solution below adapted from https://github.com/se-edu/addressbook-level4/pull/799/files
    /**
     * Sets Result display text style to green to show Success command.
     */
    private void setStyleForCommandFailure() {
        if (resultDisplay.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            return;
        }

        resultDisplay.getStyleClass().add(ERROR_STYLE_CLASS);
    }
}
