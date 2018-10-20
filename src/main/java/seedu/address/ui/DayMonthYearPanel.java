package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.calendarevent.CalendarEvent;

/**
 * Day Month Panel. Displays the month and day in the calendar.
 */
public class DayMonthYearPanel extends UiPart<Region> {

    private static final String FXML = "DayMonthYearPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(DayMonthYearPanel.class);

    private static final String MONTH_YEAR_DISPLAY_FORMAT = "MMMM uuuu";
    private static final String DAY_DISPLAY_FORMAT = "eee, dd";

    private static final DateTimeFormatter MONTH_YEAR_LABEL_FORMATTER =
        DateTimeFormatter.ofPattern(MONTH_YEAR_DISPLAY_FORMAT);
    private static final DateTimeFormatter DAY_LABEL_FORMATTER =
        DateTimeFormatter.ofPattern(DAY_DISPLAY_FORMAT);

    private static final int FILLER_LABEL_FRACTION = 12;
    private static final int DAY_LABEL_FRACTION = 6;
    private static final int NUMBER_OF_DAYS_IN_WEEK = 7;
    private static final double DAY_LABEL_MIN_HEIGHT = 50.0;

    private SimpleObjectProperty<LocalDateTime> startOfWeek;
    private String monthYearLabelText;

    @FXML
    private Label monthYearLabel;

    @FXML
    private AnchorPane monthYearPanel;

    @FXML
    private HBox daysPanel;

    public DayMonthYearPanel(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        renderDayMonthPanel(calendarEventList);
        registerAsAnEventHandler(this);
        calendarEventList.addListener(new ListChangeListener<CalendarEvent>() {
            @Override
            public void onChanged(Change<? extends CalendarEvent> c) {
                if (c.wasUpdated()) {
                    renderDayMonthPanel(calendarEventList);
                }
            }
        });
    }

    private void setMonthYearLabelText(LocalDateTime startOfWeek) {
        monthYearLabel.setText(startOfWeek.format(MONTH_YEAR_LABEL_FORMATTER));
    }

    void setDayLabelTexts(LocalDateTime startOfWeek) {
        for (int i = 0; i < NUMBER_OF_DAYS_IN_WEEK; i++) {
            Label dayLabel = new Label(startOfWeek.plusDays(i).format(DAY_LABEL_FORMATTER));
            dayLabel.prefWidthProperty().bind(daysPanel.widthProperty().divide(DAY_LABEL_FRACTION));
            formatDayLabel(dayLabel);
            daysPanel.getChildren().add(dayLabel);
        }

    }

    private LocalDateTime getStartOfWeek(ObservableList<CalendarEvent> calendarEventList) {
        if (!calendarEventList.isEmpty()) {
            CalendarEvent earliestCalendarEvent = calendarEventList.get(0);
            return earliestCalendarEvent.getStart().localDateTime;
        } else {
            return LocalDateTime.now();
        }
    }

    void addFillerLabel() {
        Label fillerLabel = new Label("");
        fillerLabel.prefWidthProperty().bind(daysPanel.widthProperty().divide(FILLER_LABEL_FRACTION));
        daysPanel.getChildren().add(fillerLabel);
    }

    void renderDayMonthPanel(ObservableList<CalendarEvent> calendarEventList) {
        LocalDateTime startOfWeek = getStartOfWeek(calendarEventList);
        addFillerLabel();
        setMonthYearLabelText(startOfWeek);
        setDayLabelTexts(startOfWeek);
    }

    void formatDayLabel(Label dayLabel) {
        dayLabel.minHeight(DAY_LABEL_MIN_HEIGHT);
        dayLabel.setAlignment(Pos.BOTTOM_RIGHT);
        // optional textAlignment="RIGHT" wrapText="true"
    }
}
