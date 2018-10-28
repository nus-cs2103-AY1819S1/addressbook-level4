package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import seedu.address.commons.core.LogsCenter;

/**
 * Controller for document page
 */
public class DocumentWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(DocumentWindow.class);
    private static final String FXML = "DocumentWindow.fxml";

    @javafx.fxml.FXML
    private WebView browser;

    /**
     * Creates a new DocumentWindow.
     *
     * @param root Stage to use as the root of the DocumentWindow.
     */
    public DocumentWindow(Stage root, String filePath) {
        super(FXML, root);

        String documentUrl = getClass().getResource(filePath).toString();
        Platform.runLater(()->browser.getEngine().load(documentUrl));
    }

    /**
     * Creates a new DocumentWindow.
     */
    public DocumentWindow(String filePath) {
        this(new Stage(), filePath);
    }

    /**
     * Shows the document window.
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
        logger.fine("Showing document for file.");
        getRoot().show();
    }

    /**
     * Returns true if the document window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the document window.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}
