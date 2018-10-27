package seedu.address.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javafx.beans.property.SimpleObjectProperty;

/**
 * Represents a record of the user's achievements while using the task manager.
 * Achievements include the experience points(xp) earned by completing the task and the level a user has reached.
 * Guarantees: details are present and not null.
 */
public class AchievementRecord {

    private Xp xp;
    private Level level;
    private int numTaskCompleted;

    private Calendar nextDayBreakPoint;
    private int xpValueByDay;
    private int numTaskCompletedByDay;

    private Calendar nextWeekBreakPoint;
    private int xpValueByWeek;
    private int numTaskCompletedByWeek;

    /**
     * Constructs a {@code AchievementRecord}.
     * Xp value is initialized to 0 and Level initialized to LEVEL_1.
     */
    public AchievementRecord() {
        setUpAchievementRecord();
        setUpAchievementByDay();
        setUpAchievementByWeek();
    }

    /**
     * Constructs a {@code AchievementRecord}.
     * Both fields must be present.
     */
    public AchievementRecord(Xp xp, Level level, int numTaskCompleted, Calendar nextDayBreakPoint,
                             int numTaskCompletedByDay, int xpValueByDay, Calendar nextWeekBreakPoint,
                             int numTaskCompletedByWeek, int xpValueByWeek) {
        requireAllNonNull(xp, level, numTaskCompleted, nextDayBreakPoint, numTaskCompletedByDay, xpValueByDay,
                nextWeekBreakPoint, numTaskCompletedByWeek, xpValueByWeek);
        this.xp = xp;
        this.level = level;
        this.numTaskCompleted = numTaskCompleted;
        
        this.nextDayBreakPoint = nextDayBreakPoint;
        this.numTaskCompletedByDay = numTaskCompletedByDay;
        this.xpValueByDay = xpValueByDay;
        
        this.nextWeekBreakPoint = nextWeekBreakPoint;
        this.numTaskCompletedByWeek = numTaskCompletedByWeek;
        this.xpValueByWeek = xpValueByWeek;
    }

    public Xp getXp() {
        return xp;
    }

    public Level getLevel() {
        return level;
    }

    public int getXpValue() {
        return xp.getXp();
    }

    public int getNumTaskCompleted() {
        return numTaskCompleted;
    }

    public Calendar getNextDayBreakPoint() {
        return nextDayBreakPoint;
    }

    public int getXpValueByDay() {
        return xpValueByDay;
    }

    public int getNumTaskCompletedByDay() {
        return numTaskCompletedByDay;
    }

    public Calendar getNextWeekBreakPoint() {
        return nextWeekBreakPoint;
    }

    public int getXpValueByWeek() {
        return xpValueByWeek;
    }

    public int getNumTaskCompletedByWeek() {
        return numTaskCompletedByWeek;
    }

    private void setUpAchievementRecord() {
        this.xp = new Xp();
        this.level = Level.LEVEL_1;
        this.numTaskCompleted = 0;
    }
    
    private void setUpAchievementByDay() {
        assert nextDayBreakPoint == null;
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.add(Calendar.DAY_OF_MONTH, 1);
        nextDayBreakPoint = date;
        numTaskCompletedByDay = 0;
        xpValueByDay = 0;
    }

    private void setUpAchievementByWeek() {
        assert nextWeekBreakPoint == null;
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.add(Calendar.DAY_OF_MONTH, 7);
        nextWeekBreakPoint = date;
        numTaskCompletedByWeek = 0;
        xpValueByWeek = 0;
    }

    /**
     * Resets the existing data of this {@code AchievementRecord} with {@code newData}.
     */
    public void resetData(AchievementRecord newData) {
        requireNonNull(newData);

        xp = newData.xp;
        level = newData.level;
        numTaskCompleted = newData.numTaskCompleted;
        nextDayBreakPoint = newData.nextDayBreakPoint;
        numTaskCompletedByDay = newData.numTaskCompletedByDay;
        xpValueByDay = newData.xpValueByDay;
        nextWeekBreakPoint = newData.nextWeekBreakPoint;
        numTaskCompletedByWeek = newData.numTaskCompletedByWeek;
        xpValueByWeek = newData.xpValueByWeek;
    }

