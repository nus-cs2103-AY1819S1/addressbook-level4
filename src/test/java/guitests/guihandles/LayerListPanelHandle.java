package guitests.guihandles;

//@@author j-lum
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListView;
/**
 * Provides a handle to {@code LayerListPanel}.
 */
public class LayerListPanelHandle extends NodeHandle<Node> {
    public static final String HISTORY_LIST_VIEW_ID = "#layerListView";
    private final ListView<String> layerListView;

    public LayerListPanelHandle(Node historyListPanel) {
        super(historyListPanel);
        layerListView = getChildNode(HISTORY_LIST_VIEW_ID);
    }

    /**
     * Returns the items in {@code LayerListView}.
     */
    public ObservableList<String> getItems() {
        return layerListView.getItems();
    }

}
