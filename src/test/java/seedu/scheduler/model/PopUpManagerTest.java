package seedu.scheduler.model;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.testutil.TypicalEvents.DINNER_WITH_JOE_WEEKLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.DINNER_WITH_JOE_WEEK_ONE;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEARLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEAR_ONE;
import static seedu.scheduler.testutil.TypicalEvents.LEAP_DAY_CELEBRATION_YEARLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.LEAP_DAY_CELEBRATION_YEAR_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STARTUP_LECTURE_MONTHLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.STARTUP_LECTURE_MONTH_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAILY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_ONE;
import static seedu.scheduler.testutil.TypicalEvents.getBirthdayAllList;
import static seedu.scheduler.testutil.TypicalEvents.getSchedulerBirthday;
import static seedu.scheduler.testutil.TypicalEvents.getSchedulerStudyWithJane;
import static seedu.scheduler.testutil.TypicalEvents.getStudyWithJaneAllList;
import static seedu.scheduler.testutil.TypicalEvents.getTypicalScheduler;

import org.junit.Before;
import org.junit.Test;

import seedu.scheduler.logic.RepeatEventGenerator;

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
    }

    @Test
    public void reInitialise() {
        popUpManager.reInitialise(model2.getScheduler());
        assertEquals(popUpManager.size(), 0);
        popUpManager.reInitialise(model1.getScheduler());
        assertEquals(popUpManager.size(), 6);
    }

    @Test
    public void add() {
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
    public void edit() {
    }



    /**
     * Initialise RepeatEventGenerator if the instance is not yet created.
     */
    private void initialisePopUpManager() {
        popUpManager = PopUpManager.getInstance();
    }
}
