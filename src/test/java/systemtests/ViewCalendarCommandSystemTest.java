package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.FOUR_DIGIT_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.OCTOBER_COMMAND_INPUT;
import static seedu.address.logic.commands.CommandTestUtil.UPPER_CASE_MONTH;

import java.io.IOException;
import java.util.Locale;

import org.junit.Test;

import jfxtras.icalendarfx.VCalendar;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import seedu.address.logic.commands.ViewCalendarCommand;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.storage.Storage;

public class ViewCalendarCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void view() {
        try {
            Storage storage = getStorage();
            Month month = new Month("OCT");
            Year year = new Year("2018");
            String calendarName = month + "-" + year;

            /*
             * Case : View existing calendar OCT-2018.ics
             */
            Calendar octCalendar = storage.loadCalendar(calendarName);
            String command = ViewCalendarCommand.COMMAND_WORD + OCTOBER_COMMAND_INPUT + FOUR_DIGIT_YEAR;
            VCalendar vcalendar = parseCalendar(octCalendar);
            assertCommandSuccess(command, month, year, vcalendar);

            /*
             * Case : View not existing calendar JAN-2018.ics
             */
            command = ViewCalendarCommand.COMMAND_WORD + UPPER_CASE_MONTH + FOUR_DIGIT_YEAR;
            assertCommandFailure(command, ViewCalendarCommand.MESSAGE_NOT_EXISTING_CALENDAR, vcalendar);
        } catch (IOException | ParserException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the {@code ViewCalendarCommand} that loads an .ics file from storage and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing {@code ViewCalendarCommand} with the details of
     * {@code calendar}.<br>
     * 4. Browser url and selected card remain unchanged.<br>
     * 5. Calendar Panel displays the correct Calendar. <br>
     * 6. Status bar's sync status changes.<br>
     * Verifications 1, 3 and 5 are performed by
     * {@code AddressBookSystemTest#assertCalendarDisplaysExpected(String, String, VCalendar)}.<br>
     *
     * @see AddressBookSystemTest#assertCalendarDisplaysExpected(String, String, VCalendar)
     */
    private void assertCommandSuccess(String command, Month month, Year year, VCalendar calendar) {
        String expectedResultMessage = String.format(ViewCalendarCommand.MESSAGE_SUCCESS, month + "-" + year + ".ics");
        assertCommandSuccess(command, expectedResultMessage, calendar);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Month, Year, VCalendar)}.
     * Executes {@code command} instead.
     *
     * @see ViewCalendarCommandSystemTest#assertCommandSuccess(String, Month, Year, VCalendar)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, VCalendar expectedCalendar) {
        executeCommand(command);
        assertCalendarDisplaysExpected("", expectedResultMessage, expectedCalendar);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. Browser url, selected card and status bar remain unchanged.<br>
     * 5. Calendar panel and calendar remain unchanged. <br>
     * Verifications 1, 3 and 5 are performed by
     * {@code AddressBookSystemTest#assertCalendarDisplaysExpected(String, String, VCalendar)}.<br>
     *
     * @see AddressBookSystemTest#assertCalendarDisplaysExpected(String, String, VCalendar)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, VCalendar expectedCalendar) {
        executeCommand(command);
        assertCalendarDisplaysExpected(command, expectedResultMessage, expectedCalendar);
        assertCalendarUnchanged();
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Checks if the operating system is windows.
     */
    private boolean isWindowsOs() {
        String osName = System.getProperty("os.name");
        if (osName == null) {
            return false;
        }
        osName = osName.toLowerCase(Locale.ENGLISH);
        return osName.contains("windows");
    }

    /**
     * Parse an ical4j Calendar into its VCalendar format.
     */
    private VCalendar parseCalendar(Calendar calendar) {
        String content = calendar.toString();
        if (!isWindowsOs()) {
            content = content.replace("\r\n", System.getProperty("line.separator"));
        }

        return VCalendar.parse(content);
    }

}
