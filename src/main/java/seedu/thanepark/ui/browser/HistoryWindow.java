package seedu.thanepark.ui.browser;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.commons.util.FilePathToUrl;

/**
 * Controller for a command history window.
 */
public class HistoryWindow extends BrowserRelatedUiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(HistoryWindow.class);
    private static final String FXML = "HistoryWindow.fxml";

    @FXML
    private WebView commandHistoryWindow;

    /**
     * Creates a new HistoryWindow.
     */
    public HistoryWindow() {
        this(new Stage());
    }

    /**
     * Creates a new HistoryWindow.
     *
     * @param root Stage to use as the root of the HistoryWindow.
     */
    public HistoryWindow(Stage root) {
        super(FXML, root);
    }

    @Override
    protected WebView getWebView() {
        return commandHistoryWindow;
    }

    /**
     * Loads the specified reportFilePath, then shows the history window.
     */
    public void showWithFilePath(FilePathToUrl reportFilePath) {
        loadPage(reportFilePath);
        show();
    }

    /**
     * Shows the history window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing command history report.");
        getRoot().show();
    }

    /**
     * Returns true if the history window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the history window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


}
