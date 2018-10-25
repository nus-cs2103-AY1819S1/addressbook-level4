package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.events.model.AnalyticsDisplayEvent;

//@@author arsalanc-v2

// hierarchial data structure
// patient, medicine

/**
 * A ui for displaying statistics and visualizations.
 *
 */
public class AnalyticsDisplay extends UiPart<Region> {

    private static final String FXML = "Analytics.fxml";

    @FXML
    private Label summaryBar;
    @FXML
    private Label summaryTextOne;
    @FXML
    private Label summaryValueOne;
    @FXML
    private Label summaryTextTwo;
    @FXML
    private Label summaryValueTwo;
    @FXML
    private Label summaryTextThree;
    @FXML
    private Label summaryValueThree;
    @FXML
    private Label summaryTextFour;
    @FXML
    private Label summaryValueFour;

    @FXML
    private AnchorPane analyticsPane;
    @FXML
    private StackPane browserPlaceholder;

    private Map<Integer, Label> summaryTextLabels;
    private Map<Integer, Label> summaryValueLabels;

    public AnalyticsDisplay() {
        super(FXML);
        analyticsPane.setVisible(false);
        registerAsAnEventHandler(this);

        summaryTextLabels = new HashMap<>() {{
            put(1, summaryTextOne);
            put(2, summaryTextTwo);
            put(3, summaryTextThree);
            put(4, summaryTextFour);
        }};

        summaryValueLabels = new HashMap<>() {{
            put(1, summaryValueOne);
            put(2, summaryValueTwo);
            put(3, summaryValueThree);
            put(4, summaryValueFour);
        }};
    }

    @Subscribe
    public void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        //browserPlaceholder.getChildren().clear();

        //browserPlaceholder.getChildren().add(this.getRoot());
        updateSummary(event.getAllStatistics());
        analyticsPane.setVisible(true);
    }

    /**
     *
     */
    public void updateSummary(Map<String, Map<String, Integer>> allStats) {
        summaryBar.setText(summaryBar.getText() + " appointments per day");

        Map<String, Integer> summaryStats = allStats.get("summary");

        int i = 1;
        for (Map.Entry<String, Integer> stat : summaryStats.entrySet()) {
            System.out.println(stat.getKey() + " " + stat.getValue());
            summaryTextLabels.get(i).setText(stat.getKey());
            summaryValueLabels.get(i).setText(stat.getValue().toString());
            i++;
        }
    }
}
