package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.CalendarViewEvent;

//@@author GilgameshTC

/**
 * The Browser Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private static final String FXML = "CalendarPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private BorderPane borderPane;

    public CalendarPanel() {
        super(FXML);

        // To prevent triggering events for typing and dragging inside the loaded Calendar.
        getRoot().setOnKeyPressed(Event::consume);
        loadDefaultCalendar();
        registerAsAnEventHandler(this);
    }

    /** Method to load a default calendar into the border pane. */
    private void loadDefaultCalendar() {
        ICalendarAgenda iCalendarAgenda = new ICalendarAgenda();
        borderPane.setCenter(iCalendarAgenda);

        // block events on calendar
        EventHandler<MouseEvent> handler = MouseEvent::consume;
        borderPane.getCenter().addEventFilter(MouseEvent.ANY, handler);
        setUpBorderPane(iCalendarAgenda);
    }

    /** Sets up the border pane with button and styling. */
    private void setUpBorderPane(ICalendarAgenda agenda) {
        // Set the border pane background to white
        String style = "-fx-background-color: rgba(255, 255, 255, 1);";
        borderPane.setStyle(style);

        // Buttons
        Button increaseWeek = new Button(">");
        Button decreaseWeek = new Button("<");
        HBox weekButtonHBox = new HBox(decreaseWeek, increaseWeek);
        weekButtonHBox.setSpacing(10);
        borderPane.setBottom(weekButtonHBox);

        // Weekly increase/decrease event handlers
        increaseWeek.setOnAction(event -> {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().plus(Period.ofWeeks(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });

        decreaseWeek.setOnAction(event -> {
            LocalDateTime newDisplayedLocalDateTime = agenda.getDisplayedLocalDateTime().minus(Period.ofWeeks(1));
            agenda.setDisplayedLocalDateTime(newDisplayedLocalDateTime);
        });
    }

    /**
     * Frees resources allocated to the iCalendarAgenda.
     */
    public void freeResources() {
        borderPane = null;
    }

    /**
     * Loads the calendar requested by the user onto the UI.
     */
    public void loadCalendar(Calendar calendar) {
        String content = calendar.toString();
        if (!isWindowsOs()) {
            content = content.replace("\r\n", System.getProperty("line.separator"));
        }

        VCalendar vCalendar = VCalendar.parse(content);
        ICalendarAgenda iCalendarAgenda = new ICalendarAgenda(vCalendar);
        borderPane.setCenter(iCalendarAgenda);

        // block events on calendar
        EventHandler<MouseEvent> handler = MouseEvent::consume;
        borderPane.getCenter().addEventFilter(MouseEvent.ANY, handler);
        setUpBorderPane(iCalendarAgenda);

    }

    /**
     * Checks if the operating system is windows.
     */
    private boolean isWindowsOs() {
        String osName = System.getProperty("os.name");
        if (osName == null) {
            return false;
        }
        osName = osName.toLowerCase(Locale.ENGLISH);
        return osName.contains("windows");
    }

    @Subscribe
    private void handleCalendarViewEvent(CalendarViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCalendar(event.calendar);
    }

}
