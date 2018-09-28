package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;

/**
 * Controller for a help page
 */
public class BudgetWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/HelpWindow.html";

    private static final Logger logger = LogsCenter.getLogger(BudgetWindow.class);
    private static final String FXML = "BudgetWindow.fxml";

    @FXML
    private WebView browser;

    /**
     * Creates a new BudgetWindow.
     *
     * @param root Stage to use as the root of the BudgetWindow.
     */
    public BudgetWindow(Stage root) {
        super(FXML, root);

//        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
//        browser.getEngine().load(userGuideUrl);
    }

    /**
     * Creates a new BudgetWindow.
     */
    public BudgetWindow() {
        this(new Stage());
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        raise(new ExitAppRequestEvent());
    }

    /**
     * Shows the budget window.
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
        logger.fine("Showing budget list of the hostel.");
        getRoot().show();
    }

    /**
     * Returns true if the budget window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the budget window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
