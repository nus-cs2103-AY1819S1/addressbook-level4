package seedu.address.ui;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jfxtras.internal.scene.control.skin.agenda.AgendaDaySkin;
import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;

import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.SchedulerChangedEvent;
import seedu.address.commons.events.ui.CalendarDisplayTimeChangedEvent;
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * The Ui component that is responsible for displaying a List of CalendarEvents to the user
 */
public class CalendarDisplay extends UiPart<Region> {
    private static final String FXML = "CalendarDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarDisplay.class);

    private ObservableList<CalendarEvent> calendarEventList;

    private Agenda agenda;
    private Agenda.AppointmentGroupImpl appointmentGroup;

    // keeps track of which week calendar is showing
    private LocalDateTime currentDateTime = LocalDateTime.now()
            .withHour(0).withMinute(0).withSecond(0).withNano(0);

    @FXML
    private VBox calendarDisplayBox;

    public CalendarDisplay(ObservableList<CalendarEvent> calendarEventList) {
        super(FXML);
        // registerAsAnEventHandler(this); // I don't think this is needed as of now

        this.calendarEventList = calendarEventList;
        initAgenda(); // set up agenda internally
        setConnections(calendarEventList);
        setControls();

        agenda.getStylesheets().add("view/ModifiedAgenda.css"); // "src/main/resources/view/
    }

    /**
     * Starts up the internal Agenda Object
     * Prevents the user from interacting directly with the calendar display
     */
    private void initAgenda() {
        agenda = new Agenda();

        appointmentGroup = new Agenda.AppointmentGroupImpl().withStyleClass("group18");

        // register actionCallBack, to be executed when user double click on appointment
        agenda.actionCallbackProperty().set(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment param) {
                // can add more functionality here
                logger.info("User double clicked on " + param.toString());
                return null;
            }
        });

        // disable allowing user to create appointments by clicking on screen
        agenda.setAppointmentChangedCallback(null);

        // disable dragging appointments around
        agenda.setAllowDragging(false);

        agenda.setEditAppointmentCallback(null);

        // set the week for calendar to display
        agenda.setDisplayedLocalDateTime(currentDateTime);

        // show 1 week
        agenda.setSkin(new AgendaWeekSkin(agenda));

        // add agenda to the VBox area
        calendarDisplayBox.getChildren().add(agenda);
    }

    /**
     * Passes agenda the list of CalendarEvents to display
     * @param calendarEventList
     */
    private void setConnections(ObservableList<CalendarEvent> calendarEventList) {
        // populate calendar
        calendarEventList.forEach((calendarEvent -> calendarEvent.setAppointmentGroup(appointmentGroup)));
        agenda.appointments().addAll(calendarEventList);

        // TODO: fix weird add/remove all items bug for first command
        this.calendarEventList.addListener(new ListChangeListener<CalendarEvent>() {
            @Override
            public void onChanged(Change<? extends CalendarEvent> c) {
                while (c.next()) {
                    if (c.wasRemoved()) {
                        for (CalendarEvent removedEvent : c.getRemoved()) {
                            agenda.appointments().remove(removedEvent);
                        }
                    }
                    if (c.wasAdded()) {
                        for (CalendarEvent addedEvent : c.getAddedSubList()) {
                            addedEvent.setAppointmentGroup(appointmentGroup);
                            agenda.appointments().add(addedEvent);
                        }
                    }
                }
            }
        });
    }

    /**
     * Set up the controls for interacting with the calendar display
     * The calendarDisplay must be in focus for this to work
     * TODO: find way to set focus properly
     */
    public void setControls() {
        calendarDisplayBox.addEventFilter(KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                case T: // toggle between day and week view
                    logger.info("Toggle Pressed");
                    toggleSkin();
                    agenda.requestFocus();
                    break;
                case LEFT:
                    logger.info("LEFT arrow Pressed");
                    viewPrevious();
                    indicateCalendarDisplayTimeChanged();
                    break;
                case RIGHT:
                    logger.info("RIGHT arrow Pressed");
                    viewNext();
                    indicateCalendarDisplayTimeChanged();
                    break;
                }
            }
        });
    }

    @FXML
    /**
     * Bug fix: Consumes the DOWN event, preventing strange behaviour
     */
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DOWN) {
            keyEvent.consume();
        }
    }

    /**
     * Raises event to event center of change in display time
     * Depends on the current date, not on the first date displayed in the calendar
     */
    private void indicateCalendarDisplayTimeChanged() {
        raise(new CalendarDisplayTimeChangedEvent(currentDateTime));
    }

    /**
     * Displays the agenda for previous week or day, depending on current skin
     */
    public void viewPrevious() {
        if (isDaySkin()) {
            displayPreviousDay();
        } else if (isWeekSkin()) {
            displayPreviousWeek();
        }
    }

    /**
     * Displays the agenda for next week or day, depending on current skin
     */
    public void viewNext() {
        if (isDaySkin()) {
            displayNextDay();
        } else if (isWeekSkin()) {
            displayNextWeek();
        }
    }

    public void toggleSkin() {
        if (isDaySkin()) {
            setViewToWeeklyView();
        } else if (isWeekSkin()) {
            setViewToDailyView();
        }
    }

    public boolean isDaySkin() {
        return agenda.getSkin() instanceof AgendaDaySkin;
    }

    public boolean isWeekSkin() {
        return agenda.getSkin() instanceof AgendaWeekSkin;
    }

    /**
     * Toggle the view
     */
    public void setViewToWeeklyView() {
        agenda.setSkin(new AgendaWeekSkin(agenda)); // skin for viewing by week
    }

    /**
     * Toggle the view
     */
    public void setViewToDailyView() {
        agenda.setSkin(new AgendaDaySkin(agenda)); // skin for viewing by day
    }
    /**
     * Navigation method
     */
    public void displayNextWeek() {
        currentDateTime = currentDateTime.plusDays(7);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

    /**
     * Navigation method
     */
    public void displayPreviousWeek() {
        currentDateTime = currentDateTime.minusDays(7);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

    /**
     * Navigation method
     */
    public void displayNextDay() {
        currentDateTime = currentDateTime.plusDays(1);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

    /**
     * Navigation method
     */
    public void displayPreviousDay() {
        currentDateTime = currentDateTime.minusDays(1);
        agenda.setDisplayedLocalDateTime(currentDateTime);
    }

}