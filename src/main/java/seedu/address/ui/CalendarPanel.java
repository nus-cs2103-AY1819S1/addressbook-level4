package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.event.Event;
import javafx.fxml.FXML;
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
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private ICalendarAgenda iCalendarAgenda;

    public CalendarPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        registerAsAnEventHandler(this);
    }

    /**
     * Frees resources allocated to the iCalendarAgenda.
     */
    public void freeResources() {
        iCalendarAgenda = null;
    }

    public void loadCalendar(Calendar calendar) {
        VCalendar vCalendar = VCalendar.parse(calendar.toString());
        iCalendarAgenda = new ICalendarAgenda(vCalendar);
    }

    @Subscribe
    private void handleCalendarViewEvent(CalendarViewEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadCalendar(event.calendar);
    }
}
