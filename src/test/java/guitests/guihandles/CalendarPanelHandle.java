package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import jfxtras.icalendarfx.VCalendar;
import jfxtras.scene.control.agenda.icalendar.ICalendarAgenda;

//@@author GilgameshTC
/**
 * A handler for the {@code CalendarPanel} of the UI.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {
    public static final String BORDER_ID = "#borderPane";

    private VCalendar lastRememberedCalendar;

    private BorderPane borderPane;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);

        borderPane = getChildNode(BORDER_ID);
    }

    /**
     * Returns the {@code VCalendar} of the currently loaded calendar.
     */
    public VCalendar getLoadedCalendar() {
        return ((ICalendarAgenda) borderPane.getCenter()).getVCalendar();
    }

    /**
     * Remembers the {@code VCalendar} of the currently loaded page.
     */
    public void rememberCalendar() {
        lastRememberedCalendar = getLoadedCalendar();
    }

    /**
     * Returns true if the current {@code VCalendar} is different from the value remembered by the most recent
     * {@code rememberCalendar()} call.
     */
    public boolean isCalendarChanged() {
        return !lastRememberedCalendar.equals(getLoadedCalendar());
    }

}