    /**
     * Updates all fields of this {@code AchievementRecord} with new xp value.
     * Triggers the update of Level with xp.
     */
    public void updateAchievementsWithNewXp(Integer newXp) {
        requireNonNull(newXp);

        updateAllTimeAchievementWithNewXP(newXp);
        updateAchievementByDayWithNewXp(newXp);
        updateAchievementByWeekWithNewXp(newXp); 
    }

    /**
     * Updates the xp, level, numTaskCompleted fields of this {@code AchievementRecord} with new xp value.
     */
    private void updateAllTimeAchievementWithNewXP(int newXp) {
        Integer updatedXpValue = this.getXpValue() + newXp;
        this.xp = new Xp(updatedXpValue);

        // recalculate level based on updated xp and update level field if necessary
        Level newLevel = getMatchingLevel(updatedXpValue);
        if (!this.level.equals(newLevel)) {
            level = newLevel;
        }

        // one task is completed each time xp is awarded
        numTaskCompleted++;
    }

    /**
     * Returns the corresponding {@code Level} of the current Xp value.
     * Maximum level is level 5.
     */
    private Level getMatchingLevel(Integer xp) {
        if (xp < Level.LEVEL_1.getMaxXp()) {
            return Level.LEVEL_1;
        } else if (xp < Level.LEVEL_2.getMaxXp()) {
            return Level.LEVEL_2;
        } else if (xp < Level.LEVEL_3.getMaxXp()) {
            return Level.LEVEL_3;
        } else if (xp < Level.LEVEL_4.getMaxXp()) {
            return Level.LEVEL_4;
        } else {
            return Level.LEVEL_5;
        }
    }

    private void updateAchievementByDayWithNewXp(int newXp) {
        dayBreakPointChecknSet();

        // one task is completed each time xp is awarded
        numTaskCompletedByDay++;
        xpValueByDay += newXp;
    }

    private void updateAchievementByWeekWithNewXp(int newXp) {
        weekBreakPointChecknSet();

        // one task is completed each time xp is awarded
        numTaskCompletedByWeek++;
        xpValueByWeek += newXp;
    }

    /**
     * Check if the current day has elapsed and the next day is reached.
     * If true, reset {@code nextDayBreakPoint} to be the start of next day and reset the cumulative achievements
     * from the previous day to 0.
     */
    private void dayBreakPointChecknSet() {
        Calendar date = new GregorianCalendar();
        if (date.before(nextDayBreakPoint)) return;
        nextDayBreakPoint = null;
        setUpAchievementByDay();
    }

    /**
     * Check if the current week has elapsed and the next week is reached.
     * If true, reset {@code nextWeekBreakPoint} to be the start of next week and reset the cumulative achievements
     * from the previous week to 0.
     */
    private void weekBreakPointChecknSet() {
        Calendar date = new GregorianCalendar();
        if (date.before(nextWeekBreakPoint)) return;
        nextWeekBreakPoint = null;
        setUpAchievementByWeek();
    }

    private boolean areDatesEqual(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
                && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AchievementRecord // instanceof handles nulls
                && xp.equals(((AchievementRecord) other).xp))
                && level.equals(((AchievementRecord) other).level)
                && numTaskCompleted == ((AchievementRecord) other).numTaskCompleted
                && areDatesEqual(nextDayBreakPoint, ((AchievementRecord) other).nextDayBreakPoint)
                && numTaskCompletedByDay == ((AchievementRecord) other).numTaskCompletedByDay
                && xpValueByDay == ((AchievementRecord) other).xpValueByDay
                && areDatesEqual(nextWeekBreakPoint, ((AchievementRecord) other).nextWeekBreakPoint)
                && numTaskCompletedByWeek == ((AchievementRecord) other).numTaskCompletedByWeek
                && xpValueByWeek == ((AchievementRecord) other).xpValueByWeek;
    }
}
