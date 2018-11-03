package seedu.address.model.achievement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        AchievementRecord newData = new AchievementRecordBuilder().build();
        newData.setNextDayBreakPoint(new GregorianCalendar());
        thrown.expect(DateBreakPointsMismatchException.class);
        achievementRecord.resetData(newData);
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
