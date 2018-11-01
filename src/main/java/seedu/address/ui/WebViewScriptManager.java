package seedu.address.ui;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.scene.web.WebView;

/**
 * Manager class for the webview script runner.
 */
public class WebViewScriptManager implements WebViewScript {

    private WebView webView;
    private int counter;

    public WebViewScriptManager(WebView webView) {
        this.webView = webView;
        this.counter = 0;
    }

    /**
     * This function will run script that is passed as argument.
     * @param script script to run.
     */
    public void runScript(String script) {
        int currentCounter = this.counter;
        this.webView.getEngine().getLoadWorker().stateProperty().addListener((
                ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
            if (newValue == Worker.State.SUCCEEDED && currentCounter == this.counter) {
                Platform.runLater(() -> this.webView.getEngine().executeScript(script));
                this.counter++;
            }
        });
    }
}
