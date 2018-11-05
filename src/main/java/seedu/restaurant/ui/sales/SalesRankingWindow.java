package seedu.restaurant.ui.sales;

import java.util.Map;
import java.util.logging.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.ui.UiPart;

/**
 * Controller for a sales ranking window. There are two types of ranking. 1) Rank Date by revenue. 2) Rank Item by
 * revenue.
 */
public class SalesRankingWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(SalesRankingWindow.class);
    private static final String FXML = "SalesRankingWindow.fxml";

    private Map<String, String> salesRanking;

    @FXML
    private TableView<Map.Entry<String, String>> rankingTable;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> rank;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> dateOrItem;
    @FXML
    private TableColumn<Map.Entry<String, String>, String> revenue;

    /**
     * Creates a new SalesRankingWindow
     *
     * @param root Stage to use as the root of the SalesRankingWindow.
     * @param salesRanking the ranking to display in this window represented by a Map.
     */
    public SalesRankingWindow(Stage root, Map<String, String> salesRanking) {
        super(FXML, root);
        root.setTitle("Sales Ranking");
        this.salesRanking = salesRanking;
    }

    /**
     * Creates a new SalesRankingWindow.
     */
    public SalesRankingWindow(Map<String, String> salesRanking) {
        this(new Stage(), salesRanking);
    }

    /**
     * Shows the SalesRankingWindow.
     */
    public void show() {
        logger.fine("Displaying Sales Ranking.");
        initialize();
        getRoot().show();
    }

    /**
     * Initializes the SalesRankingWindow according to the salesRanking map.
     */
    private void initialize() {
        ObservableList<Map.Entry<String, String>> salesRankingList =
                FXCollections.observableArrayList(salesRanking.entrySet());
        rankingTable.setItems(salesRankingList);
        rank.setCellValueFactory(e -> new SimpleStringProperty(
                String.valueOf(Index.fromZeroBased(salesRankingList.indexOf(e.getValue())).getOneBased())));
        dateOrItem.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getKey()));
        revenue.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().getValue()));
    }

    /**
     * Returns true if the SalesRankingWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
