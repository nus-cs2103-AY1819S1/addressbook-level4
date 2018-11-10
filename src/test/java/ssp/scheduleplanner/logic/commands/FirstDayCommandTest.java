package ssp.scheduleplanner.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.UnmarshalException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ssp.scheduleplanner.commons.exceptions.DataConversionException;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.storage.XmlFileStorage;
import ssp.scheduleplanner.storage.XmlSerializableRangeOfWeek;

public class FirstDayCommandTest {
    private static final String INVALID_DATE_RANGE = "010101";
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlSerializableRangeOfWeekTest");
    private static final String USER_INPUT_DATE = "130818";
    private static final File CHECK_FILE_EXIST = new File("testrangeofweek.xml");
    private static final File CHECK = new File("rangeofweek.xml");
    private static final Path PATH = Paths.get("testrangeofweek.xml");
    private static final Path NULL_PATH = TEST_DATA_FOLDER.resolve("nullrangeofweek.xml");
    private static final Path DIFF_SIZE_PATH = TEST_DATA_FOLDER.resolve("diffsizerangeofweek.xml");
    private static final Path INVALID_DATE_PATH = TEST_DATA_FOLDER.resolve("invaliddaterangeofweek.xml");
    private static final Path INVALID_DATE_RANGE_PATH = TEST_DATA_FOLDER.resolve("invaliddaterangerangeofweek.xml");
    private static final Path TYPICAL_PATH = TEST_DATA_FOLDER.resolve("typicalrangeofweek.xml");
    private static final Path NON_EXIST_FILE_PATH = TEST_DATA_FOLDER.resolve("testrangeofweek.xml");
    private static final Path UNABLE_CONVERT_FILE_PATH = TEST_DATA_FOLDER.resolve("unableconvertrangeofweek.xml");

    private FirstDayCommand fds = new FirstDayCommand();
    private String[][] rangeOfWeeks = new String[FirstDayCommand.WEEKS_IN_SEMESTER][3];
    private String[][] storeRangeOfWeeks = new String[FirstDayCommand.WEEKS_IN_SEMESTER][3];

    @Before
    public void setup() {
        rangeOfWeeks[0][0] = "130818";
        rangeOfWeeks[0][1] = "190818";
        rangeOfWeeks[0][2] = "Week 1";
        rangeOfWeeks[1][0] = "200818";
        rangeOfWeeks[1][1] = "260818";
        rangeOfWeeks[1][2] = "Week 2";
        rangeOfWeeks[2][0] = "270818";
        rangeOfWeeks[2][1] = "020918";
        rangeOfWeeks[2][2] = "Week 3";
        rangeOfWeeks[3][0] = "030918";
        rangeOfWeeks[3][1] = "090918";
        rangeOfWeeks[3][2] = "Week 4";
        rangeOfWeeks[4][0] = "100918";
        rangeOfWeeks[4][1] = "160918";
        rangeOfWeeks[4][2] = "Week 5";
        rangeOfWeeks[5][0] = "170918";
        rangeOfWeeks[5][1] = "230918";
        rangeOfWeeks[5][2] = "Week 6";
        rangeOfWeeks[6][0] = "240918";
        rangeOfWeeks[6][1] = "300918";
        rangeOfWeeks[6][2] = "Recess Week";
        rangeOfWeeks[7][0] = "011018";
        rangeOfWeeks[7][1] = "071018";
        rangeOfWeeks[7][2] = "Week 7";
        rangeOfWeeks[8][0] = "081018";
        rangeOfWeeks[8][1] = "141018";
        rangeOfWeeks[8][2] = "Week 8";
        rangeOfWeeks[9][0] = "151018";
        rangeOfWeeks[9][1] = "211018";
        rangeOfWeeks[9][2] = "Week 9";
        rangeOfWeeks[10][0] = "221018";
        rangeOfWeeks[10][1] = "281018";
        rangeOfWeeks[10][2] = "Week 10";
        rangeOfWeeks[11][0] = "291018";
        rangeOfWeeks[11][1] = "041118";
        rangeOfWeeks[11][2] = "Week 11";
        rangeOfWeeks[12][0] = "051118";
        rangeOfWeeks[12][1] = "111118";
        rangeOfWeeks[12][2] = "Week 12";
        rangeOfWeeks[13][0] = "121118";
        rangeOfWeeks[13][1] = "181118";
        rangeOfWeeks[13][2] = "Week 13";
        rangeOfWeeks[14][0] = "191118";
        rangeOfWeeks[14][1] = "251118";
        rangeOfWeeks[14][2] = "Study Week";
        rangeOfWeeks[15][0] = "261118";
        rangeOfWeeks[15][1] = "021218";
        rangeOfWeeks[15][2] = "Examination Week";
        rangeOfWeeks[16][0] = "031218";
        rangeOfWeeks[16][1] = "091218";
        rangeOfWeeks[16][2] = "Examination Week";
    }

