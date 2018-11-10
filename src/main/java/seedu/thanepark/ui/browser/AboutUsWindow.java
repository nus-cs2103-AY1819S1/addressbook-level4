package seedu.thanepark.ui.browser;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.commons.util.FilePathToUrl;

/**
 * Controller for an about us page
 */
public class AboutUsWindow extends BrowserRelatedUiPart<Stage> {

    public static final FilePathToUrl ABOUT_US_FILE_PATH =
        new FilePathToUrl("/docs/AboutUsWindow.html", false);

    private static final Logger logger = LogsCenter.getLogger(AboutUsWindow.class);
    private static final String FXML = "AboutUsWindow.fxml";

    @FXML
    private WebView aboutUsWindow;

    /**
     * Creates a new AboutUsWindow.
     *
     * @param root Stage to use as the root of the AboutUsWindow.
     */
    public AboutUsWindow(Stage root) {
        super(FXML, root);

        loadPage(ABOUT_US_FILE_PATH);
    }

    /**
     * Creates a new AboutUsWindow.
     */
    public AboutUsWindow() {
        this(new Stage());
    }

    @Override
    protected WebView getWebView() {
        return aboutUsWindow;
    }

    /**
     * Shows the aboutUs window.
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
        logger.fine("Showing about us page about the application.");
        getRoot().show();
    }

    /**
     * Returns true if the aboutUs window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the aboutUs window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
