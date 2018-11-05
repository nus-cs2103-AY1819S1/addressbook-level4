package guitests.guihandles;

//@@author chivent
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
/**
 * Provides a handle to {@code HistoryListPanel}.
 */
public class HistoryListPanelHandle extends NodeHandle<Node> {
    public static final String HISTORY_LIST_VIEW_ID = "#historyListView";
    private final ListView<String> historyListView;

    public HistoryListPanelHandle(Node historyListPanel) {
        super(historyListPanel);
        historyListView = getChildNode(HISTORY_LIST_VIEW_ID);
    }

    /**
     * Returns the items in {@code HistoryListView}.
     */
    public ObservableList<String> getItems() {
        return historyListView.getItems();
    }

}
