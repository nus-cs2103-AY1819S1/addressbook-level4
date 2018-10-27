package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;

import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.achievement.Xp;
import seedu.address.model.util.DateFormatUtil;

/**
 * A utility class to help with building AchievementRecord objects.
 */
public class AchievementRecordBuilder {
    
    public static final String DEFAULT_NEXT_DAY_BREAK_POINT = "13-10-19 0000";
    public static final String DEFAULT_NEXT_WEEK_BREAK_POINT = "19-10-19 0000";

    private Xp xp;
    private Level level;
    private int numTaskCompleted;

    private Calendar nextDayBreakPoint;
    private int xpValueByDay;
    private int numTaskCompletedByDay;

    private Calendar nextWeekBreakPoint;
    private int xpValueByWeek;
    private int numTaskCompletedByWeek;

    public AchievementRecordBuilder() {
        this.xp = new Xp();
        this.level = Level.LEVEL_1;
        this.numTaskCompleted = 0;
        this.nextDayBreakPoint = getCalendarFromString(DEFAULT_NEXT_DAY_BREAK_POINT);
        this.numTaskCompletedByDay = 0;
        this.xpValueByDay = 0;
        this.nextWeekBreakPoint = getCalendarFromString(DEFAULT_NEXT_WEEK_BREAK_POINT);
        this.numTaskCompletedByWeek = 0;
        this.xpValueByWeek = 0;
    }

    /**
     * Initializes the AchievementRecordBuilder with the data of {@code newData}.
     */
    public AchievementRecordBuilder(AchievementRecord newData) {
        requireNonNull(newData);

        xp = newData.getXp();
        level = newData.getLevel();
        numTaskCompleted = newData.getNumTaskCompleted();
        nextDayBreakPoint = newData.getNextDayBreakPoint();
        numTaskCompletedByDay = newData.getNumTaskCompletedByDay();
        xpValueByDay = newData.getXpValueByDay();
        nextWeekBreakPoint = newData.getNextWeekBreakPoint();
        numTaskCompletedByWeek = newData.getNumTaskCompletedByWeek();
        xpValueByWeek = newData.getXpValueByWeek();
    }

    private static Calendar getCalendarFromString(String source) {
        final Calendar result = Calendar.getInstance();
        result.setTime(DateFormatUtil.parseDate(source));
        return result;
    }

    /**
     * Parse the integer {@code xpValue} into {@code Xp} and sets it to the
     * {@code xp} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withXpValue(int xpValue) {
        this.xp = new Xp(xpValue);
        return this;
    }

    /**
     * Sets the {@code xp} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withXp(Xp xp) {
        this.xp = xp;
        return this;
    }

    /**
     * Sets the {@code level} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withLevel(Level level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the {@code nextDayBreakPoint} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNextDayBreakPoint(String dateString) {
        this.nextDayBreakPoint = getCalendarFromString(dateString);
        return this;
    }

    /**
     * Sets the {@code nextWeekBreakPoint} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNextWeekBreakPoint(String dateString) {
        this.nextWeekBreakPoint = getCalendarFromString(dateString);
        return this;
    }

    public AchievementRecord build() {
        return new AchievementRecord(xp, level, numTaskCompleted, nextDayBreakPoint, numTaskCompletedByDay,
                xpValueByDay, nextWeekBreakPoint, numTaskCompletedByWeek, xpValueByWeek);
    }
}

