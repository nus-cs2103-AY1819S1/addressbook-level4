package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.events.model.AnalyticsDisplayEvent;
import seedu.address.model.analytics.StatData;
import seedu.address.model.analytics.data.Tuple;

//@@author arsalanc-v2

// hierarchial data structure  {summaryLabel, list(Tuple)}

//      root
//     /    \
// summary  visualizations
//     /               \
// summaryLabel        {chart title, xtitle, ytitle, xlabels, ylabels, (x, y) values}
//      /
// {key, value}

// patient, medicine

/**
 * A ui for displaying statistics and visualizations.
 */
public class AnalyticsDisplay extends UiPart<Region> {

    private static final String FXML = "Analytics.fxml";
    private static final int NUM_SUMMARY_ELEMENTS = 4;
    private static final int NUM_VISUALIZATION_ELEMENTS = 6;

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



    private List<Tuple<Label, Label>> summaryLabels;

    public AnalyticsDisplay() {
        super(FXML);
        analyticsPane.setVisible(false);
        registerAsAnEventHandler(this);

        summaryLabels = Arrays.asList(new Tuple(summaryTextOne, summaryValueOne), new Tuple(summaryTextTwo,
            summaryValueTwo), new Tuple(summaryTextThree, summaryValueThree), new Tuple(summaryTextFour,
            summaryValueFour));
    }

    @Subscribe
    public void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        Map<String, List> allDataToDisplay = event.getAllData();
        updateSummary(allDataToDisplay.get("summary"));
        updateVisualization(allDataToDisplay.get("visualization"));
        analyticsPane.setVisible(true);
    }

    /**
     *
     * @param visualizationData
     */
    public void updateVisualization(List<StatData.VisualizationData> visualizationData) {

    }

    /**
     *
     */
    public void updateSummary(List<Tuple<String, Integer>> summaryData) {
        summaryBar.setText("Number of appointments per day");

        int i = 0;
        for (Tuple summaryElement : summaryData) {
            // set text for summary text label
            summaryLabels.get(i).getKey().setText(summaryElement.getKey().toString());
            // set text for summary value label
            summaryLabels.get(i).getValue().setText(summaryElement.getValue().toString());
            i++;
        }
    }
}
