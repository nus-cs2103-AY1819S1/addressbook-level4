package seedu.thanepark.ui.browser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebView;
import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.commons.util.FilePathToUrl;
import seedu.thanepark.ui.UiPart;

/**
 * Abstract class that loads Html pages, handles IOException when page cannot be read, and has ability to queue
 * Javascript calls for running after the page loads completely. Right click functionality is disabled by default.
 * Override the webView getter when extending this abstract class.
 */
public abstract class BrowserRelatedUiPart<T> extends UiPart<T> {
    private static final int MAX_QUEUE_SIZE = 15;

    protected ArrayList<String> queuedJavascriptCommands = new ArrayList<>();
    private final Logger logger = LogsCenter.getLogger(getClass());

    public BrowserRelatedUiPart(String fxml) {
        super(fxml);
        init();
    }

    public BrowserRelatedUiPart(String fxml, T root) {
        super(fxml, root);
        init();
    }

    /**
     * Initializes the BrowserRelatedUiPart. Disables rightclick, enables Javascript and adds a listener to execute
     * queued Javascript after the page loads completely.
     */
    private void init() {
        WebView webView = getWebView();
        assert(webView != null);
        webView.setContextMenuEnabled(false);
        webView.getEngine().setJavaScriptEnabled(true);
        webView.getEngine().getLoadWorker().stateProperty().addListener(
            new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {

                    if (newState == Worker.State.SUCCEEDED) {
                        runQueuedJavascript();
                    }

                }
            });
    }

    protected WebView getWebView() {
        return null;
    }

    /**
     * Program that runs all queued Javascript. Clears the Javascript queue if too many scripts are queued.
     */
    private void runQueuedJavascript() {
        WebView webView = getWebView();
        if (queuedJavascriptCommands.size() > MAX_QUEUE_SIZE) {
            logger.warning(String.format("Javascript queue size exceeds %1s", MAX_QUEUE_SIZE));
            queuedJavascriptCommands.clear();
        }
        while (!queuedJavascriptCommands.isEmpty()) {
            webView.getEngine().executeScript(queuedJavascriptCommands.remove(0));
        }
    }

    /**
     * Loads a local resource in the browser, with its Url decoded from FilePathToUrl
     */
    public void loadPage(FilePathToUrl filePathToUrl) {
        WebView webView = getWebView();
        try {
            String url = filePathToUrl.filePathToUrlString();
            Platform.runLater(() -> webView.getEngine().load(url));
        } catch (IOException e) {
            e.printStackTrace();
            logger.warning(e.getMessage());
            queuedJavascriptCommands.clear();
        }
    }

}
