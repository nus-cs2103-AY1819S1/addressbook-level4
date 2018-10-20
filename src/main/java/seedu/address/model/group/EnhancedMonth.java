package seedu.address.model.group;

import static seedu.address.model.group.TimeStamp.MESSAGE_TIMESTAMP_CONSTRAINT;

import java.time.DateTimeException;
import java.time.Month;
import java.time.Year;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/* @@author Pakorn */

/**
 * This is a modified version of the Java Month class with support for detecting nonexistent dates.
 */
public class EnhancedMonth {

    private Month month;

    public EnhancedMonth(Month month) {
        this.month = month;
    }

    public Index getMonthIndex() {
        return Index.fromOneBased(month.getValue());
    }

    /**
     * Create an {@code EnhancedMonth} from month index.
     */
    public static EnhancedMonth fromMonthIndex(int index) throws ParseException {
        try {
            return new EnhancedMonth(Month.of(index));
        } catch (DateTimeException e) {
            throw new ParseException(MESSAGE_TIMESTAMP_CONSTRAINT);
        }
    }

    public Integer getLength(Integer year) {
        switch(month) {
        case FEBRUARY:
            return Year.isLeap(year) ? 29 : 28;
        case APRIL:
        case JUNE:
        case SEPTEMBER:
        case NOVEMBER:
            return 30;
        default:
            return 31;
        }
    }

}
