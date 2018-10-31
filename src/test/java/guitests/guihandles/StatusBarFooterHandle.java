package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String LOGIN_STATUS_ID = "#loginStatus";
    private static final String DIRECTORY_DISPLAY_ID = "#directoryDisplay";

    private final StatusBar loginStatusNode;
    private final StatusBar directoryNode;

    private String lastRememberedLoginStatus;
    private String lastRememberedDirectoryDisplay;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        loginStatusNode = getChildNode(LOGIN_STATUS_ID);
        directoryNode = getChildNode(DIRECTORY_DISPLAY_ID);
    }

    /**
     * Returns the text of the login status portion of the status bar.
     */
    public String getLoginStatus() {
        return loginStatusNode.getText();
    }

    /**
     * Returns the text of the directory display portion of the status bar.
     */
    public String getDirectoryDisplay() {
        return directoryNode.getText();
    }

    /**
     * Remembers the content of the login status portion of the status bar.
     */
    public void rememberLoginStatus() {
        lastRememberedLoginStatus = getLoginStatus();
    }

    /**
     * Remembers the content of the directory display portion of the status bar.
     */
    public void rememberDirectoryDisplay() {
        lastRememberedDirectoryDisplay = getDirectoryDisplay();
    }

    /**
     * Returns true if the current content of the login status is different from the value remembered by the most recent
     * {@code rememberLoginStatus()} call.
     */
    public boolean isLoginStatusChanged() {
        return !lastRememberedLoginStatus.equals(getLoginStatus());
    }

    /**
     * Returns true if the current content of the directory display is different from the value remembered by the most recent
     * {@code rememberDirectoryStatus()} call.
     */
    public boolean isDirectoryChanged() {
        return !lastRememberedDirectoryDisplay.equals(getLoginStatus());
    }
}
