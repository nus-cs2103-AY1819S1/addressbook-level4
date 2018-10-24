package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String LOGIN_STATUS_ID = "#loginStatus";

    private final StatusBar loginStatusNode;

    private String lastRememberedLoginStatus;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        loginStatusNode = getChildNode(LOGIN_STATUS_ID);
    }

    /**
     * Returns the text of the login status portion of the status bar.
     */
    public String getLoginStatus() {
        return loginStatusNode.getText();
    }

    /**
     * Remembers the content of the login status portion of the status bar.
     */
    public void rememberLoginStatus() {
        lastRememberedLoginStatus = getLoginStatus();
    }

    /**
     * Returns true if the current content of the login status is different from the value remembered by the most recent
     * {@code rememberLoginStatus()} call.
     */
    public boolean isLoginStatusChanged() {
        return !lastRememberedLoginStatus.equals(getLoginStatus());
    }
}
