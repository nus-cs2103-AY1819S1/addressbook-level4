package seedu.address.testutil;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

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

    private final Calendar currentDate = new GregorianCalendar();

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

    public AchievementRecordBuilder() {
        this.displayOption = AchievementRecord.DISPLAY_ALL_TIME;
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

        displayOption = newData.getDisplayOption();
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

    public static Calendar getCalendarFromString(String source) {
        final Calendar result = Calendar.getInstance();
        result.setTime(DateFormatUtil.parseDate(source));
        return result;
    }

    /**
     * Sets the {@code displayOption} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withDisplayOption(int displayOption) {
        this.displayOption = displayOption;
        return this;
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
     * Sets the {@code nextDayBreakPoint} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNextDayBreakPoint(Calendar date) {
        this.nextDayBreakPoint = date;
        return this;
    }

    /**
     * Sets the {@code nextDayBreakPoint} of the {@code AchievementRecord} to be the next day of current date.
     */
    public AchievementRecordBuilder withNextDayBreakPointFromNow() {
        Calendar date = (GregorianCalendar) this.currentDate.clone();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.add(Calendar.DAY_OF_MONTH, 1);
        this.nextDayBreakPoint = date;
        return this;
    }

    /**
     * Sets the {@code nextWeekBreakPoint} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNextWeekBreakPoint(String dateString) {
        this.nextWeekBreakPoint = getCalendarFromString(dateString);
        return this;
    }

    /**
     * Sets the {@code nextDayWeekPoint} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNextWeekBreakPoint(Calendar date) {
        this.nextWeekBreakPoint = date;
        return this;
    }

    /**
     * Sets the {@code nextWeekBreakPoint} of the {@code AchievementRecord} to be the next week of current date.
     */
    public AchievementRecordBuilder withNextWeekBreakPointFromNow() {
        Calendar date = (GregorianCalendar) this.currentDate.clone();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.add(Calendar.DAY_OF_MONTH, 7);
        this.nextWeekBreakPoint = date;
        return this;
    }

    /**
     * Sets the {@code numTaskCompleted} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNumTaskCompleted(int num) {
        this.numTaskCompleted = num;
        return this;
    }

    /**
     * Sets the {@code numTaskCompletedByDay} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNumTaskCompletedByDay(int num) {
        this.numTaskCompletedByDay = num;
        return this;
    }

    /**
     * Sets the {@code numTaskCompletedByWeek} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withNumTaskCompletedByWeek(int num) {
        this.numTaskCompletedByWeek = num;
        return this;
    }

    /**
     * Sets the {@code xpValueByDay} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withXpValueByDay(int xp) {
        this.xpValueByDay = xp;
        return this;
    }

    /**
     * Sets the {@code xpValueByWeek} of the {@code AchievementRecord} that we are building.
     */
    public AchievementRecordBuilder withXpValueByWeek(int xp) {
        this.xpValueByWeek = xp;
        return this;
    }

    /**
     * Constructs an {@code AchievementRecord} with the current attributes of this {@code TaskBuilder}.
     */
    public AchievementRecord build() {
        return new AchievementRecord(displayOption, xp, level, numTaskCompleted, nextDayBreakPoint,
                numTaskCompletedByDay, xpValueByDay, nextWeekBreakPoint, numTaskCompletedByWeek, xpValueByWeek);
    }
}

