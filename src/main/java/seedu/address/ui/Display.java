package seedu.address.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.BackToHomeEvent;
import seedu.address.commons.events.ui.DeliveryManPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.OrderPanelSelectionChangedEvent;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.ui.display.DeliverymanDisplayCard;
import seedu.address.ui.display.OrderDisplayCard;


/**
 * Panel containing the main display - map and side content.
 */
public class Display extends UiPart<Region> {

    private static final String FXML = "Display.fxml";
    private final Logger logger = LogsCenter.getLogger(Display.class);

    @FXML
    private StackPane mapWrapper;

    @FXML
    private StackPane displayPanelPlaceholder;

    @FXML
    private StackPane statisticsWrapper;

    private StatisticsPanel statisticsPanel;
    private TreeMap<Date, Integer> orderHistory;
    private HashMap<String, Integer> purchaseHistory;
    private double progress;
    private int total;

    private MapPanel mapPanel;
    private ObservableList<Order> orderList;
    private HashMap<String, Integer> directory;


    /**
     * Constructor for this panel. Process information related to order and updates respective UI components
     *
     * @param orderList the current list of orders in-memory
     */
    public Display(ObservableList<Order> orderList) {
        super(FXML);
        this.orderList = orderList;
        this.progress = 0;
        this.total = orderList.size();

        fillInnerParts();
        setupMap();
        setupStatistics();

        this.orderList.addListener((ListChangeListener.Change<? extends Order> change) -> {
            while (change.next()) {

                if (change.wasUpdated()) {

                    this.progress = 0;
                    this.total = 0;

                    directory = new HashMap<>();
                    updateMapCache(change.getList());

                    orderHistory = new TreeMap<>();
                    updateOrderHistory(change.getList());

                    purchaseHistory = new HashMap<>();
                    addFoodItems(change.getList());

                    trackProgress(change.getList(), false);
                    total = change.getList().size();

                } else {
                    removeFromMapCache(change.getRemoved());
                    updateMapCache(change.getAddedSubList());

                    removeFromOrderHistory(change.getRemoved());
                    updateOrderHistory(change.getAddedSubList());

                    removeFoodItems(change.getRemoved());
                    addFoodItems(change.getAddedSubList());

                    trackProgress(change.getRemoved(), true);
                    trackProgress(change.getAddedSubList(), false);

                    total += change.getAddedSubList().size();
                    total -= change.getRemoved().size();
                }

                mapPanel.clear();
                mapPanel.initialise(directory);

                statisticsPanel.clear();
                statisticsPanel.initialize(orderHistory);
                statisticsPanel.updateLabels(total, progress / total, getTrendingFood());
            }
        });

        registerAsAnEventHandler(this);
    }

    /**
     * Initializes and sets up the variables needed for map component
     */
    private void setupMap() {
        directory = new HashMap<>();
        updateMapCache(orderList);
        mapPanel.initialise(directory);
    }

    /**
     * Initializes and sets up the variables needed for statistics component
     */
    private void setupStatistics() {
        orderHistory = new TreeMap<>();
        purchaseHistory = new HashMap<>();
        this.progress = 0;

        logger.info(progress + "   " + total);

        addFoodItems(orderList);
        trackProgress(orderList, false);
        updateOrderHistory(orderList);

        statisticsPanel.initialize(orderHistory);

        logger.info(progress + "   " + total);

        statisticsPanel.updateLabels(total, progress / total, getTrendingFood());
    }

    /**
     * Fills the panel with the map and statistics
     */
    private void fillInnerParts() {
        mapPanel = new MapPanel();
        mapWrapper.getChildren().add(mapPanel.getRoot());

        statisticsPanel = new StatisticsPanel();
        statisticsWrapper.getChildren().add(statisticsPanel.getRoot());
    }

    /**
     * This updates the progress bar in the statistics panel with % of pending orders
     *
     * @param changeList list of orders that have to be changed
     * @param toRemove a flag to indicate whether to update or remove
     */
    public void trackProgress(List<? extends Order> changeList, boolean toRemove) {
        for (Order o : changeList) {
            if (o.getOrderStatus().toString().equals("PENDING")) {
                if (toRemove) {
                    progress--;
                } else {
                    progress++;
                }
            }
        }
    }

