package seedu.address.ui;

//@@author j-lum

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.LayerUpdateEvent;


/**
 * Panel containing the list of past transformations.
 */
public class LayerListPanel extends UiPart<Region> {
    public static final String SELECTED_STYLE = "-fx-background-color: #a3a3a3;  -fx-text-fill: #1f1f1f;";

    private static final String FXML = "LayerListPanel.fxml";
    private static final String DEFAULT_STYLE = "-fx-background-color: transparent;  -fx-text-fill: #6e6e6e;";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private ObservableList<String> items = FXCollections.observableArrayList();

    @FXML
    private ListView<String> layerListView;

    @FXML
    private Text layerTitle;

    private Index current = Index.fromZeroBased(0);

    public LayerListPanel() {
        super(FXML);
        layerTitle.setText("Layers");
        layerListView.setItems(items);
        layerListView.setCellFactory(listView -> new LayerListPanel.LayerListViewCell());
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleLayerUpdateEvent(LayerUpdateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        this.current = event.current;
        for (int size = items.size(); 0 < size; size--) {
            items.remove(items.size() - 1, items.size());
        }
        layerListView.setItems(items);
        items.addAll(event.list);
        layerListView.setItems(items);
    }

    /**
     * Custom {@code ListCell} that displays transformations.
     */
    class LayerListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String layerName, boolean empty) {
            super.updateItem(layerName, empty);

            setStyle(DEFAULT_STYLE);
            setGraphic(null);
            setText(empty ? null : layerName);

            if (!empty && getIndex() == current.getZeroBased()) {
                setStyle(SELECTED_STYLE);
            }
        }
    }
}

