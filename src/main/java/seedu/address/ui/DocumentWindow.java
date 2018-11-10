package seedu.address.ui;

import java.util.logging.Logger;

import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.document.Document;

/**
 * UI window representing a webview displaying the document generated for Patients.
 */
public class DocumentWindow extends UiPart<Stage> {

    public static final String DOCUMENT_TEMPLATE_FILE_PATH = "/view/Documents/DocumentTemplate.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "DocumentWindow.fxml";

    private static String documentTemplateUrl;

    private WebViewScript webViewScript;


    @FXML
    private WebView browser;

    /**
     * Creates a new DocumentWindow.
     *
     * @param root Stage to use as the root of the DocumentWindow.
     */
    public DocumentWindow(Stage root) {
        super(FXML, root);

        // Disable right-click
        this.browser.setContextMenuEnabled(false);

        // Initialise WebViewScript to run the script in the browser
        this.webViewScript = new WebViewScriptManager(this.browser);

        this.documentTemplateUrl = getClass().getResource(DOCUMENT_TEMPLATE_FILE_PATH).toExternalForm();
        this.load();
    }

    /**
     * Creates a new DocumentWindow.
     */
    public DocumentWindow() {
        this(new Stage());
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
    public void show(Document document) {
        logger.fine("Showing document screenshot");
        webViewScript.runScript(getScript(document));
        Platform.runLater(() -> browser.getEngine().load(documentTemplateUrl));
        getRoot().show();
    }

    private void load() {
        browser.getEngine().load(documentTemplateUrl);
    }

    /**
     * Default show command for testing.
     */
    public void showTest() {
        logger.fine("Showing default document");
        getRoot().show();
    }

    private String getScript(Document document) {
        String script = "loadDetails('";
        script += document.getFileName();
        script += "', '";
        script += document.getHeaders();
        script += "', '";
        script += document.getPatientName();
        script += "', '";
        script += document.getPatientIc();
        script += "', '";
        script += document.getContent();
        script += "');";
        return script;
    }

    /**
     * Returns true if the receipt window is currently being shown.
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
