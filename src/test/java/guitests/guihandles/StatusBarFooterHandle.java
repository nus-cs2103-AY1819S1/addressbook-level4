package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String USER_SESSION_STATUS_ID = "#userSessionStatus";

    private final StatusBar syncStatusNode;
    private final StatusBar userSessionNode;

    private String lastRememberedSyncStatus;
    private String lastRememberedUserSession;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        syncStatusNode = getChildNode(SYNC_STATUS_ID);
        userSessionNode = getChildNode(USER_SESSION_STATUS_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'user session' portion of the status bar.
     */
    public String getUserSession() {
        return userSessionNode.getText();
    }

    /**
     * Remembers the content of the sync status portion of the status bar.
     */
    public void rememberSyncStatus() {
        lastRememberedSyncStatus = getSyncStatus();
    }

    /**
     * Returns true if the current content of the sync status is different from the value remembered by the most recent
     * {@code rememberSyncStatus()} call.
     */
    public boolean isSyncStatusChanged() {
        return !lastRememberedSyncStatus.equals(getSyncStatus());
    }

    /**
     * Remembers the content of the 'user session' portion of the status bar.
     */
    public void rememberUserSession() {
        lastRememberedUserSession = getUserSession();
    }

    /**
     * Returns true if the current content of the 'user session' is different from the value remembered by the most
     * recent {@code rememberUserSessionUserSession()} call.
     */
    public boolean isUserSessionChanged() {
        return !lastRememberedUserSession.equals(getUserSession());
    }
}
