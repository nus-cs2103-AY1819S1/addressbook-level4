package seedu.clinicio.ui.analytics;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.events.ui.AnalyticsDisplayEvent;
import seedu.clinicio.model.analytics.ChartType;
import seedu.clinicio.model.analytics.data.CircularList;
import seedu.clinicio.model.analytics.data.StatData;
import seedu.clinicio.model.analytics.data.Tuple;
import seedu.clinicio.model.analytics.data.VisualizationData;

import seedu.clinicio.ui.UiPart;

//@@author arsalanc-v2

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
    private StatData allDataToDisplay;

    public AnalyticsDisplay() {
        super(FXML);
        analyticsPane.setVisible(false);
        registerAsAnEventHandler(this);

        summaryLabels = Arrays.asList(
            new Tuple(summaryTextOne, summaryValueOne),
            new Tuple(summaryTextTwo, summaryValueTwo),
            new Tuple(summaryTextThree, summaryValueThree),
            new Tuple(summaryTextFour,
            summaryValueFour)
        );
    }

    public void setEventListeners() {
        analyticsPane.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                analyticsPane.requestFocus();
            }
        });

        analyticsPane.addEventFilter(KeyEvent.KEY_PRESSED,
            event -> {
                if (event.getCode().equals(KeyCode.RIGHT)) {
                    handleRight();
                }
            });
    }

    /**
     *
     */
    public void handleRight() {
        chartPane.getChildren().clear();
        Plot.plotChart(allDataToDisplay.getVisualizationData().getNext(), chartPane);
        analyticsPane.requestFocus();
        System.out.println("rightr");

    }

    public void requestFocus() {
        analyticsPane.requestFocus();
    }

    @Subscribe
    public void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        setEventListeners();
        allDataToDisplay = event.getAllData();
        chartPane.getChildren().clear();
        chartPane.setStyle("-fx-background-color: #6593F5");
        CircularList<VisualizationData> allVisualizationData = allDataToDisplay.getVisualizationData();
        //allVisualizationData.setIterator();
        Plot.plotChart(allVisualizationData.getNext(), chartPane);
        Plot.fillSummary(allDataToDisplay.getSummaryData(), summaryBar, summaryLabels);
        analyticsPane.setVisible(true);
        analyticsPane.requestFocus();
    }

    public boolean isVisible() {
        return analyticsPane.isVisible();
    }

    public void setVisible(boolean visibility) {
        analyticsPane.setVisible(visibility);
    }

    @Subscribe
    public void handleAnalyticsDisplayEvent(AnalyticsDisplayEvent event) {
        StatData allDataToDisplay = event.getAllData();
        chartPane.getChildren().clear();
        chartPane.setStyle("-fx-background-color: #6593F5");
        Plot.updateVisualization(allDataToDisplay.getVisualizationData(), chartPane);
        Plot.fillSummary(allDataToDisplay.getSummaryData(), summaryBar, summaryLabels);
        analyticsPane.setVisible(true);
    }
}
