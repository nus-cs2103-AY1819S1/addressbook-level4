package seedu.address.ui;

import java.util.Arrays;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.util.StringConverter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.OverviewPanelChangedEvent;
import seedu.address.model.Overview;
import seedu.address.model.event.Event;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Panel containing the statistics overview.
 */
public class OverviewPanel extends UiPart<Region> {
    private static final String FXML = "OverviewPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label upcomingLabel;
    @FXML
    private Label ongoingLabel;
    @FXML
    private Label completedLabel;
    @FXML
    private PieChart genderPieChart;
    @FXML
    private BarChart ageBarChart;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;

    private ObservableList<Volunteer> volunteerList;
    private ObservableList<Event> eventList;
    private ObservableList<Record> recordList;
    private Overview overview;

    public OverviewPanel(ObservableList<Volunteer> volunteerList, ObservableList<Event> eventList,
                         ObservableList<Record> recordList) {
        super(FXML);

        this.volunteerList = volunteerList;
        this.eventList = eventList;
        this.recordList = recordList;
        this.overview = new Overview(volunteerList, eventList, recordList);

        registerAsAnEventHandler(this);
    }

    private void setLabelText() {
        upcomingLabel.setText(Integer.toString(overview.getNumOfUpcomingEvents()));
        ongoingLabel.setText(Integer.toString(overview.getNumOfOngoingEvents()));
        completedLabel.setText(Integer.toString(overview.getNumOfCompletedEvents()));
    }

    /**
     * This method creates a pie chart which shows the gender ratio.
     */
    private void createGenderPieChart() {
        String male = "Male (" + overview.getNumOfMale() + ")";
        String female = "Female (" + overview.getNumOfFemale() + ")";

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data(male, overview.getNumOfMale()),
                new PieChart.Data(female, overview.getNumOfFemale()));

        genderPieChart.getData().clear();
        genderPieChart.setData(pieChartData);
        genderPieChart.setLabelsVisible(true);
        genderPieChart.setLegendVisible(false);
    }

    /**
     * This method creates a bar chart which shows the age distribution.
     */
    private void createAgeBarChart() {
        xAxis.setCategories(FXCollections
                .observableArrayList(Arrays.asList("14 and Below", "15 to 24", "24 to 64", "65 and Above")));
        yAxis.setLabel("Number");
        yAxis.setMinorTickVisible(false);
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if (object.intValue() != object.doubleValue())
                    return "";
                return "" + (object.intValue());
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });


        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("14 and Below", overview.getNumOfChildren()));
        series.getData().add(new XYChart.Data<>("15 to 24", overview.getNumOfYouth()));
        series.getData().add(new XYChart.Data<>("24 to 64", overview.getNumOfAdult()));
        series.getData().add(new XYChart.Data<>("65 and Above", overview.getNumOfSenior()));

        ageBarChart.getData().clear();
        ageBarChart.getData().add(series);
        ageBarChart.setLegendVisible(false);
    }

    @Subscribe
    private void handleOverviewPanelSelectionChangedEvent(OverviewPanelChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setLabelText();

        overview.calculateNumOfEvents();
        overview.calculateVolunteerDemographics();

        createGenderPieChart();
        createAgeBarChart();
    }
}
