package seedu.scheduler.model;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEAR_ONE;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEAR_THREE;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEAR_TWO;
import static seedu.scheduler.testutil.TypicalEvents.STARTUP_LECTURE_MONTH_ONE;
import static seedu.scheduler.testutil.TypicalEvents.getBirthdayAllList;
import static seedu.scheduler.testutil.TypicalEvents.getSchedulerBirthday;
import static seedu.scheduler.testutil.TypicalEvents.getSchedulerStudyWithJane;
import static seedu.scheduler.testutil.TypicalEvents.getStudyWithJaneAllList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.scheduler.model.event.Event;
import seedu.scheduler.testutil.EventBuilder;

public class PopUpManagerTest {
    private PopUpManager popUpManager;
    private Model model1 = new ModelManager(getSchedulerBirthday(), new UserPrefs()); //All future
    private Model model2 = new ModelManager(getSchedulerStudyWithJane(), new UserPrefs()); //All past

    @Before
    public void setUp() {
        initialisePopUpManager();
    }

    @Test
    public void syncPopUpInfo() {
        popUpManager.reInitialise(model2.getScheduler());
        assertEquals(popUpManager.size(), 0);
        popUpManager.syncPopUpInfo(model1.getScheduler());
        assertEquals(popUpManager.size(), 6);
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
    }

    @Test
    public void reInitialise() {
        popUpManager.reInitialise(model2.getScheduler());
        assertEquals(popUpManager.size(), 0);
        popUpManager.reInitialise(model1.getScheduler());
        assertEquals(popUpManager.size(), 6);
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
    }

    @Test
    public void add() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(JIM_BIRTHDAY_YEAR_ONE);
        assertEquals(popUpManager.size(), 2);
        popUpManager.add(STARTUP_LECTURE_MONTH_ONE);
        assertEquals(popUpManager.size(), 2); //Nothing added

        // test overloading method here with list of events as input
        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 8); // Allow repeated EventPopUpInfo, since allow repeated events
        popUpManager.add(getStudyWithJaneAllList());
        assertEquals(popUpManager.size(), 8); //Nothing added
    }

    @Test
    public void delete() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(JIM_BIRTHDAY_YEAR_ONE);
        assertEquals(popUpManager.size(), 2);
        popUpManager.delete(JIM_BIRTHDAY_YEAR_ONE);
        assertEquals(popUpManager.size(), 0);

        popUpManager.add(JIM_BIRTHDAY_YEAR_ONE);
        popUpManager.add(JIM_BIRTHDAY_YEAR_TWO);
        assertEquals(popUpManager.size(), 4);
        popUpManager.delete(JIM_BIRTHDAY_YEAR_ONE);
        assertEquals(popUpManager.size(), 2); //Nothing added
    }

    @Test
    public void deleteUpcoming() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        popUpManager.deleteUpcoming(JIM_BIRTHDAY_YEAR_TWO);
        assertEquals(popUpManager.size(), 2);
    }

    @Test
    public void deleteAll() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        popUpManager.deleteAll(JIM_BIRTHDAY_YEAR_TWO);
        assertEquals(popUpManager.size(), 0);

        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        popUpManager.deleteAll(JIM_BIRTHDAY_YEAR_ONE);
        assertEquals(popUpManager.size(), 0);

        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        popUpManager.deleteAll(JIM_BIRTHDAY_YEAR_ONE);
        assertEquals(popUpManager.size(), 0);
    }

    @Test
    public void edit() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        EventBuilder eventInList = new EventBuilder(JIM_BIRTHDAY_YEAR_ONE);
        Event editedEvent = eventInList.withReminderDurationList(getReminderDurationList(0)).build();
        popUpManager.edit(JIM_BIRTHDAY_YEAR_ONE, editedEvent);
        assertEquals(popUpManager.size(), 5);
    }

    @Test
    public void editAll() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        List<Event> editedEvents = new ArrayList<>();
        EventBuilder eventInList = new EventBuilder(JIM_BIRTHDAY_YEAR_ONE);
        Event editedEvent = eventInList.withReminderDurationList(getReminderDurationList(0)).build();
        EventBuilder eventInList2 = new EventBuilder(JIM_BIRTHDAY_YEAR_TWO);
        Event editedEvent2 = eventInList2.withReminderDurationList(getReminderDurationList(0)).build();
        EventBuilder eventInList3 = new EventBuilder(JIM_BIRTHDAY_YEAR_THREE);
        Event editedEvent3 = eventInList3.withReminderDurationList(getReminderDurationList(0)).build();
        editedEvents.add(editedEvent);
        editedEvents.add(editedEvent2);
        editedEvents.add(editedEvent3);
        popUpManager.editAll(JIM_BIRTHDAY_YEAR_TWO, editedEvents);
        assertEquals(popUpManager.size(), 3);
    }

    @Test
    public void editUpcoming() {
        popUpManager.clear();
        assertEquals(popUpManager.size(), 0);
        popUpManager.add(getBirthdayAllList());
        assertEquals(popUpManager.size(), 6);
        List<Event> editedEvents = new ArrayList<>();
        EventBuilder eventInList2 = new EventBuilder(JIM_BIRTHDAY_YEAR_TWO);
        Event editedEvent2 = eventInList2.withReminderDurationList(getReminderDurationList(0)).build();
        EventBuilder eventInList3 = new EventBuilder(JIM_BIRTHDAY_YEAR_THREE);
        Event editedEvent3 = eventInList3.withReminderDurationList(getReminderDurationList(0)).build();
        editedEvents.add(editedEvent2);
        editedEvents.add(editedEvent3);
        popUpManager.editUpcoming(JIM_BIRTHDAY_YEAR_TWO, editedEvents);
        assertEquals(popUpManager.size(), 4);

        popUpManager.clear();
        popUpManager.add(getBirthdayAllList());
        editedEvents.clear();
        editedEvents.add(editedEvent3);
        popUpManager.editUpcoming(JIM_BIRTHDAY_YEAR_THREE, editedEvents);
        assertEquals(popUpManager.size(), 5);
        popUpManager.clear();
    }


    /**
     * Initialise PopUpManager if the instance is not yet created.
     */
    private void initialisePopUpManager() {
        popUpManager = PopUpManager.getInstance();
    }
}
