package seedu.thanepark.ui.browser;

import java.util.Set;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.thanepark.commons.core.LogsCenter;
import seedu.thanepark.commons.util.FilePathToUrl;
import seedu.thanepark.ui.exceptions.AccessibilityException;

/**
 * Controller for a help page
 */
public class HelpWindow extends BrowserRelatedUiPart<Stage> {

    public static final FilePathToUrl USER_GUIDE_FILE_PATH =
        new FilePathToUrl("/docs/HelpWindow.html", false);
    public static final FilePathToUrl SHORT_HELP_FILE_PATH =
        new FilePathToUrl("/docs/ShortHelpWindow.html", false);

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    //Adapted from https://stackoverflow.com/questions/2238938/how-to-programmatically-scroll-android-webview
    //https://stackoverflow.com/questions/6991494/javascript-getelementbyid-base-on-partial-string
    //https://stackoverflow.com/questions/22778241/javafx-webview-scroll-to-desired-position
    //javascript to scroll webpage to commandWord
    private static final String SCROLL_JAVASCRIPT =
            "function scrollToElement(commandWord) {\n"
            + "    var elem = document.querySelector('[id$= code-' + commandWord + '-code]');\n"
            + "    var x = 0;\n"
            + "    var y = 0;\n"
            + "\n"
            + "    while (elem != null) {\n"
            + "        x += elem.offsetLeft;\n"
            + "        y += elem.offsetTop;\n"
            + "        elem = elem.offsetParent;\n"
            + "    }\n"
            + "    window.scrollTo(x, y);\n"
            + "}";

    private int verticalScroll = 0;

    @FXML
    private WebView browser;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        loadPage(USER_GUIDE_FILE_PATH);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    @Override
    protected WebView getWebView() {
        return browser;
    }

    /**
     * Shows the help window.
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
        logger.fine("Showing help page about the application.");
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

    /**
     * Scrolls the help window to the specified commandWord.
     */
    public void scrollToCommandWord(String commandWord) throws AccessibilityException {
        browser.getEngine().executeScript(SCROLL_JAVASCRIPT);
        browser.getEngine().executeScript("window.scrollTo(document.body.scrollLeft, 0)");
        verticalScroll = (int) browser.getEngine().executeScript("document.body.scrollTop");
        //No commandWords in the window are at the top of the window.
        assert(verticalScroll == 0);
        browser.getEngine().executeScript("scrollToElement(\"" + commandWord + "\")");
        assertScrollToCommandWordSuccessful();
    }

    /**
     * Checks if the help window successfully scrolled to the command word.
     */
    private void assertScrollToCommandWordSuccessful() throws AccessibilityException {
        if (verticalScroll != (int) browser.getEngine().executeScript("document.body.scrollTop")) {
            return;
        }
        throw new AccessibilityException("Help [commandWord] scrolling error!");
    }

    /**
     * Adapted from
     * https://stackoverflow.com/questions/31264847/how-to-set-remember-scrollbar-thumb-position-in-javafx-8-webview
     * Returns the vertical scrollbar of the webview.
     *
     * @param browser webview
     * @return vertical scrollbar of the webview or {@code null} if no vertical
     * scrollbar exists
     */
    private ScrollBar getVScrollBar(WebView browser) {
        Set<Node> scrolls = browser.lookupAll(".scroll-bar");
        for (Node scrollNode : scrolls) {
            if (!ScrollBar.class.isInstance(scrollNode)) {
                continue;
            }
            ScrollBar scroll = (ScrollBar) scrollNode;
            if (scroll.getOrientation() == Orientation.VERTICAL) {
                return scroll;
            }
        }
        return null;
    }

}
