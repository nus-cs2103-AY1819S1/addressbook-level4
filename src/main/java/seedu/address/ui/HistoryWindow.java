package seedu.address.ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a command history window.
 */
public class HistoryWindow extends UiPart<Stage> {
    private static final Logger logger = LogsCenter.getLogger(HistoryWindow.class);
    private static final String FXML = "HistoryWindow.fxml";
    private static final String MESSAGE_FILE_ERROR = "%1$s cannot be accessed!";
    private static final String URL_HEADER = "file:/";

    @FXML
    private WebView commandHistoryWindow;

    /**
     * Creates a new HistoryWindow.
     *
     * @param root Stage to use as the root of the HistoryWindow.
     */
    public HistoryWindow(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new HistoryWindow.
     */
    public HistoryWindow() {
        this(new Stage());
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
    public void show(String reportFileName) throws IOException {
        File file = new File(reportFileName);
        if (!file.exists() || !file.canRead()) {
            throw new IOException(String.format(MESSAGE_FILE_ERROR, reportFileName));
        }
        String reportUrl = URL_HEADER + file.getAbsolutePath();
        commandHistoryWindow.getEngine().load(reportUrl);

        logger.fine("Showing command history report.");
        getRoot().show();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }


}
