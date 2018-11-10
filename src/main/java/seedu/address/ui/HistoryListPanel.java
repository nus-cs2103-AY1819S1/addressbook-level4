package seedu.address.ui;

//@@author chivent
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
import seedu.address.commons.events.ui.HistoryUpdateEvent;


/**
 * Panel containing the list of past transformations.
 */
public class HistoryListPanel extends UiPart<Region> {
    public static final String SELECTED_STYLE = "-fx-background-color: #a3a3a3;  -fx-text-fill: #1f1f1f;";

    private static final String FXML = "HistoryListPanel.fxml";
    private static final String DEFAULT_STYLE = "-fx-background-color: transparent;  -fx-text-fill: #6e6e6e;";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private ObservableList<String> items = FXCollections.observableArrayList();

    /**
     * Stores transformations that have been undone.
     */
    //private Queue<String> redoQueue = new LinkedList<>();

    @FXML
    private ListView<String> historyListView;

    @FXML
    private Text historyTitle;

    public HistoryListPanel() {
        super(FXML);
        historyTitle.setText("Transformation History");
        historyListView.setItems(items);
        historyListView.setCellFactory(listView -> new HistoryListPanel.HistoryListViewCell());
        registerAsAnEventHandler(this);
    }

    @Subscribe
    private void handleHistoryUpdateEvent(HistoryUpdateEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        for (int size = items.size(); 0 < size; size--) {
            items.remove(items.size() - 1, items.size());
        }
        historyListView.setItems(items);
        items.addAll(event.list);
        historyListView.setItems(items);
    }

    /**
     * Custom {@code ListCell} that displays transformations.
     */
    class HistoryListViewCell extends ListCell<String> {
        @Override
        protected void updateItem(String transformation, boolean empty) {
            super.updateItem(transformation, empty);

            setStyle(DEFAULT_STYLE);
            setGraphic(null);
            setText(empty ? null : transformation);

            if (!empty && getIndex() == (getListView().getItems().size() - 1)) {
                setStyle(SELECTED_STYLE);
            }
        }
    }
}

