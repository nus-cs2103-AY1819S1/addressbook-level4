package guitests.guihandles;

import org.controlsfx.control.StatusBar;

import javafx.scene.Node;

/**
 * A handle for the {@code StatusBarFooter} at the footer of the application.
 */
public class StatusBarFooterHandle extends NodeHandle<Node> {
    public static final String STATUS_BAR_PLACEHOLDER = "#statusbarPlaceholder";

    private static final String SYNC_STATUS_ID = "#syncStatus";
    private static final String SAVE_LOCATION_STATUS_ID = "#saveLocationStatus";
    private static final String TOTAL_VOLUNTEERS_STATUS_ID = "#totalVolunteersStatus";

    private final StatusBar syncStatusNode;
    private final StatusBar saveLocationNode;
    private final StatusBar totalVolunteersStatusNode;

    private String lastRememberedSyncStatus;
    private String lastRememberedSaveLocation;
    private String lastRememberedTotalVolunteersStatus;

    public StatusBarFooterHandle(Node statusBarFooterNode) {
        super(statusBarFooterNode);

        syncStatusNode = getChildNode(SYNC_STATUS_ID);
        saveLocationNode = getChildNode(SAVE_LOCATION_STATUS_ID);
        totalVolunteersStatusNode = getChildNode(TOTAL_VOLUNTEERS_STATUS_ID);
    }

    /**
     * Returns the text of the sync status portion of the status bar.
     */
    public String getSyncStatus() {
        return syncStatusNode.getText();
    }

    /**
     * Returns the text of the 'save location' portion of the status bar.
     */
    public String getSaveLocation() {
        return saveLocationNode.getText();
    }

    /**
     * Returns the text of the 'total volunteers' portion of the status bar.
     */
    public String getTotalVolunteersStatus() {
        return totalVolunteersStatusNode.getText();
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
     * Remembers the content of the 'save location' portion of the status bar.
     */
    public void rememberSaveLocation() {
        lastRememberedSaveLocation = getSaveLocation();
    }

    /**
     * Returns true if the current content of the 'save location' is different from the value remembered by the most
     * recent {@code rememberSaveLocation()} call.
     */
    public boolean isSaveLocationChanged() {
        return !lastRememberedSaveLocation.equals(getSaveLocation());
    }

    /**
     * Remembers the content of the 'total volunteers' portion of the status bar.
     */
    public void rememberTotalVolunteersStatus() {
        lastRememberedTotalVolunteersStatus = getTotalVolunteersStatus();
    }

    /**
     * Returns true if the current content of the 'total volunteers' is different from the value remembered by the most
     * recent {@code rememberTotalVolunteersStatus()} call.
     */
    public boolean isTotalVolunteersStatusChanged() {
        return !lastRememberedTotalVolunteersStatus.equals(getTotalVolunteersStatus());
    }
}
