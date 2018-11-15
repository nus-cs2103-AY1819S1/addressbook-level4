package guitests.guihandles;

import java.net.URL;

import guitests.GuiRobot;
import javafx.stage.Stage;

/**
 * A handle to the DocumentWindow of the Application.
 */
public class DocumentWindowHandle extends StageHandle {

    public static final String DOCUMENT_WINDOW_TITLE = "Document";

    private static final String DOCUMENT_WINDOW_BROWSER_ID = "#browser";

    public DocumentWindowHandle(Stage documentWindowStage) {
        super(documentWindowStage);
    }

    /**
     * Returns true if a help window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(DOCUMENT_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(DOCUMENT_WINDOW_BROWSER_ID));
    }
}
