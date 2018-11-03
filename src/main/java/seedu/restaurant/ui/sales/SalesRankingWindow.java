package seedu.restaurant.ui.sales;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import seedu.restaurant.commons.core.LogsCenter;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;
import seedu.restaurant.model.sales.SalesReport;
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
    private ListView<String> dateOrName;
    @FXML
    private ListView<String> revenue;
    @FXML
    private Label totalRevenue;

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
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
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
        dateOrName.setItems(FXCollections.observableArrayList(salesRanking.keySet()));
        revenue.setItems(FXCollections.observableArrayList(salesRanking.values()));
    }

    /**
     * Returns true if the SalesRankingWindow is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }
}
