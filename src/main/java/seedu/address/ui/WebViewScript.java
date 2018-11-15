package seedu.address.ui;

/**
 * A WebView script runner that can run any javascript in the webview.
 */
public interface WebViewScript {
    void runScript(String script);
}
