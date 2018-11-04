package seedu.address.testutil;

import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.storage.XmlAdaptedAchievementRecord;

/**
 * A utility class to help with building XmlAdaptedAchievementRecord objects.
 */
public class XmlAchievementRecordBuilder {

    public static final String DEFAULT_NEXT_DAY_BREAK_POINT = "13-10-19 0000";
    public static final String DEFAULT_NEXT_WEEK_BREAK_POINT = "19-10-19 0000";

    private String displayOption;
    private String xp;
    private String level;
    private String numTaskCompleted;

    private String nextDayBreakPoint;
    private String xpValueByDay;
    private String numTaskCompletedByDay;

    private String nextWeekBreakPoint;
    private String xpValueByWeek;
    private String numTaskCompletedByWeek;

    public XmlAchievementRecordBuilder() {
        this.displayOption = Integer.toString(AchievementRecord.DISPLAY_ALL_TIME);
        this.xp = "0";
        this.level = Level.LEVEL_1.toString();
        this.numTaskCompleted = "0";
        this.nextDayBreakPoint = DEFAULT_NEXT_DAY_BREAK_POINT;
        this.numTaskCompletedByDay = "0";
        this.xpValueByDay = "0";
        this.nextWeekBreakPoint = DEFAULT_NEXT_WEEK_BREAK_POINT;
        this.numTaskCompletedByWeek = "0";
        this.xpValueByWeek = "0";
    }

    /**
     * Sets the {@code displayOption} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withDisplayOption(String displayOption) {
        this.displayOption = displayOption;
        return this;
    }

    /**
     * Sets the {@code xp} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withXp(String xp) {
        this.xp = xp;
        return this;
    }

    /**
     * Sets the {@code level} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withLevel(String level) {
        this.level = level;
        return this;
    }

    /**
     * Sets the {@code nextDayBreakPoint} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withNextDayBreakPoint(String date) {
        this.nextDayBreakPoint = date;
        return this;
    }

    /**
     * Sets the {@code nextDayWeekPoint} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withNextWeekBreakPoint(String date) {
        this.nextWeekBreakPoint = date;
        return this;
    }

    /**
     * Sets the {@code numTaskCompleted} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withNumTaskCompleted(String num) {
        this.numTaskCompleted = num;
        return this;
    }

    /**
     * Sets the {@code numTaskCompletedByDay} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withNumTaskCompletedByDay(String num) {
        this.numTaskCompletedByDay = num;
        return this;
    }

    /**
     * Sets the {@code numTaskCompletedByWeek} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withNumTaskCompletedByWeek(String num) {
        this.numTaskCompletedByWeek = num;
        return this;
    }

    /**
     * Sets the {@code xpValueByDay} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withXpValueByDay(String xp) {
        this.xpValueByDay = xp;
        return this;
    }

    /**
     * Sets the {@code xpValueByWeek} of the {@code XmlAdaptedAchievementRecord} that we are building.
     */
    public XmlAchievementRecordBuilder withXpValueByWeek(String xp) {
        this.xpValueByWeek = xp;
        return this;
    }

    /**
     * Constructs an {@code XmlAdaptedAchievementRecord} with the current attributes of this {@code TaskBuilder}.
     */
    public XmlAdaptedAchievementRecord build() {
        return new XmlAdaptedAchievementRecord(displayOption, xp, level, numTaskCompleted, nextDayBreakPoint,
                xpValueByDay, numTaskCompletedByDay, nextWeekBreakPoint, xpValueByWeek, numTaskCompletedByWeek);
    }
}


