package seedu.address.ui;

import com.google.common.eventbus.Subscribe;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ShowHelpRequestEvent;
import seedu.address.commons.events.ui.ShowModuleRequestEvent;
import seedu.address.commons.events.ui.ShowOccasionRequestEvent;
import seedu.address.commons.events.ui.ShowPersonRequestEvent;

import java.net.URL;
import java.util.logging.Logger;

public class MainWindow extends UiPart<Stage> {

    private Stage primaryStage;
    private final Logger logger = LogsCenter.getLogger(getClass());
    private HelpWindow helpWindow;

    public MainWindow(String fxml, Stage primaryStage) {
        super(fxml, primaryStage);
        this.primaryStage = primaryStage;
        helpWindow = new HelpWindow();
    }

    void hide() {
        primaryStage.hide();
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Opens up the module window on this primaryStage.
     */
    @FXML
    public void handleModule() {
        URL moduleWindowUrl = getFxmlFileUrl("ModuleWindow.fxml");
        loadFxmlFile(moduleWindowUrl, primaryStage);
    }

    /**
     * Opens up the persons window on this primaryStage.
     */
    @FXML
    public void handlePerson() {
        URL personWindowUrl = getFxmlFileUrl("PersonWindow.fxml");
        loadFxmlFile(personWindowUrl, primaryStage);
    }

    /**
     * Opesn up the occasions window on this primaryStage.
     */
    @FXML
    public void handleOccasion() {
        URL occasionWindowUrl = getFxmlFileUrl("OccasionWindow.fxml");
        loadFxmlFile(occasionWindowUrl, primaryStage);
    }

    @Subscribe
    private void handleShowHelpEvent(ShowHelpRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleHelp();
    }

    @Subscribe
    private void handleShowModuleEvent(ShowModuleRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleModule();
    }

    @Subscribe
    private void handleShowPersonEvent(ShowPersonRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handlePerson();
    }

    @Subscribe
    private void handleShowOccasionEvent(ShowOccasionRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        handleOccasion();
    }
}