    @Test
    public void saveRangeOfWeeks_success() throws CommandException {
        if (!CHECK_FILE_EXIST.exists()) {
            try {
                CHECK_FILE_EXIST.createNewFile();
                XmlFileStorage.saveWeekDataToFile(PATH, new XmlSerializableRangeOfWeek(rangeOfWeeks));
            } catch (java.io.IOException e) {
                throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
            }
        }

        assertTrue(CHECK_FILE_EXIST.exists());
    }

    @Test
    public void retrieveRangeOfWeeks_success() throws CommandException {
        if (!CHECK_FILE_EXIST.exists()) {
            saveRangeOfWeeks_success();
        }

        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(PATH);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);
        } catch (DataConversionException e) {
            throw new CommandException(fds.MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
        }

        //check first entry first data and last entry last data if they are empty to verify if null data
        assertTrue(storeRangeOfWeeks[0][0] != null);
        assertTrue(storeRangeOfWeeks[16][2] != null);

        //check first entry first data and last entry last data if they are empty to verify if empty data
        assertTrue(storeRangeOfWeeks[0][0] != "");
        assertTrue(storeRangeOfWeeks[16][2] != "");
    }

    //The following code is referenced from: https://stackoverflow.com/questions/156503/how-do-you-assert-
    //that-a-certain-exception-is-thrown-in-junit-4-tests
    @Test(expected = FileNotFoundException.class)
    public void retrieveRangeOfWeeks_fileNotExist_throwsFileNotFoundException() throws FileNotFoundException {
        if (!CHECK_FILE_EXIST.exists()) {
            try {
                XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(PATH);
            } catch (FileNotFoundException e) {
                throw e;
            } catch (DataConversionException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void retrieveRangeOfWeeks_fileNotExist_throwsDataConversionException() throws DataConversionException {
        ExpectedException thrown = ExpectedException.none();
        if (!CHECK_FILE_EXIST.exists()) {
            try {
                thrown.expect(DataConversionException.class);
                XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(INVALID_DATE_PATH);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Test
    public void retrieveWeekDescription_emptyString_success() {
        String[][] emptyString = new String[fds.WEEKS_IN_SEMESTER][3];
        for (int i = 0; i < fds.WEEKS_IN_SEMESTER; i++) {
            emptyString[i][0] = INVALID_DATE_RANGE;
            emptyString[i][1] = INVALID_DATE_RANGE;
            emptyString[i][2] = "placeholder description";
        }

        assertTrue(fds.retrieveWeekDescription(emptyString).equals(""));

    }

    @Test
    public void isMonday_test() {
        //the following tests are Monday
        assertTrue(fds.isMonday("130818"));
        assertTrue(fds.isMonday("200818"));

        //the following tests are Tuesday to Sunday
        assertFalse(fds.isMonday("140818"));
        assertFalse(fds.isMonday("150818"));
        assertFalse(fds.isMonday("160818"));
        assertFalse(fds.isMonday("170818"));
        assertFalse(fds.isMonday("180818"));
        assertFalse(fds.isMonday("190818"));
    }

    @Test
    public void computeRangeOfWeekTest_success() {
        String[][] testUserRangeOfWeeks = new String[FirstDayCommand.WEEKS_IN_SEMESTER][3];
        testUserRangeOfWeeks = fds.computeRangeOfWeeks(USER_INPUT_DATE);

        boolean allFieldsSame = true;
        for (int i = 0; i < FirstDayCommand.WEEKS_IN_SEMESTER; i++) {
            if (!testUserRangeOfWeeks[i][0].equals(rangeOfWeeks[i][0])
                    || !testUserRangeOfWeeks[i][1].equals(rangeOfWeeks[i][1])
                    || !testUserRangeOfWeeks[i][2].equals(rangeOfWeeks[i][2])) {
                allFieldsSame = false;
            }
        }
        assertTrue(allFieldsSame);
    }

    @Test
    public void isWithinDateRange_test() {
        //system date before range
        assertFalse(isWithinDateRange("120818", "130818", "091218"));

        //system date on boundary of start date
        assertTrue(isWithinDateRange("130818", "130818", "091218"));

        //system date within both boundary of start date and end date
        assertTrue(isWithinDateRange("130818", "130818", "130818"));

        //system date inside boundary of start date and end date
        assertTrue(isWithinDateRange("211018", "130818", "091218"));

        //system date on boundary of end date
        assertTrue(isWithinDateRange("091218", "130818", "091218"));

        //system date after range
        assertFalse(isWithinDateRange("101218", "130818", "091218"));
    }

    @Test
    public void createDefaultFileIfNotExist_success() throws CommandException {
        fds.createDefaultFileIfNotExist();
        assertTrue(CHECK.exists());
        retrieveRangeOfWeeks_success();
    }

    //The following code is referenced from: https://stackoverflow.com/questions/156503/how-do-you-assert-
    //that-a-certain-exception-is-thrown-in-junit-4-tests
    @Test(expected = IndexOutOfBoundsException.class)
    public void diffSizePath_throwsIndexOutOfBoundException() throws CommandException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(DIFF_SIZE_PATH);
            assertTrue(range.returnSize() != fds.WEEKS_IN_SEMESTER);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);
        } catch (DataConversionException e) {
            throw new CommandException(fds.MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
        }
    }

    //The following code is referenced from: https://stackoverflow.com/questions/156503/how-do-you-assert-
    //that-a-certain-exception-is-thrown-in-junit-4-tests
    @Test(expected = FileNotFoundException.class)
    public void createDefaultFile_fileNotExist_throwFileNotFoundException() throws FileNotFoundException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(NON_EXIST_FILE_PATH);
        } catch (FileNotFoundException e) {
            throw e;
        } catch (DataConversionException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void createDefaultFile_invalidDataFormat_throwDataConversionException() throws DataConversionException {
        ExpectedException thrown = ExpectedException.none();
        try {
            thrown.expect(DataConversionException.class);
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(INVALID_DATE_PATH);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        }
    }

    @Test
    public void nullPath_throwsCommandException() throws CommandException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(NULL_PATH);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);
            assertFalse(range.checkIfNullValueFromStorage());
        } catch (DataConversionException e) {
            throw new CommandException(fds.MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
        }
    }

    @Test
    public void invalidDatePath_throwsCommandException() throws CommandException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(INVALID_DATE_PATH);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);
            assertFalse(range.checkIfValidDateOrRangeFromStorage());
        } catch (DataConversionException e) {
            throw new CommandException(fds.MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
        }
    }

    @Test
    public void invalidDateRangePath_throwsCommandException() throws CommandException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(INVALID_DATE_RANGE_PATH);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);
            assertFalse(range.checkIfValidDateOrRangeFromStorage());
        } catch (DataConversionException e) {
            throw new CommandException(fds.MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
        }
    }

    @Test
    public void unableConvertFilePath_invalidData_throwsUnmarshalException() throws UnmarshalException {
        ExpectedException thrown = ExpectedException.none();
        try {
            thrown.expect(UnmarshalException.class);
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(UNABLE_CONVERT_FILE_PATH);
        } catch (DataConversionException e) {
            System.out.println(e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void typicalPath_success() throws CommandException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(TYPICAL_PATH);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);

            assertTrue(range.returnSize() == fds.WEEKS_IN_SEMESTER);
            assertTrue(range.checkIfNullValueFromStorage());
            assertTrue(range.checkIfValidDateOrRangeFromStorage());
        } catch (DataConversionException e) {
            throw new CommandException(fds.MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(fds.MESSAGE_FILE_DOES_NOT_EXIST);
        }
    }

    @Test
    public void computeAppTitle_test() throws CommandException {
        String appTitle = "Student Schedule Planner";

        if (fds.isWithinDateRange(rangeOfWeeks[0][0], rangeOfWeeks[16][1])) {
            appTitle = "Student Schedule Planner" + "  - " + fds.retrieveWeekDescription(rangeOfWeeks);
        }
        
        //if system time is within date range, appTitle should not be "Student Schedule Planner"
        //else, appTitle should be "Student Schedule Planner"
        if (fds.isWithinDateRange(rangeOfWeeks[0][0], rangeOfWeeks[16][1])) {
            assertFalse(appTitle.equals("Student Schedule Planner"));
        } else {
            assertTrue(appTitle.equals("Student Schedule Planner"));
        }
    }

    @After
    public void close() {
        if (CHECK_FILE_EXIST.exists()) {
            CHECK_FILE_EXIST.delete();
        }
    }

    /**
     * Modification of actual isWithinDateRange in FirstDayCommand.java
     * Add a parameter for "testing" system date
     *
     * @param firstDayOfSem the first day of the semester
     * @param lastDayOfSem the last day of the semester
     * @return true or false
     */
    private boolean isWithinDateRange(String systemTestDate, String firstDayOfSem, String lastDayOfSem) {
        LocalDate firstDate = LocalDate.parse(firstDayOfSem, DateTimeFormatter.ofPattern("ddMMyy"));
        LocalDate lastDate = LocalDate.parse(lastDayOfSem, DateTimeFormatter.ofPattern("ddMMyy"));
        LocalDate systemDate = LocalDate.parse(systemTestDate, DateTimeFormatter.ofPattern("ddMMyy"));
        return (systemDate.isEqual(firstDate) || systemDate.isAfter(firstDate) && (systemDate.isBefore(lastDate)
                || systemDate.isEqual(lastDate)));
    }

}
