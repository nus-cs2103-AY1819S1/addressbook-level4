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

    private AchievementRecord getNonEmptyAchievementRecord() {
        AchievementRecord nonEmptyAchRec = new AchievementRecord();
        nonEmptyAchRec.incrementAchievementsWithNewXp(400);
        nonEmptyAchRec.incrementAchievementsWithNewXp(400);
        return nonEmptyAchRec;
    }

    /**
     * Returns an {@code AchievementRecord} with nextDayBreakPoint set to be the beginning of today and all other
     * fields same as the {@code original}
     */
    private AchievementRecord getRecWithPassedNextDayBreakPoint(AchievementRecord original) {
        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        Calendar aWeekFromYtd = new GregorianCalendar();
        aWeekFromYtd.add(Calendar.DAY_OF_MONTH, 6);
        return new AchievementRecordBuilder(original)
                .withNextDayBreakPoint(today)
                .withNextWeekBreakPoint(aWeekFromYtd)
                .build();
    }

    /**
     * Returns an {@code AchievementRecord} with nextDayBreakPoint and nextWeekBreakPoint set to be the beginning of 
     * today and all other fields same as the {@code original}
     */
    private AchievementRecord getRecWithPassedBreakPoints(AchievementRecord original) {
        Calendar today = new GregorianCalendar();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        return new AchievementRecordBuilder(original)
                .withNextDayBreakPoint(today)
                .withNextWeekBreakPoint(today)
                .build();
    }

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
    public void setDisplayOption_noBreakPointIsPassed_noReset() {
        // set up the achievement record to have non-zero time based achievement fields
        AchievementRecord achievementRecord = getNonEmptyAchievementRecord();

        int option = AchievementRecord.DISPLAY_THIS_WEEK;
        AchievementRecord expected = new AchievementRecordBuilder(achievementRecord)
                .withDisplayOption(option)
                .build();
        achievementRecord.setDisplayOption(option);
        
    }

    @Test
    public void setDisplayOption_nextDayBreakPointIsPassed_AchievementsTodayReset() {

        // get an original achievement record with non-zero time based achievement fields
        AchievementRecord original = getNonEmptyAchievementRecord();

        // set the nextDayBreakPoint to be beginning of today
        AchievementRecord achievementRecord = getRecWithPassedNextDayBreakPoint(original);

        // nextDayBreakPoint is expected to be reset to the start of tomorrow, xpValueByDay and numTaskCompletedByDay
        // reset to 0 before being incremented again with the new xp
        int option = AchievementRecord.DISPLAY_TODAY;
        int valueAfterReset = 0;
        AchievementRecord expected = new AchievementRecordBuilder(original)
                .withDisplayOption(option)
                .withNextDayBreakPointFromNow()
                .withNextWeekBreakPoint(achievementRecord.getNextWeekBreakPoint())
                .withXpValueByDay(valueAfterReset)
                .withNumTaskCompletedByDay(valueAfterReset)
                .build();

        achievementRecord.setDisplayOption(option);
        assertEquals(achievementRecord, expected);
    }
    
    @Test
    public void incrementAchievementsWithNewXp_noBreakPointIsPassed_noReset() {

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

        // get an original achievement record with non-zero time based achievement fields
        AchievementRecord original = getNonEmptyAchievementRecord();

        // set the nextDayBreakPoint to be beginning of today
        AchievementRecord achievementRecord = getRecWithPassedNextDayBreakPoint(original);

        // nextDayBreakPoint is expected to be reset to the start of tomorrow, xpValueByDay and numTaskCompletedByDay
        // reset to 0 before being incremented again with the new xp
        int xpToAdd = 50;
        int numOfTaskToComplete = 1;
        int totalXp = original.getXpValue() + xpToAdd;
        int totalNumTasksCompleted = original.getNumTaskCompleted() + numOfTaskToComplete;
        AchievementRecord expected = new AchievementRecordBuilder(original)
                .withNextDayBreakPointFromNow()
                .withNextWeekBreakPoint(achievementRecord.getNextWeekBreakPoint())
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

    /**
     * If the current time has passed the {@code nextWeekBreakPoint}, it must have passed the {@code nextDayBreakPoint}
     * as well because {@code nextDayBreakPoint} is restricted to be no later than {@code nextWeekBreakPoint}.
     * Therefore there would not be a case where only the {@code nextWeekBreakPoint} is passed.
     * 
     * This test is for the case when {@code nextWeekBreakPoint} is passed, and along with it, {@code nextDayBreakPoint}
     * is passed as well.
     */
    @Test
    public void incrementAchievementsWithNewXp_bothBreakPointsArePassed_AchievementsTodayAndThisWeekReset() {

        // get an original achievement record with non-zero time based achievement fields
        AchievementRecord original = getNonEmptyAchievementRecord();

        // set both nextDayBreakPoint and nextWeekBreakPoint to be the beginning of today
        AchievementRecord achievementRecord = getRecWithPassedBreakPoints(original); 

        // nextDayBreakPoint is expected to be reset to the start of tomorrow, xpValueByDay and numTaskCompletedByDay
        // reset to 0 before being incremented again with the new xp

        // nextWeekBreakPoint is expected to be reset to the start of (today + 7 days), xpValueByWeek and
        // numTaskCompletedByWeek reset to 0 before being incremented again with the new xp
        int xpToAdd = 50;
        int numOfTaskToComplete = 1;
        int totalXp = original.getXpValue() + xpToAdd;
        int totalNumTasksCompleted = original.getNumTaskCompleted() + numOfTaskToComplete;
        AchievementRecord expected = new AchievementRecordBuilder(original)
                .withNextDayBreakPointFromNow()
                .withNextWeekBreakPointFromNow()
                .withXpValue(totalXp)
                .withNumTaskCompleted(totalNumTasksCompleted)
                .withXpValueByDay(xpToAdd)
                .withNumTaskCompletedByDay(numOfTaskToComplete)
                .withXpValueByWeek(xpToAdd)
                .withNumTaskCompletedByWeek(numOfTaskToComplete)
                .build();

        achievementRecord.incrementAchievementsWithNewXp(xpToAdd);
        assertEquals(achievementRecord, expected);
    }
}

