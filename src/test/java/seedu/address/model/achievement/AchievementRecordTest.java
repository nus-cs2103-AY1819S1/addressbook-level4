package seedu.address.model.achievement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.achievement.exceptions.DateBreakPointsMismatchException;
import seedu.address.model.achievement.exceptions.XpLevelMismatchException;
import seedu.address.testutil.AchievementRecordBuilder;

public class AchievementRecordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AchievementRecord achievementRecord = new AchievementRecord();

    @Test
    public void constructor() {
        AchievementRecord expected = new AchievementRecordBuilder()
                .withNextDayBreakPointFromNow()
                .withNextWeekBreakPointFromNow()
                .build();
        assertEquals(achievementRecord, expected);
    }

    @Test
    public void is_valid_displayOption() {
        // argument is valid display option
        assertTrue(AchievementRecord.isValidDisplayOption(AchievementRecord.DISPLAY_ALL_TIME));
        assertTrue(AchievementRecord.isValidDisplayOption(AchievementRecord.DISPLAY_TODAY));
        assertTrue(AchievementRecord.isValidDisplayOption(AchievementRecord.DISPLAY_THIS_WEEK));

        // argument is invalid display option
        assertFalse(AchievementRecord.isValidDisplayOption(5));
    }
    
    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        achievementRecord.resetData(null);
    }

    @Test
    public void resetData_withValidAchievementRecord_replacesData() {
        AchievementRecord newData = new AchievementRecordBuilder().build();
        achievementRecord.resetData(newData);
        assertEquals(newData, achievementRecord);
    }
    
    @Test
    public void resetData_withXpLevelMismatch_throwsXpLevelMismatchException() {
        AchievementRecord newData = new AchievementRecordBuilder().build();
        newData.setLevel(Level.LEVEL_3);
        thrown.expect(XpLevelMismatchException.class);
        achievementRecord.resetData(newData);
    }
    
    @Test
    public void resetData_withDateBreakPointsMismatch_throwsDateBreakPointsMismatchException() {
        // new Data has nextWeekBreakPoint before nextDayBreakPoint
        AchievementRecord newData = new AchievementRecordBuilder().build();
        Calendar oneDayAhead = (GregorianCalendar) newData.getNextWeekBreakPoint().clone();
        oneDayAhead.add(Calendar.DAY_OF_MONTH, 1);
        newData.setNextDayBreakPoint(oneDayAhead);
        thrown.expect(DateBreakPointsMismatchException.class);
        achievementRecord.resetData(newData);

        // newData has nextDayBreakPoint more than 6 days before nextWeekBreakPoint
        newData = new AchievementRecordBuilder().build();
        Calendar sevenDaysBack = (GregorianCalendar) newData.getNextWeekBreakPoint().clone();
        sevenDaysBack.add(Calendar.DAY_OF_MONTH, -7);
        newData.setNextDayBreakPoint(sevenDaysBack);
        thrown.expect(DateBreakPointsMismatchException.class);
        achievementRecord.resetData(newData);
    }
    
    @Test
    public void incrementAchievementsWithNewXp_noBreakPointIsPassed_noReset() {

//        //get a default achievementRecord for this test
//        AchievementRecord achievementRecord = new AchievementRecordBuilder().build();

        // no level up with new xp
        int xpAdded = 400;
        int numTasksCompleted = 1;
        AchievementRecord expectedWithoutLevelUp = new AchievementRecordBuilder()
                .withXpValue(xpAdded)
                .withLevel(Level.LEVEL_1)
                .withNumTaskCompleted(numTasksCompleted)
                .withXpValueByDay(xpAdded)
                .withNumTaskCompletedByDay(numTasksCompleted)
                .withXpValueByWeek(xpAdded)
                .withNumTaskCompletedByWeek(numTasksCompleted)
                .build();

        achievementRecord.incrementAchievementsWithNewXp(xpAdded);
        assertEquals(achievementRecord, expectedWithoutLevelUp);

        // level up with new xp

        // add another 400xp, total xp increases pass the level 1 maximum which is 500
        achievementRecord.incrementAchievementsWithNewXp(xpAdded);
        xpAdded += 400;
        numTasksCompleted++;
        AchievementRecord expectedWithLevelUp = new AchievementRecordBuilder()
                .withXpValue(xpAdded)
                .withLevel(Level.LEVEL_2)
                .withNumTaskCompleted(numTasksCompleted)
                .withXpValueByDay(xpAdded)
                .withNumTaskCompletedByDay(numTasksCompleted)
                .withXpValueByWeek(xpAdded)
                .withNumTaskCompletedByWeek(numTasksCompleted)
                .build();
        assertEquals(achievementRecord, expectedWithLevelUp);
    }


    @Test
    public void incrementAchievementsWithNewXp_nextDayBreakPointIsPassed_AchievementsTodayReset() {

        // set up the achievement record to have non-zero time based achievement fields
        int setUpXp = 400;
        achievementRecord.incrementAchievementsWithNewXp(setUpXp);
        achievementRecord.incrementAchievementsWithNewXp(setUpXp);

        // set the nextDayBreakPoint to be yesterday while still keeping nextWeekBreakPoint consistent
        Calendar ytd = new GregorianCalendar();
//        ytd.add(Calendar.DAY_OF_MONTH, -1);
        Calendar aWeekFromYtd = new GregorianCalendar();
        aWeekFromYtd.add(Calendar.DAY_OF_MONTH, 6);
        AchievementRecord achievementRecord = new AchievementRecordBuilder(this.achievementRecord)
                .withNextDayBreakPoint(ytd)
                .withNextWeekBreakPoint(aWeekFromYtd)
                .build();

        // nextDayBreakPoint is expected to be reset to the start of tomorrow, xpValueByDay and numTaskCompletedByDay
        // reset to 0 before being incremented again with the new xp
        int xpToAdd = 50;
        int numOfTaskToComplete = 1;
        int totalXp = this.achievementRecord.getXpValue() + xpToAdd;
        int totalNumTasksCompleted = this.achievementRecord.getNumTaskCompleted() + numOfTaskToComplete;
        AchievementRecord expected = new AchievementRecordBuilder(this.achievementRecord)
                .withNextDayBreakPointFromNow()
                .withNextWeekBreakPoint(aWeekFromYtd)
                .withXpValue(totalXp)
                .withNumTaskCompleted(totalNumTasksCompleted)
                .withXpValueByDay(xpToAdd)
                .withNumTaskCompletedByDay(numOfTaskToComplete)
                .withXpValueByWeek(totalXp)
                .withNumTaskCompletedByWeek(totalNumTasksCompleted)
                .build();

        achievementRecord.incrementAchievementsWithNewXp(xpToAdd);
        assertEquals(achievementRecord, expected);
    }
}

