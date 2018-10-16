package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the {@code HelpWindow} of the application.
 */
public class HistoryWindowHandle extends StageHandle {

    public static final String HISTORY_WINDOW_TITLE = "Command History Report";

    private static final String HISTORY_WINDOW_BROWSER_ID = "#commandHistoryWindow";

    public HistoryWindowHandle(Stage historyWindowStage) {
        super(historyWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(HISTORY_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(HISTORY_WINDOW_BROWSER_ID));
    }
}
