package seedu.address.ui;

import java.util.Arrays;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.events.ui.AnalyticsDisplayEvent;
import seedu.clinicio.model.analytics.StatData;
import seedu.clinicio.model.analytics.data.Tuple;

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
// charts ui adjustment
// 1d 1w 1m 1y
// consultation tests
// 0 data points tests

/**
 * A ui for displaying statistics and visualizations.
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
    private AnchorPane visualizationPane;
    @FXML
    private AnchorPane chartPane;

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
        StatData allDataToDisplay = event.getAllData();
        chartPane.getChildren().clear();
       Plot.updateVisualization(allDataToDisplay.getVisualizationData(), chartPane);
       Plot.fillSummary(allDataToDisplay.getSummaryData(), summaryBar, summaryLabels);
        analyticsPane.setVisible(true);
    }
}
