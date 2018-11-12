package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HEAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARKS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROOM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHOOL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_MIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VICE_HEAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.address.testutil.TypicalEntries.getEntryCompetition1;
import static seedu.address.testutil.TypicalEntries.getEntryEquipment1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Entry;
import seedu.address.testutil.EditCcaDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CARL = "Carl Tan";
    public static final String VALID_NAME_DAVID = "David Lee";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ROOM_AMY = "B124";
    public static final String VALID_ROOM_BOB = "A410";
    public static final String VALID_SCHOOL_AMY = "SDE";
    public static final String VALID_SCHOOL_BOB = "Biz";
    public static final String VALID_TAG_AMY = "Handball";
    public static final String VALID_TAG_BOB = "Floorball";

    // Calendar Fields
    public static final Month VALID_MONTH_JAN = new Month("JAN");
    public static final Month VALID_MONTH_FEB = new Month("FEB");
    public static final Month VALID_MONTH_JUN = new Month("JUN");
    public static final Year LEAP_YEAR = new Year("2016");
    public static final Year VALID_YEAR_2018 = new Year("2018");
    public static final Year VALID_YEAR_2017 = new Year("2017");
    public static final int VALID_CALENDAR_DATE_1 = 1;
    public static final int VALID_CALENDAR_DATE_2 = 2;
    public static final int INVALID_CALENDAR_DATE = 32;
    public static final int VALID_SHOUR = 8;
    public static final int VALID_SMIN = 0;
    public static final int VALID_EHOUR = 17;
    public static final int VALID_EMIN = 30;
    public static final String VALID_CALENDAR_TITLE_OCAMP = "Orientation Camp";
    public static final String VALID_CALENDAR_TITLE_HACK = "Hackathon";

    public static final String VALID_STRING_JAN = "JAN";
    public static final String VALID_STRING_YEAR = "2018";
    public static final String VALID_STRING_DATE_1 = "1";
    public static final String VALID_STRING_DATE_2 = "2";
    public static final String LOWER_CASE_MONTH = " " + PREFIX_MONTH + "jan";
    public static final String MIX_CASE_MONTH = " " + PREFIX_MONTH + "jAn";
    public static final String UPPER_CASE_MONTH = " " + PREFIX_MONTH + "JAN";
    public static final String OCTOBER_COMMAND_INPUT = " " + PREFIX_MONTH + "OCT";
    public static final String FOUR_DIGIT_YEAR = " " + PREFIX_YEAR + VALID_STRING_YEAR;
    public static final String ONE_DIGIT_DATE = " " + PREFIX_DATE + VALID_STRING_DATE_1;
    public static final String ONE_DIGIT_SDATE = " " + PREFIX_START_DATE + VALID_CALENDAR_DATE_1;
    public static final String ONE_DIGIT_EDATE = " " + PREFIX_END_DATE + VALID_CALENDAR_DATE_2;
    public static final String EIGHT_START_HOUR = " " + PREFIX_START_HOUR + VALID_SHOUR;
    public static final String ZERO_START_MIN = " " + PREFIX_START_MIN + VALID_SMIN;
    public static final String SEVENTEEN_END_HOUR = " " + PREFIX_END_HOUR + VALID_EHOUR;
    public static final String THIRTY_END_MIN = " " + PREFIX_END_MIN + VALID_EMIN;
    public static final String USER_INPUT_TITLE = " " + PREFIX_TITLE + VALID_CALENDAR_TITLE_OCAMP;

    public static final String INVALID_MONTH_CHAR_NO = " " + PREFIX_MONTH + "January";
    public static final String INVALID_MONTH_VALUE = " " + PREFIX_MONTH + "Mov";
    public static final String INVALID_NEGATIVE_YEAR = " " + PREFIX_YEAR + "-2018";
    public static final String INVALID_YEAR_MORE_THAN_FOUR_DIGIT = " " + PREFIX_YEAR + "12345";
    public static final String INVALID_YEAR_LESS_THAN_FOUR_DIGIT = " " + PREFIX_YEAR + "1";
    public static final String INVALID_DATE_MORE_THAN_31 = " " + PREFIX_DATE + "32";
    public static final String INVALID_DATE_ZERO = " " + PREFIX_DATE + "0";
    public static final String INVALID_DATE_NEGATIVE = " " + PREFIX_DATE + "-1";
    public static final String INVALID_SDATE_MORE_THAN_31 = " " + PREFIX_START_DATE + "32";
    public static final String INVALID_SDATE_ZERO = " " + PREFIX_START_DATE + "0";
    public static final String INVALID_SDATE_NEGATIVE = " " + PREFIX_START_DATE + "-1";
    public static final String INVALID_EDATE_MORE_THAN_31 = " " + PREFIX_END_DATE + "32";
    public static final String INVALID_EDATE_ZERO = " " + PREFIX_END_DATE + "0";
    public static final String INVALID_EDATE_NEGATIVE = " " + PREFIX_END_DATE + "-1";
    public static final String INVALID_EMPTY_TITLE = " " + PREFIX_TITLE + " ";
    public static final int INVALID_HOUR_OUTOFBOUND = 24;
    public static final int INVALID_MIN_OUTOFBOUND = 60;
    public static final int INVALID_HOUR_NEG = -1;
    public static final int INVALID_MIN_NEG = -1;
    public static final String INVALID_SHOUR_OUTOFBOUND = " " + PREFIX_START_HOUR + INVALID_HOUR_OUTOFBOUND;
    public static final String INVALID_SHOUR_NEGATIVE = " " + PREFIX_START_HOUR + INVALID_HOUR_NEG;
    public static final String INVALID_SMIN_OUTOFBOUND = " " + PREFIX_START_MIN + INVALID_MIN_OUTOFBOUND;
    public static final String INVALID_SMIN_NEGATIVE = " " + PREFIX_START_MIN + INVALID_MIN_NEG;
    public static final String INVALID_EHOUR_OUTOFBOUND = " " + PREFIX_END_HOUR + INVALID_HOUR_OUTOFBOUND;
    public static final String INVALID_EHOUR_NEGATIVE = " " + PREFIX_END_HOUR + INVALID_HOUR_NEG;
    public static final String INVALID_EMIN_OUTOFBOUND = " " + PREFIX_END_MIN + INVALID_MIN_OUTOFBOUND;
    public static final String INVALID_EMIN_NEGATIVE = " " + PREFIX_END_MIN + INVALID_MIN_NEG;

    public static final String VALID_EMAIL_EXCURSION = "excursion@example.com";
    public static final String VALID_EMAIL_CAMP = "camp@example.com";
    public static final String VALID_CONTENT_EXCURSION = "We have an excursion on Monday";
    public static final String VALID_CONTENT_CAMP = "We have a camp on Tuesday";
    public static final String VALID_SUBJECT_EXCURSION = "Excursion";
    public static final String VALID_SUBJECT_CAMP = "Camp";

    // Valid CCA Names
    public static final String VALID_CCA_NAME_BASKETBALL = "Basketball";
    public static final String VALID_CCA_NAME_BADMINTON = "BADMINTON";
    public static final String VALID_CCA_NAME_TRACK = "track";
    public static final String VALID_CCA_NAME_FLOORBALL = "FLOORBALL M";
    public static final String VALID_CCA_NAME_HOCKEY = "hockey f";

    // Valid Fields for Basketball
    public static final String VALID_BUDGET_BASKETBALL = "700";
    public static final String VALID_SPENT_BASKETBALL = "200";
    public static final String VALID_OUTSTANDING_BASKETBALL = "500";
    public static final String VALID_TRANSACTION_NUM_BASKETBALL = "1";
    public static final Entry VALID_TRANSACTION_BASKETBALL = getEntryCompetition1();
    public static final String VALID_DATE_BASKETBALL = "05.08.2018";
    public static final String VALID_AMOUNT_BASKETBALL = "-400";
    public static final String VALID_REMARKS_BASKETBALL = "Overseas Trip";

    // Valid Fields for Track
    public static final String VALID_BUDGET_TRACK = "300";
    public static final String VALID_SPENT_TRACK = "100";
    public static final String VALID_OUTSTANDING_TRACK = "200";
    public static final Entry VALID_TRANSACTION_TRACK = getEntryEquipment1();
    public static final String VALID_TRANSACTION_NUM_TRACK = "2";
    public static final String VALID_DATE_TRACK = "05.08.2014";
    public static final String VALID_AMOUNT_TRACK = "100";
    public static final String VALID_REMARKS_TRACK = "Prize Money";

    // Valid Fields for Badminton
    public static final String VALID_BUDGET_BADMINTON = "200";
    public static final String VALID_SPENT_BADMINTON = "0";
    public static final String VALID_OUTSTANDING_BADMINTON = "200";
    public static final Entry VALID_TRANSACTION_BADMINTON = getEntryEquipment1();
    public static final String VALID_TRANSACTION_NUM_BADMINTON = "2";
    public static final String VALID_DATE_BADMINTON = "06.04.2015";
    public static final String VALID_AMOUNT_BADMINTON = "400";
    public static final String VALID_REMARKS_BADMINTON = "Sponsorship";

    // Valid Default Fields
    public static final String VALID_BUDGET_DEFAULT = "900";
    public static final String VALID_SPENT_DEFAULT = "700";
    public static final String VALID_OUTSTANDING_DEFAULT = "200";
    public static final Entry VALID_TRANSACTION_DEFAULT = getEntryEquipment1();
    public static final String VALID_ENTRY_NUM = "1";
    public static final String VALID_DATE = "12.12.2018";
    public static final String VALID_AMOUNT = "-100";
    public static final String VALID_REMARKS = "Purchase of Equipment";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ROOM_DESC_AMY = " " + PREFIX_ROOM + VALID_ROOM_AMY;
    public static final String ROOM_DESC_BOB = " " + PREFIX_ROOM + VALID_ROOM_BOB;
    public static final String SCHOOL_DESC_AMY = " " + PREFIX_SCHOOL + VALID_SCHOOL_AMY;
    public static final String SCHOOL_DESC_BOB = " " + PREFIX_SCHOOL + VALID_SCHOOL_BOB;
    public static final String TAG_DESC_AMY = " " + PREFIX_TAG + VALID_TAG_AMY;
    public static final String TAG_DESC_BOB = " " + PREFIX_TAG + VALID_TAG_BOB;

    // Valid Desc for Basketball
    public static final String NAME_DESC_BASKETBALL = " " + PREFIX_NAME + VALID_CCA_NAME_BASKETBALL;
    public static final String HEAD_DESC_BASKETBALL = " " + PREFIX_HEAD + VALID_NAME_BOB;
    public static final String VICE_HEAD_DESC_BASKETBALL = " " + PREFIX_VICE_HEAD + VALID_NAME_CARL;
    public static final String BUDGET_DESC_BASKETBALL = " " + PREFIX_BUDGET + VALID_BUDGET_BASKETBALL;
    public static final String TRANSACTION_NUM_DESC_BASKETBALL =
        " " + PREFIX_TRANSACTION + VALID_TRANSACTION_NUM_BASKETBALL;
    public static final String DATE_DESC_BASKETBALL = " " + PREFIX_DATE + VALID_DATE_BASKETBALL;
    public static final String AMOUNT_DESC_BASKETBALL = " " + PREFIX_AMOUNT + VALID_AMOUNT_BASKETBALL;
    public static final String REMARKS_DESC_BASKETBALL = " " + PREFIX_REMARKS + VALID_REMARKS_BASKETBALL;

    // Valid Desc for Badminton
    public static final String NAME_DESC_BADMINTON = " " + PREFIX_NAME + VALID_CCA_NAME_BADMINTON;
    public static final String HEAD_DESC_BADMINTON = " " + PREFIX_HEAD + VALID_NAME_AMY;
    public static final String VICE_HEAD_DESC_BADMINTON = " " + PREFIX_VICE_HEAD + VALID_NAME_DAVID;
    public static final String BUDGET_DESC_BADMINTON = " " + PREFIX_BUDGET + VALID_BUDGET_BADMINTON;
    public static final String TRANSACTION_NUM_DESC_BADMINTON =
        " " + PREFIX_TRANSACTION + VALID_TRANSACTION_NUM_BADMINTON;
    public static final String DATE_DESC_BADMINTON = " " + PREFIX_DATE + VALID_DATE_BADMINTON;
    public static final String AMOUNT_DESC_BADMINTON = " " + PREFIX_AMOUNT + VALID_AMOUNT_BADMINTON;
    public static final String REMARKS_DESC_BADMINTON = " " + PREFIX_REMARKS + VALID_REMARKS_BADMINTON;


    public static final String FROM_DESC_EXCURSION = " " + PREFIX_FROM + VALID_EMAIL_EXCURSION;
    public static final String FROM_DESC_CAMP = " " + PREFIX_FROM + VALID_EMAIL_CAMP;
    public static final String CONTENT_DESC_EXCURSION = " " + PREFIX_CONTENT + VALID_CONTENT_EXCURSION;
    public static final String CONTENT_DESC_CAMP = " " + PREFIX_CONTENT + VALID_CONTENT_CAMP;
    public static final String SUBJECT_DESC_EXCURSION = " " + PREFIX_SUBJECT + VALID_SUBJECT_EXCURSION;
    public static final String SUBJECT_DESC_CAMP = " " + PREFIX_SUBJECT + VALID_SUBJECT_CAMP;

    // Invalid Desc for CCA
    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ROOM_DESC = " " + PREFIX_ROOM; // empty string not allowed for room
    public static final String INVALID_SCHOOL_DESC = " " + PREFIX_SCHOOL
        + "School of Computing"; // spacing no allowed for school
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String INVALID_FROM_DESC = " " + PREFIX_FROM + "excursion!yahoo"; // missing '@' symbol
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT; // empty string not allowed for subjects
    public static final String INVALID_CONTENT_DESC = " " + PREFIX_CONTENT; // empty string not allowed for contents

    public static final String INVALID_CCA_NAME_BASKETBALL_DESC =
        " " + PREFIX_TAG + "Basketball - M"; // '-' not allowed in cca names
    public static final String INVALID_NAME_BASKETBALL_DESC =
        " " + PREFIX_NAME + "Basketball - M"; // '-' not allowed in cca names
    public static final String INVALID_HEAD_DESC = " " + PREFIX_HEAD + "$ally"; // '$' not allowed in name
    public static final String INVALID_VICE_HEAD_DESC = " " + PREFIX_VICE_HEAD + "ke//y"; // '/' not allowed in name
    public static final String INVALID_BUDGET_DESC = " " + PREFIX_BUDGET + "$500"; // '$' not allowed in budget
    public static final String INVALID_TRANS_DESC =
        " " + PREFIX_TRANSACTION + "0"; // '0' not allowed in transaction number
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "02/04/2018"; // '/' not allowed in date
    public static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "$200"; // '$' not allowed in amount

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final UpdateCommand.EditCcaDescriptor DESC_BASKETBALL;
    public static final UpdateCommand.EditCcaDescriptor DESC_TRACK;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withRoom(VALID_ROOM_AMY)
            .withSchool(VALID_SCHOOL_AMY).withTags(VALID_TAG_AMY).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withRoom(VALID_ROOM_BOB)
            .withSchool(VALID_SCHOOL_BOB).withTags(VALID_TAG_BOB).build();
    }

    static {
        DESC_BASKETBALL = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_BASKETBALL)
            .withHead(VALID_NAME_BOB)
            .withViceHead(VALID_NAME_AMY)
            .withBudget(VALID_BUDGET_BASKETBALL)
            .withSpent(VALID_SPENT_BASKETBALL)
            .withOustanding(VALID_OUTSTANDING_BASKETBALL)
            .withEntries(VALID_TRANSACTION_BASKETBALL).build();
        DESC_TRACK = new EditCcaDescriptorBuilder()
            .withCcaName(VALID_CCA_NAME_TRACK)
            .withHead(VALID_NAME_AMY)
            .withViceHead(VALID_NAME_BOB)
            .withBudget(VALID_BUDGET_TRACK)
            .withSpent(VALID_SPENT_TRACK)
            .withOustanding(VALID_OUTSTANDING_TRACK)
            .withEntries(VALID_TRANSACTION_TRACK).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        BudgetBook expectedBudgetBook = new BudgetBook(actualModel.getBudgetBook());
        List<Cca> ccaList = new ArrayList<>(actualModel.getFilteredCcaList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedBudgetBook, actualModel.getBudgetBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(ccaList, actualModel.getFilteredCcaList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
