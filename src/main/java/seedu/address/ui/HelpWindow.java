package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_FILE_PATH = "/docs/HelpWindow.html";
    public static final String SHORT_HELP_FILE_PATH = "/docs/ShortHelpWindow.html";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    //adapted from https://stackoverflow.com/questions/2238938/how-to-programmatically-scroll-android-webview
    //https://stackoverflow.com/questions/6991494/javascript-getelementbyid-base-on-partial-string
    //https://stackoverflow.com/questions/22778241/javafx-webview-scroll-to-desired-position
    //javascript to scroll webpage to commandWord
    private static final String scrollJavaScript =
            "function scrollToElement(commandWord) {\n" +
            "    var elem = document.querySelector('[id$=\"code-\" + commandWord + \"-code\"]');\n" +
            "    alert(\"X\");" +
            "    var x = 0;\n" +
            "    var y = 0;\n" +
            "\n" +
            "    while (elem != null) {\n" +
            "        x += elem.offsetLeft;\n" +
            "        y += elem.offsetTop;\n" +
            "        elem = elem.offsetParent;\n" +
            "    }\n" +
            "    window.scrollTo(x, y);\n" +
            "}";

    @FXML
    private WebView browser;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);

        String userGuideUrl = getClass().getResource(USERGUIDE_FILE_PATH).toString();
        browser.getEngine().load(userGuideUrl);
        browser.getEngine().executeScript(scrollJavaScript);
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
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
     * Scrolls the help window to the specified commandWord
     * @param commandWord
     */
    public void scrollTo(String commandWord) {
        browser.getEngine().load("javascript:scrollToElement('" + commandWord + "')");
    }


}
