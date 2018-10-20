package ssp.scheduleplanner.logic.commands;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.xml.bind.JAXBException;

import ssp.scheduleplanner.commons.exceptions.DataConversionException;
import ssp.scheduleplanner.commons.util.XmlUtil;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.storage.XmlFileStorage;
import ssp.scheduleplanner.storage.XmlSerializableRangeOfWeek;

/**
 * Mark the first day of the semester to be able to compute the range of date of all semester weeks and save the range.
 */
public class FirstDayCommand extends Command {

    public static final String COMMAND_WORD = "firstday";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the first monday of the semester. "
            + "Date should be in ddmmyy format\nExample: " + COMMAND_WORD + " " + "130818";
    public static final String MESSAGE_SUCCESS = "First day of semester saved successfully";
    public static final String MESSAGE_ONLY_ONE_ARGUMENT = "FirstDay command only accept one set of date";
    public static final String MESSAGE_INVALID_DATE = "Invalid date or date format\n"
            + "Date should be in ddmmyy format\nExample: " + COMMAND_WORD + " " + "130818";;
    public static final String MESSAGE_NOT_MONDAY = "Date given is not a Monday";
    public static final String MESSAGE_FILE_DOES_NOT_EXIST = "Unable to save range of dates of semester as "
            + "default file is missing";
    public static final String MESSAGE_DATA_UNABLE_CONVERT = "Data unable to convert from saved file";

    private static final int WEEKS_IN_SEMESTER = 17;
    private final String inputDate;
    private String[][] rangeOfWeek = new String[WEEKS_IN_SEMESTER][3];
    private Path path = Paths.get("rangeofweek.xml");

    public FirstDayCommand(String userInputDate) {
        this.inputDate = userInputDate;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (!Date.isValidDate(inputDate)) {
            throw new CommandException(MESSAGE_INVALID_DATE);
        }

        if (LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("ddMMyy")).getDayOfWeek().name() != "MONDAY") {
            throw new CommandException(MESSAGE_NOT_MONDAY);
        }

        computeRangeOfWeeks(inputDate);

        try {
            XmlFileStorage.saveWeekDataToFile(path, new XmlSerializableRangeOfWeek(rangeOfWeek));
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_DOES_NOT_EXIST);
        }

        String[][] test = new String[WEEKS_IN_SEMESTER][3];
        test = retrieveRangeOfWeeks(test);

        for (int i = 0; i < 17; i++) {
            System.out.printf(test[i][0]);
            System.out.printf(" ");
            System.out.printf(test[i][1]);
            System.out.printf(" ");
            System.out.println(test[i][2]);
        }

        throw new CommandException("success");
    }

    /**
     * This method retrieve the rangeOfWeeks
     * @throws DataConversionException
     * @throws FileNotFoundException
     */
    public String[][] retrieveRangeOfWeeks (String[][] storeRangeOfWeeks) throws CommandException {
        try {
            XmlSerializableRangeOfWeek range = XmlFileStorage.loadWeekDataFromSaveFile(path);
            storeRangeOfWeeks = range.convertRangeOfWeeksToString2dArray(range);
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_DATA_UNABLE_CONVERT);
        } catch (FileNotFoundException e) {
            throw new CommandException(MESSAGE_FILE_DOES_NOT_EXIST);
        }
        return storeRangeOfWeeks;
    }

    private XmlSerializableRangeOfWeek getDataFromFile (Path file) throws
            DataConversionException, FileNotFoundException {
        try {
            return XmlUtil.getDataFromFile(path, XmlSerializableRangeOfWeek.class);
        } catch (JAXBException e) {
            throw new DataConversionException(e);
        }
    }

    /**
     * This method compute the range of weeks for one semester based on the first Monday.
     * @param firstDay
     */
    private void computeRangeOfWeeks(String firstDay) {
        for (int i = 0; i < WEEKS_IN_SEMESTER; i++) {
            String startOfWeekDate = LocalDate.parse(firstDay, DateTimeFormatter.ofPattern("ddMMyy")).plusDays(7 * i)
                    .format(DateTimeFormatter.ofPattern("ddMMyy"));
            String endOfWeekDate = LocalDate.parse(firstDay, DateTimeFormatter.ofPattern("ddMMyy")).plusDays(7 * i + 6)
                    .format(DateTimeFormatter.ofPattern("ddMMyy"));
            rangeOfWeek[i][0] = startOfWeekDate;
            rangeOfWeek[i][1] = endOfWeekDate;
        }

        addDescriptionForWeeks(rangeOfWeek);

        /*
        for (int i = 0; i < WEEKS_IN_SEMESTER; i++) {
            System.out.printf(rangeOfWeek[i][0]);
            System.out.printf(" ");
            System.out.printf(rangeOfWeek[i][1]);
            System.out.printf(" ");
            System.out.println(rangeOfWeek[i][2]);
        }
        */

    }

    /**
     * Helper method to insert description of each week in the 2d String array
     * @param rangeOfWeek
     */
    private void addDescriptionForWeeks(String[][] rangeOfWeek) {
        rangeOfWeek[0][2] = "Week 1";
        rangeOfWeek[1][2] = "Week 2";
        rangeOfWeek[2][2] = "Week 3";
        rangeOfWeek[3][2] = "Week 4";
        rangeOfWeek[4][2] = "Week 5";
        rangeOfWeek[5][2] = "Week 6";
        rangeOfWeek[6][2] = "Recess Week";
        rangeOfWeek[7][2] = "Week 7";
        rangeOfWeek[8][2] = "Week 8";
        rangeOfWeek[9][2] = "Week 9";
        rangeOfWeek[10][2] = "Week 10";
        rangeOfWeek[11][2] = "Week 11";
        rangeOfWeek[12][2] = "Week 12";
        rangeOfWeek[13][2] = "Week 13";
        rangeOfWeek[14][2] = "Study Week";
        rangeOfWeek[15][2] = "Examination Week";
        rangeOfWeek[16][2] = "Examination Week";
    }

}
