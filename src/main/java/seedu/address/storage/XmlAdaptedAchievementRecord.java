package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.achievement.Xp;
import seedu.address.model.util.DateFormatUtil;

/**
 * JAXB-friendly version of the {@code AchievementRecord}.
 */
public class XmlAdaptedAchievementRecord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Achievement record's %s field is missing!";
    public static final String INVALID_FIELD_MESSAGE_FORMAT = "Achievement record's %s field is invalid!";

    public static final String DISPLAY_OPTION_FIELD = "displayOption";
    public static final String NUM_TASK_COMPLETED_FIELD = "numTaskCompleted";

    public static final String NEXT_DAY_BREAK_POINT_FIELD = "nextDayBreakPoint";
    public static final String NUM_TASK_COMPLETED_BY_DAY_FIELD = "numTaskCompletedByDay";
    public static final String XP_VALUE_BY_DAY_FIELD = "xpValueByDay";

    public static final String NEXT_WEEK_BREAK_POINT_FIELD = "nextWeekBreakPoint";
    public static final String NUM_TASK_COMPLETED_BY_WEEK_FIELD = "numTaskCompletedByWeek";
    public static final String XP_VALUE_BY_WEEK_FIELD = "xpValueByWeek";

    @XmlElement(required = true)
    private String displayOption;
    @XmlElement(required = true)
    private String xp;
    @XmlElement(required = true)
    private String level;
    @XmlElement(required = true)
    private String numTaskCompleted;

    @XmlElement(required = true)
    private String nextDayBreakPoint;
    @XmlElement(required = true)
    private String xpValueByDay;
    @XmlElement(required = true)
    private String numTaskCompletedByDay;

    @XmlElement(required = true)
    private String nextWeekBreakPoint;
    @XmlElement(required = true)
    private String xpValueByWeek;
    @XmlElement(required = true)
    private String numTaskCompletedByWeek;

    /**
     * Constructs an {@code XmlAdaptedAchievementRecord}.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAchievementRecord() {}

    /**
     * Converts a given AchievementRecord into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created {@code XmlAdaptedAchievementRecord}.
     */
    public XmlAdaptedAchievementRecord(AchievementRecord source) {
        requireNonNull(source);

        displayOption = Integer.toString(source.getDisplayOption());
        xp = Integer.toString(source.getXpValue());
        level = source.getLevel().toString();
        numTaskCompleted = Integer.toString(source.getNumTaskCompleted());

        Date nextDay = source.getNextDayBreakPoint().getTime();
        nextDayBreakPoint = DateFormatUtil.FORMAT_STANDARD.format(nextDay);
        numTaskCompletedByDay = Integer.toString(source.getNumTaskCompletedByDay());
        xpValueByDay = Integer.toString(source.getXpValueByDay());

        Date nextWeek = source.getNextWeekBreakPoint().getTime();
        nextWeekBreakPoint = DateFormatUtil.FORMAT_STANDARD.format(nextWeek);
        numTaskCompletedByWeek = Integer.toString(source.getNumTaskCompletedByWeek());
        xpValueByWeek = Integer.toString(source.getXpValueByWeek());
    }

    /**
     * Constructs an {@code XmlAdaptedAchievementRecord} with the given achievement details.
     */
    public XmlAdaptedAchievementRecord(String displayOption, String xp, String level, String numTaskCompleted,
                                       String nextDayBreakPoint, String xpValueByDay, String numTaskCompletedByDay,
                                       String nextWeekBreakPoint, String xpValueByWeek, String numTaskCompletedByWeek) {
        this.displayOption = displayOption;
        this.xp = xp;
        this.level = level;
        this.numTaskCompleted = numTaskCompleted;
        this.nextDayBreakPoint = nextDayBreakPoint;
        this.xpValueByDay = xpValueByDay;
        this.numTaskCompletedByDay = numTaskCompletedByDay;
        this.nextWeekBreakPoint = nextWeekBreakPoint;
        this.xpValueByWeek = xpValueByWeek;
        this.numTaskCompletedByWeek = numTaskCompletedByWeek;
    }

    /**
     * Converts this jaxb-friendly {@code XmlAdaptedAchievementRecord} object into the model's
     * {@code AchievementRecord} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted achievement record.
     */
    public AchievementRecord toModelType() throws IllegalValueException {

        final int modelDisplayOption = stringToDisplayOptionModelField(displayOption);
        final Xp modelXp = stringToXpModelField(xp);
        final Level modelLevel = stringToLevelModelField(level);
        final int modelNumTaskCompleted = stringToIntModelField(numTaskCompleted, NUM_TASK_COMPLETED_FIELD);

        final GregorianCalendar modelNextDayBreakPoint =
                stringToCalendarModelField(nextDayBreakPoint, NEXT_DAY_BREAK_POINT_FIELD);
        final int modelNumTaskCompletedByDay = stringToIntModelField(numTaskCompletedByDay,
                NUM_TASK_COMPLETED_BY_DAY_FIELD);
        final int modelXpValueByDay = stringToIntModelField(xpValueByDay, XP_VALUE_BY_DAY_FIELD);

        final GregorianCalendar modelNextWeekBreakPoint =
                stringToCalendarModelField(nextWeekBreakPoint, NEXT_WEEK_BREAK_POINT_FIELD);
        final int modelNumTaskCompletedByWeek = stringToIntModelField(numTaskCompletedByWeek,
                NUM_TASK_COMPLETED_BY_WEEK_FIELD);
        final int modelXpValueByWeek = stringToIntModelField(xpValueByWeek, XP_VALUE_BY_WEEK_FIELD);

        try {
            return new AchievementRecord(modelDisplayOption, modelXp, modelLevel, modelNumTaskCompleted,
                    modelNextDayBreakPoint, modelNumTaskCompletedByDay, modelXpValueByDay, modelNextWeekBreakPoint,
                    modelNumTaskCompletedByWeek, modelXpValueByWeek);
        } catch (RuntimeException ex) {
            throw new IllegalValueException(ex.getMessage());
        }
    }

    /**
     * Converts the jaxb-friendly {@code source} string into the model's {@code displayOption} field.
     *
     * @throws IllegalValueException if the string cannot be convert to valid {@code displayOption}.
     */
    private int stringToDisplayOptionModelField(String source) throws IllegalValueException {
        final int result = stringToIntModelField(source, DISPLAY_OPTION_FIELD);
        if (!AchievementRecord.isValidDisplayOption(result)) {
            throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, DISPLAY_OPTION_FIELD));
        }
        return result;
    }

    /**
     * Converts the jaxb-friendly {@code source} string into the model's {@code Xp} object.
     *
     * @throws IllegalValueException if the string cannot be convert to valid {@code Xp}.
     */
    private Xp stringToXpModelField(String source) throws IllegalValueException {
        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Xp.class.getSimpleName()));
        }
        Integer xpValue;
        try {
            xpValue = Integer.valueOf(source);
        } catch (NumberFormatException nfex) {
            throw new IllegalValueException(Xp.MESSAGE_XP_CONSTRAINTS);
        }
        if (!Xp.isValidXp(xpValue)) {
            throw new IllegalValueException(Xp.MESSAGE_XP_CONSTRAINTS);
        }
        return new Xp(xpValue);
    }

    /**
     * Converts the jaxb-friendly {@code source} string into the model's {@code Level} object.
     *
     * @throws IllegalValueException if the string cannot be convert to valid {@code Level}.
     */
    private Level stringToLevelModelField(String source) throws IllegalValueException {
        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Level.class.getSimpleName()));
        }
        if (!Level.isValidLevel(source)) {
            throw new IllegalValueException(Level.MESSAGE_LEVEL_CONSTRAINTS);
        }
        return Level.fromString(source);
    }

    /**
     * Converts the jaxb-friendly {@code source} string into integer type to be used as values of model's fields.
     *
     * @throws IllegalValueException if the string cannot be convert to valid integer.
     */
    private int stringToIntModelField(String source, String fieldName) throws IllegalValueException {
        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName));
        }
        final int result;
        try {
            result = Integer.valueOf(source);
        } catch (NumberFormatException nfex) {
            throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, fieldName));
        }
        if (result < 0) {
            throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, fieldName));
        }
        return result;
    }

    /**
     * Converts the jaxb-friendly {@code source} string into the model's {@code Calendar} object.
     *
     * @throws IllegalValueException if the string cannot be convert to valid {@code Calendar}.
     */
    private GregorianCalendar stringToCalendarModelField(String source, String fieldName) throws IllegalValueException {
        if (source == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, fieldName));
        }
        if (!DateFormatUtil.isValidDateFormat(source)) {
            throw new IllegalValueException(String.format(INVALID_FIELD_MESSAGE_FORMAT, fieldName));
        }
        final Calendar result = Calendar.getInstance();
        result.setTime(DateFormatUtil.parseDate(source));
        return (GregorianCalendar) result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAchievementRecord)) {
            return false;
        }

        XmlAdaptedAchievementRecord otherRecord = (XmlAdaptedAchievementRecord) other;
        return Objects.equals(displayOption, otherRecord.displayOption)
                && Objects.equals(xp, otherRecord.xp)
                && Objects.equals(level, otherRecord.level)
                && Objects.equals(numTaskCompleted, otherRecord.numTaskCompleted)
                && Objects.equals(nextDayBreakPoint, otherRecord.nextDayBreakPoint)
                && Objects.equals(numTaskCompletedByDay, otherRecord.numTaskCompletedByDay)
                && Objects.equals(xpValueByDay, otherRecord.xpValueByDay)
                && Objects.equals(nextWeekBreakPoint, otherRecord.nextWeekBreakPoint)
                && Objects.equals(numTaskCompletedByWeek, otherRecord.numTaskCompletedByDay)
                && Objects.equals(xpValueByWeek, otherRecord.xpValueByWeek);
    }
}
