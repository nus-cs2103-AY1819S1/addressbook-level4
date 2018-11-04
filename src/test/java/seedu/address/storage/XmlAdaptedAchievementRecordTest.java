package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.achievement.AchievementRecord;
import seedu.address.model.achievement.Level;
import seedu.address.model.achievement.Xp;
import seedu.address.model.achievement.exceptions.CumulativeAchievementsMismatchException;
import seedu.address.model.achievement.exceptions.DateBreakPointsMismatchException;
import seedu.address.model.achievement.exceptions.XpLevelMismatchException;
import seedu.address.model.util.DateFormatUtil;
import seedu.address.testutil.AchievementRecordBuilder;
import seedu.address.testutil.Assert;
import seedu.address.testutil.XmlAchievementRecordBuilder;

public class XmlAdaptedAchievementRecordTest {
    private static final String NON_INT = "integer";
    private static final String NEGATIVE_INT = "-1";

    private static final String INVALID_DISPLAY_OPTION = "4";
    private static final String INVALID_LEVEL = "level 1";
    private static final String INVALID_DATE_FORMAT = "2018/11/28";

    @Test
    public void toModelType_validAchievementFields_returnsAchievementRecord() throws IllegalValueException {
        AchievementRecord validModel = new AchievementRecordBuilder().build();
        XmlAdaptedAchievementRecord rec = new XmlAdaptedAchievementRecord(validModel);
        assertEquals(rec.toModelType(), validModel);
    }

    @Test
    public void toModelType_withXpLevelMismatch_throwsIllegalValueException() {
        String expectedMessage = XpLevelMismatchException.MESSAGE;

        Level level = Level.LEVEL_5;
        int xp = level.getMinXp() - 1;
        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withXp(Integer.toString(xp))
                .withLevel(level.toString())
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        level = Level.LEVEL_4;
        xp = level.getMaxXp();
        record = new XmlAchievementRecordBuilder()
                .withXp(Integer.toString(xp))
                .withLevel(level.toString())
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_withDateBreakPointsMismatch_throwsIllegalValueException() {
        String expectedMessage = DateBreakPointsMismatchException.MESSAGE;

        // NextWeekBreakPoint can be no more than 6 days ahead of nextDayBreakPoint
        Calendar date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        String today = DateFormatUtil.FORMAT_STANDARD.format(date.getTime());

        date.add(Calendar.DAY_OF_MONTH, 7);
        String sevenDaysLater = DateFormatUtil.FORMAT_STANDARD.format(date.getTime());

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withNextDayBreakPoint(today)
                .withNextWeekBreakPoint(sevenDaysLater)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        // NextWeekBreakPoint must not be earlier than nextDayBreakPoint
        date = new GregorianCalendar();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);

        date.add(Calendar.DAY_OF_MONTH, -1);
        String ytd = DateFormatUtil.FORMAT_STANDARD.format(date.getTime());

        record = new XmlAchievementRecordBuilder()
                .withNextDayBreakPoint(today)
                .withNextWeekBreakPoint(ytd)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_withCumulativeAchievementsMismatch_throwsIllegalValueException() {
        String expectedMessage = CumulativeAchievementsMismatchException.MESSAGE;

        // total xp must not be smaller than today's xp, which must not be smaller than this week's xp
        String largest = "5";
        String middle = "4";
        String smallest = "3";
        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withXp(largest)
                .withXpValueByDay(middle)
                .withXpValueByWeek(smallest)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder()
                .withXp(smallest)
                .withXpValueByDay(middle)
                .withXpValueByWeek(largest)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        // total numTaskCompleted must not be smaller than today's numTaskCompleted,
        // which must not be smaller than this week's numTaskCompleted
        record = new XmlAchievementRecordBuilder()
                .withNumTaskCompleted(largest)
                .withNumTaskCompletedByDay(middle)
                .withNumTaskCompletedByWeek(smallest)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder()
                .withNumTaskCompleted(smallest)
                .withNumTaskCompletedByDay(middle)
                .withNumTaskCompletedByWeek(largest)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullDisplayOption_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.DISPLAY_OPTION_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withDisplayOption(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidDisplayOption_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.DISPLAY_OPTION_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withDisplayOption(NON_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withDisplayOption(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withDisplayOption(INVALID_DISPLAY_OPTION).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullXp_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
               Xp.class.getSimpleName());

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withXp(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidXp_throwsIllegalValueException() {
        String expectedMessage = Xp.MESSAGE_XP_CONSTRAINTS;

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withXp(NON_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withXp(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullLevel_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                Level.class.getSimpleName());

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withLevel(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidLevel_throwsIllegalValueException() {
        String expectedMessage = Level.MESSAGE_LEVEL_CONSTRAINTS;

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withLevel(INVALID_LEVEL).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullNumTaskCompleted_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NUM_TASK_COMPLETED_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withNumTaskCompleted(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidNumTaskCompleted_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NUM_TASK_COMPLETED_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withNumTaskCompleted(NON_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withNumTaskCompleted(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullNextDayBreakPoint_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NEXT_DAY_BREAK_POINT_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withNextDayBreakPoint(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidNextDayBreakPoint_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NEXT_DAY_BREAK_POINT_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withNextDayBreakPoint(INVALID_DATE_FORMAT)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullXpValueByDay_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.XP_VALUE_BY_DAY_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withXpValueByDay(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidXpValueByDay_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.XP_VALUE_BY_DAY_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withXpValueByDay(NON_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withXpValueByDay(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullNumTaskCompletedByDay_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NUM_TASK_COMPLETED_BY_DAY_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withNumTaskCompletedByDay(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidNumTaskCompletedByDay_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NUM_TASK_COMPLETED_BY_DAY_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withNumTaskCompletedByDay(NON_INT)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withNumTaskCompletedByDay(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullNextWeekBreakPoint_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NEXT_WEEK_BREAK_POINT_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withNextWeekBreakPoint(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidNextWeekBreakPoint_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NEXT_WEEK_BREAK_POINT_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withNextWeekBreakPoint(INVALID_DATE_FORMAT)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullXpValueByWeek_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.XP_VALUE_BY_WEEK_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withXpValueByWeek(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidXpValueByWeek_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.XP_VALUE_BY_WEEK_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withXpValueByWeek(NON_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withXpValueByWeek(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_nullNumTaskCompletedByWeek_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.MISSING_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NUM_TASK_COMPLETED_BY_WEEK_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder().withNumTaskCompletedByWeek(null).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }

    @Test
    public void toModelType_invalidNumTaskCompletedByWeek_throwsIllegalValueException() {
        String expectedMessage = String.format(XmlAdaptedAchievementRecord.INVALID_FIELD_MESSAGE_FORMAT,
                XmlAdaptedAchievementRecord.NUM_TASK_COMPLETED_BY_WEEK_FIELD);

        XmlAdaptedAchievementRecord record = new XmlAchievementRecordBuilder()
                .withNumTaskCompletedByWeek(NON_INT)
                .build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);

        record = new XmlAchievementRecordBuilder().withNumTaskCompletedByWeek(NEGATIVE_INT).build();
        Assert.assertThrows(IllegalValueException.class, expectedMessage, record::toModelType);
    }
}