//class AchievementRecordStub extends AchievementRecord {
//    private Level level;
//    private Calendar nextDayBreakPoint;
//
//    AchievementRecordStub() {
//        super();
//    }
//
//    public void setLevel(Level lvl) {
//        super.level = lvl;
//    }
//}

//    @Test
//    public void hasTask_nullTask_throwsNullPointerException() {
//        thrown.expect(NullPointerException.class);
//        taskManager.hasTask(null);
//    }
//
//    @Test
//    public void hasTask_taskNotInTaskManager_returnsFalse() {
//        assertFalse(taskManager.hasTask(A_TASK));
//    }
//
//    @Test
//    public void hasTask_personInTaskManager_returnsTrue() {
//        taskManager.addTask(A_TASK);
//        assertTrue(taskManager.hasTask(A_TASK));
//    }
//
//    @Test
//    public void hasTask_taskWithSameIdentityFieldsInTaskManager_returnsTrue() {
//        taskManager.addTask(A_TASK);
//        Task editedAlice = new TaskBuilder(A_TASK).withDescription(VALID_DESCRIPTION_BOB)
//                .withLabels(VALID_LABEL_HUSBAND)
//                .build();
//        assertTrue(taskManager.hasTask(editedAlice));
//    }
//
//    @Test
//    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
//        thrown.expect(UnsupportedOperationException.class);
//        taskManager.getTaskList().remove(0);
//    }
//
///**
// * A stub ReadOnlyTaskManager whose tasks list can violate interface constraints.
// */
//private static class TaskManagerStub implements ReadOnlyTaskManager {
//    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
//
//    TaskManagerStub(Collection<Task> persons) {
//        this.tasks.setAll(persons);
//    }
//
//    @Override
//    public ObservableList<Task> getTaskList() {
//        return tasks;
//    }
//
//    @Override
//    public AchievementRecord getAchievementRecord() {
//        throw new AssertionError("This method should not be called.");
//    }
