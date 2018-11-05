package seedu.address.model.achievement;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

import seedu.address.model.achievement.exceptions.CumulativeAchievementsMismatchException;
import seedu.address.model.achievement.exceptions.DateBreakPointsMismatchException;
import seedu.address.model.achievement.exceptions.XpLevelMismatchException;

/**
 * Represents a record of the user's achievements while using the task manager.
 * Achievements include the experience points(xp) earned by completing the task across all time, today, and this week,
 * number of tasks a user has completed across all time, today and this week and the level a user has reached.
 *
 * Keeps two date breakpoints at which today's or this week's achievements is cleared and the breakpoints are reset.
 *
 * Records the the achievement record's display preference set by user, such preference is set by
 * {@link seedu.address.logic.commands.AchievementsCommand}.
 *
 * Guarantees: details are present and not null.
 */
public class AchievementRecord {

    public static final int DISPLAY_ALL_TIME = 1;
    public static final int DISPLAY_TODAY = 2;
    public static final int DISPLAY_THIS_WEEK = 3;

    private int displayOption;

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
     * All fields must be present.
     */
    public AchievementRecord(int displayOption, Xp xp, Level level, int numTaskCompleted, Calendar nextDayBreakPoint,
                             int numTaskCompletedByDay, int xpValueByDay, Calendar nextWeekBreakPoint,
                             int numTaskCompletedByWeek, int xpValueByWeek) {

        requireAllNonNull(displayOption, xp, level, numTaskCompleted, nextDayBreakPoint, numTaskCompletedByDay,
                xpValueByDay, nextWeekBreakPoint, numTaskCompletedByWeek, xpValueByWeek);
        checkAchievementFieldsMatch(xp, level, numTaskCompleted, nextDayBreakPoint, numTaskCompletedByDay,
                xpValueByDay, nextWeekBreakPoint, numTaskCompletedByWeek, xpValueByWeek);

        this.displayOption = displayOption;
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

    /**
     * Provides a direct accesses of the max xp value of the current level.
     */
    public int getLevelMaxXp() {
        return level.getMaxXp();
    }

    /**
     * Provides a direct accesses of the current xp value.
     */
    public int getXpValue() {
        return xp.getXp();
    }

    public Xp getXp() {
        return xp;
    }

    public void setXp(Xp xp) {
        this.xp = xp;
    }

    public void setNumTaskCompleted(int numTaskCompleted) {
        this.numTaskCompleted = numTaskCompleted;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getNumTaskCompleted() {
        return numTaskCompleted;
    }

    public Calendar getNextDayBreakPoint() {
        return nextDayBreakPoint;
    }

    public void setNextDayBreakPoint(Calendar nextDayBreakPoint) {
        this.nextDayBreakPoint = nextDayBreakPoint;
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

    public void setNextWeekBreakPoint(Calendar nextWeekBreakPoint) {
        this.nextWeekBreakPoint = nextWeekBreakPoint;
    }

    public int getXpValueByWeek() {
        return xpValueByWeek;
    }

    public int getNumTaskCompletedByWeek() {
        return numTaskCompletedByWeek;
    }

    public int getDisplayOption() {
        return displayOption;
    }

    public void setDisplayOption(int displayOption) {
        checkDayBreakPoint();
        checkWeekBreakPoint();
        this.displayOption = displayOption;
    }

    private void setUpAchievementRecord() {
        this.displayOption = DISPLAY_ALL_TIME;
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
     * Checks if given displayOption is valid.
     * A valid {@code displayOption} may only take the vale of {@code DISPLAY_ALL_TIME},
     * {@code DISPLAY_TODAY} or {@code DISPLAY_THIS_WEEK}.
     */
    public static boolean isValidDisplayOption(int displayOption) {
        return (displayOption == AchievementRecord.DISPLAY_ALL_TIME
                || displayOption == AchievementRecord.DISPLAY_TODAY
                || displayOption == AchievementRecord.DISPLAY_THIS_WEEK);
    }

    /**
     * Resets the existing data of this {@code AchievementRecord} with {@code newData}.
     */
    public void resetData(AchievementRecord newData) {
        requireNonNull(newData);
        checkAchievementFieldsMatch(newData);

        displayOption = newData.displayOption;
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
     * Defensively check that the xp fields matches: today's xp is no greater than this week's xp,
     * which is no greater than all-time xp.
     */
    private void checkAchievementFieldsMatch (Xp xp, Level level, int numTaskCompleted, Calendar nextDayBreakPoint,
                                              int numTaskCompletedByDay, int xpValueByDay, Calendar nextWeekBreakPoint,
                                              int numTaskCompletedByWeek, int xpValueByWeek) {
        checkXpAndLevelMatch(xp.getXp(), level);
        checkBreakPointsMatch(nextDayBreakPoint, nextWeekBreakPoint);
        checkXpValuesMatch(xp.getXp(), xpValueByDay, xpValueByWeek);
        checkNumTaskCompletedMatch(numTaskCompleted, numTaskCompletedByDay, numTaskCompletedByWeek);
    }

    /**
     * Defensively check that the numTaskCompleted fields matches: today's numTaskCompleted is no greater than this
     * week's numTaskCompleted, which is no greater than all-time numTaskCompleted.
     */
    private void checkAchievementFieldsMatch (AchievementRecord record) {
        checkXpAndLevelMatch(record.xp.getXp(), record.level);
        checkBreakPointsMatch(record.nextDayBreakPoint, record.nextWeekBreakPoint);
        checkXpValuesMatch(record.xp.getXp(), record.xpValueByDay, record.xpValueByWeek);
        checkNumTaskCompletedMatch(record.numTaskCompleted, record.numTaskCompletedByDay,
                record.numTaskCompletedByWeek);
    }

    /**
     * Defensively check given xp value matches given level.
     * The xp value matches with level if level.minXp <= xp < level.maxXp
     * @throws XpLevelMismatchException if not match.
     */
    private void checkXpAndLevelMatch(int xp, Level lvl) {
        if (!getMatchingLevel(xp).equals(lvl)) {
            throw new XpLevelMismatchException();
        }
    }

    /**
     * Defensively check {@code nextDayBreakPoint} is at most 6 days after {@code nextWeekBreakPoint} and
     * not before {@code nextWeekBreakPoint}.
     * @throws DateBreakPointsMismatchException if not match.
     */
    private void checkBreakPointsMatch(Calendar nextDay, Calendar nextWeek) {
        Calendar maxDate = (GregorianCalendar) nextDay.clone();
        maxDate.add(Calendar.DAY_OF_MONTH, 6);

        Calendar minDate = (GregorianCalendar) nextDay.clone();
        if (!areSameDates(maxDate, nextWeek) && nextWeek.after(maxDate)) {
            throw new DateBreakPointsMismatchException();
        }
        if (!areSameDates(minDate, nextWeek) && nextWeek.before(minDate)) {
            throw new DateBreakPointsMismatchException();
        }
    }

    /**
     * Defensively check that today's xp is no greater than this week's xp, which is no greater than all-time xp.
     * @throws CumulativeAchievementsMismatchException if not match.
     */
    private void checkXpValuesMatch(int xp, int xpValueByDay, int xpValueByWeek) {
        if (xpValueByDay <= xpValueByWeek && xpValueByWeek <= xp) {
            return;
        }
        throw new CumulativeAchievementsMismatchException();
    }

    /**
     * Defensively check that today's number of tasks completed is no greater than this week's number of tasks
     * completed, which is no greater than all-time number of tasks completed.
     * @throws CumulativeAchievementsMismatchException if not match.
     */
    private void checkNumTaskCompletedMatch(int numTaskCompleted, int numTaskCompletedByDay,
                                            int numTaskCompletedByWeek) {
        if (numTaskCompletedByDay <= numTaskCompletedByWeek && numTaskCompletedByWeek <= numTaskCompleted) {
            return;
        }
        throw new CumulativeAchievementsMismatchException();
    }

    /**
     * Updates all fields of this {@code AchievementRecord} with new xp being awarded.
     */
    public void incrementAchievementsWithNewXp(int newXp) {

        incrementAllTimeAchievementWithNewXp(newXp);
        incrementAchievementByDayWithNewXp(newXp);
        incrementAchievementByWeekWithNewXp(newXp);
    }

    /**
     * Returns the corresponding {@code Level} of the current Xp value.
     * An xp value falls within level_n if level_n.minXp <= xp < level_n.maxXp
     * Maximum level is level 5.
     */
    private Level getMatchingLevel(int xp) {
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

    /**
     * Increments the xp, numTaskCompleted fields of this {@code AchievementRecord} with the new xp value.
     * Recalculates level with the new xp value and increments if necessary.
     */
    private void incrementAllTimeAchievementWithNewXp(int newXp) {
        int updatedXpValue = this.getXpValue() + newXp;
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
     * Check if the current time has passed the previously set {@code nextDayBreakPoint}
     * Increment xp and number of tasks completed.
     */
    private void incrementAchievementByDayWithNewXp(int newXp) {
        checkDayBreakPoint();

        // one task is completed each time xp is awarded
        numTaskCompletedByDay++;
        xpValueByDay += newXp;
    }

    /**
     * Check if the current time has passed the previously set {@code nextWeekBreakPoint}
     * Increment xp and number of tasks completed.
     */
    private void incrementAchievementByWeekWithNewXp(int newXp) {
        checkWeekBreakPoint();

        // one task is completed each time xp is awarded
        numTaskCompletedByWeek++;
        xpValueByWeek += newXp;
    }

    /**
     * Check if the current day has elapsed and the next day is reached.
     * If true, reset {@code nextDayBreakPoint} to be the start of next day and reset the cumulative achievements
     * from the previous day to 0.
     */
    private void checkDayBreakPoint() {
        Calendar date = new GregorianCalendar();
        if (date.before(nextDayBreakPoint)) {

            // check that current time is within one day before nextWeekBreakPoint
            date.add(Calendar.DAY_OF_MONTH, 1);
            if (date.after(nextDayBreakPoint)) {
                return;
            }
        }
        nextDayBreakPoint = null;
        setUpAchievementByDay();
    }

    /**
     * Check if the current week has elapsed and the next week is reached.
     * If true, reset {@code nextWeekBreakPoint} to be the start of next week and reset the cumulative achievements
     * from the previous week to 0.
     */
    private void checkWeekBreakPoint() {
        Calendar date = new GregorianCalendar();
        if (date.before(nextWeekBreakPoint)) {

            // check that current time is within one week before nextWeekBreakPoint
            date.add(Calendar.DAY_OF_MONTH, 7);
            if (date.after(nextWeekBreakPoint)) {
                return;
            }
        }
        nextWeekBreakPoint = null;
        setUpAchievementByWeek();
    }

    /**
     * Only check date for equality, ignore hour, minute, second and other fields of calendar.
     */
    private boolean areSameDates(Calendar date1, Calendar date2) {
        return date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
                && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH)
                && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AchievementRecord // instanceof handles nulls
                && displayOption == ((AchievementRecord) other).displayOption
                && xp.equals(((AchievementRecord) other).xp))
                && level.equals(((AchievementRecord) other).level)
                && numTaskCompleted == ((AchievementRecord) other).numTaskCompleted
                && areSameDates(nextDayBreakPoint, ((AchievementRecord) other).nextDayBreakPoint)
                && numTaskCompletedByDay == ((AchievementRecord) other).numTaskCompletedByDay
                && xpValueByDay == ((AchievementRecord) other).xpValueByDay
                && areSameDates(nextWeekBreakPoint, ((AchievementRecord) other).nextWeekBreakPoint)
                && numTaskCompletedByWeek == ((AchievementRecord) other).numTaskCompletedByWeek
                && xpValueByWeek == ((AchievementRecord) other).xpValueByWeek;
    }
}
