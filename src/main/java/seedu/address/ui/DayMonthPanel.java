package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendarevent.CalendarEvent;
import seedu.address.model.calendarevent.DateTime;

/**
 * Day Month Panel. Displays the month and day in the calendar.
 */
public class DayMonthPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(DayMonthPanel.class);
    private static final String FXML = "DayMonthPanel.fxml";
    private static final String MONTH_YEAR_FORMAT = "MMMM uuuu";
    private static final String DAY_FORMAT = "eee, dd";
    private SimpleObjectProperty<LocalDateTime> startDateTime;
    private StringProperty monthLabelText;
    private List<SimpleStringProperty> daysLabelText;
    private CalendarEvent calendarEvent;

    @FXML
    private Label monthLabel;

    @FXML
    private AnchorPane monthPanel;

    @FXML
    private HBox daysPanel;


    // TODO refactor
    // TODO clean magic numbers
    public DayMonthPanel(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        startDateTime = new SimpleObjectProperty<>(LocalDateTime.now());
        DateTimeFormatter monthLabelFormatter = DateTimeFormatter.ofPattern(MONTH_YEAR_FORMAT);
        monthLabelText= new SimpleStringProperty(LocalDateTime.now().format(monthLabelFormatter));
        monthLabel.textProperty().bind(monthLabelText);
        addLabels();
        calendarEventList.addListener(new ListChangeListener<CalendarEvent>() {
            @Override
            public void onChanged(Change<? extends CalendarEvent> c) {
                if (c.wasUpdated()) {
                    if (!calendarEventList.isEmpty()) {
                        calendarEvent = calendarEventList.get(0);
                        startDateTime.set(calendarEvent.getStart().localDateTime);
                        monthLabelText.set(startDateTime.getValue().format(monthLabelFormatter));
                    }
                }
            }
        });
        registerAsAnEventHandler(this);
    }

    // TODO refactor
    // TODO clean up magic numbers
    void addLabels() {
        daysLabelText = new ArrayList<>();
        DateTimeFormatter dayLabelFormatter = DateTimeFormatter.ofPattern(DAY_FORMAT);
        LocalDateTime currentDateTime = startDateTime.getValue();
        DayLabel fillerLabel = new DayLabel("");
        fillerLabel.getRoot().prefWidthProperty().bind(daysPanel.widthProperty().divide(12)); // binding will be
        // useful for the days columns too
        daysPanel.getChildren().add(fillerLabel.getRoot());
        for (int i = 0; i < 7; i++) {
            daysLabelText.add(new SimpleStringProperty(currentDateTime.plusDays(i).format(dayLabelFormatter)));
            DayLabel dayLabel = new DayLabel(daysLabelText.get(i));
            dayLabel.getRoot().prefWidthProperty().bind(daysPanel.widthProperty().divide(6));
            daysPanel.getChildren().add(dayLabel.getRoot());
        }

    }
}
