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
import seedu.address.model.calendarevent.CalendarEvent;


/**
 * The Ui component that is responsible for displaying a List of CalendarEvents to the user
 */
public class CalendarDisplay extends UiPart<Region> {
    private static final String FXML = "CalendarDisplay.fxml";
    private final Logger logger = LogsCenter.getLogger(CalendarDisplay.class);

    private ObservableList<CalendarEvent> calendarEventList;

    private Agenda agenda;

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
        // populate the calendar by adding all the events to the calendar
        agenda.appointments().addAll(calendarEventList);

        // TODO: fix weird add/remove all items bug for first command
        this.calendarEventList.addListener(new ListChangeListener<CalendarEvent>() {
            @Override
            public void onChanged(Change<? extends CalendarEvent> c) {
                while (c.next()) {
                    if (c.wasRemoved()) {
                        for (CalendarEvent removedEvent : c.getRemoved()) {
                            agenda.appointments().remove(removedEvent);
                            System.out.println("REMOVED: " + removedEvent.getTitle());
                        }
                    }
                    if (c.wasAdded()) {
                        for (CalendarEvent addedEvent : c.getAddedSubList()) {
                            agenda.appointments().add(addedEvent);
                            System.out.println("ADDED: " + addedEvent.getTitle());
                        }
                    }
                }
            }
        });
    }

    /**
     * Temporary convienience method to test navigation
     * The calendarDisplay must be in focus for this to work, i.e. must click on the calendar
     * TODO: get help with the down key problem
     */
    public void setControls() {
        agenda.addEventFilter(KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.T) { // toggle between day and week view
                    logger.info("Toggle Pressed");
                    if (agenda.getSkin() instanceof AgendaDaySkin) {
                        setViewToWeeklyView();
                    } else if (agenda.getSkin() instanceof AgendaWeekSkin) {
                        setViewToDailyView();
                    }
                    agenda.requestFocus();
                } else if (event.getCode() == KeyCode.LEFT) {
                    logger.info("LEFT arrow Pressed");
                    if (agenda.getSkin() instanceof AgendaDaySkin) {
                        displayPreviousDay();
                    } else if (agenda.getSkin() instanceof AgendaWeekSkin) {
                        displayPreviousWeek();
                    }
                } else if (event.getCode() == KeyCode.RIGHT) {
                    logger.info("RIGHT arrow Pressed");
                    if (agenda.getSkin() instanceof AgendaDaySkin) {
                        displayNextDay();
                    } else if (agenda.getSkin() instanceof AgendaWeekSkin) {
                        displayNextWeek();
                    }
                }
            }
        });
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