    /**
     * Looks through hashmap to find the most popular food item ordered
     *
     * @return a String that represents the food item ordered the most
     */
    private String getTrendingFood() {
        String bestFood = "";
        int bestVal = -1;

        for (String food : purchaseHistory.keySet()) {
            if (purchaseHistory.get(food) > bestVal) {
                bestVal = purchaseHistory.get(food);
                bestFood = food;
            }
        }

        return bestFood;
    }

    /**
     * Adds food items onto the purchase history hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void addFoodItems(List<? extends Order> changeList) {
        for (Order o : changeList) {
            Set<Food> foodList = o.getFood();
            for (Food item : foodList) {
                String foodKey = item.toString();
                if (purchaseHistory.containsKey(foodKey)) {
                    purchaseHistory.put(foodKey, purchaseHistory.get(foodKey) + 1);
                } else {
                    purchaseHistory.put(foodKey, 1);
                }
            }
        }
    }

    /**
     * Removes food items from the purchase history hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void removeFoodItems(List<? extends Order> changeList) {
        for (Order o : changeList) {
            Set<Food> foodList = o.getFood();
            for (Food item : foodList) {
                String foodKey = item.toString();
                if (purchaseHistory.containsKey(foodKey)) {
                    if (purchaseHistory.get(foodKey) <= 1) {
                        purchaseHistory.remove(foodKey);
                    } else {
                        purchaseHistory.put(foodKey, purchaseHistory.get(foodKey) - 1);
                    }
                }
            }
        }
    }

    /**
     * Removes addresses from the directory hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void removeFromMapCache(List<? extends Order> changeList) {
        for (Order o : changeList) {
            if (o.getOrderStatus().toString().equals("PENDING")) {
                String postalCode = o.getAddress().getPostalCode();
                String postalCodeKey = postalCode.substring(0, 2);
                if (directory.containsKey(postalCodeKey)) {
                    if (directory.get(postalCodeKey) <= 1) {
                        directory.remove(postalCodeKey);
                    } else {
                        directory.put(postalCodeKey, directory.get(postalCodeKey) - 1);
                    }
                }
            }
        }
    }

    /**
     * Adds addresses onto the directory hashmap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void updateMapCache(List<? extends Order> changeList) {
        for (Order o : changeList) {
            if (o.getOrderStatus().toString().equals("PENDING")) {
                String postalCode = o.getAddress().getPostalCode();
                String postalCodeKey = postalCode.substring(0, 2);
                if (directory.containsKey(postalCodeKey)) {
                    directory.put(postalCodeKey, directory.get(postalCodeKey) + 1);
                } else {
                    directory.put(postalCodeKey, 1);
                }
            }
        }
    }

    /**
     * Adds orders onto the orderHistory treemap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void updateOrderHistory(List<? extends Order> changeList) {
        for (Order o : changeList) {
            Date dateKey = o.getDate().getShortenedDate();
            logger.info("ADDED " + o.toString());
            if (orderHistory.containsKey(dateKey)) {
                orderHistory.put(dateKey, orderHistory.get(dateKey) + 1);
            } else {
                orderHistory.put(dateKey, 1);
            }
        }

        logger.info(orderHistory.toString());
    }

    /**
     * Removes orders from the orderHistory treemap once there is a change
     *
     * @param changeList orders that were changed
     */
    private void removeFromOrderHistory(List<? extends Order> changeList) {
        for (Order o : changeList) {
            Date dateKey = o.getDate().getShortenedDate();
            logger.info("REMOVED " + o.toString());
            if (orderHistory.containsKey(dateKey)) {
                if (orderHistory.get(dateKey) <= 1) {
                    orderHistory.remove(dateKey);
                } else {
                    orderHistory.put(dateKey, orderHistory.get(dateKey) - 1);
                }
            }
        }

        logger.info(orderHistory.toString());
    }

    @Subscribe
    public void handleBackToHomeRequest(BackToHomeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));

        statisticsPanel = new StatisticsPanel();
        displayPanelPlaceholder.getChildren().setAll(statisticsPanel.getRoot());

        setupStatistics();
    }

    @Subscribe
    public void handleOrderPanelSelectionChangedEvent(OrderPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPanelPlaceholder.getChildren().setAll(new OrderDisplayCard(event.getNewSelection()).getRoot());
    }

    @Subscribe
    public void handleDeliveryPanelSelectionChangedEvent(DeliveryManPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        displayPanelPlaceholder.getChildren().setAll(new DeliverymanDisplayCard(event.getNewSelection()).getRoot());
    }
}
