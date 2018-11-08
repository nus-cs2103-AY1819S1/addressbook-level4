package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASSIGNMENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LEAVE_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PROJECT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;
import seedu.address.model.leaveapplication.LeaveDescriptionContainsKeywordsPredicate;
import seedu.address.model.leaveapplication.LeaveEmployeeEqualsPredicate;
import seedu.address.model.leaveapplication.StatusEnum;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.project.Assignment;
import seedu.address.model.project.AssignmentContainsKeywordsPredicate;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_PROJECT_OASIS = "OASIS";
    public static final String VALID_PROJECT_FALCON = "FALCON";
    public static final String VALID_SALARY_AMY = "10000";
    public static final String VALID_SALARY_BOB = "12000";
    public static final String VALID_DESCRIPTION_OASIS = "Project management system for all.";
    public static final String VALID_DESCRIPTION_FALCON = "Home security camera.";
    public static final String VALID_USERNAME_AMY = "Amy Bee";
    public static final String VALID_USERNAME_BOB = "Bob Choo";
    public static final String VALID_PASSWORD_AMY = "Pa55w0rd";
    public static final String VALID_PASSWORD_BOB = "Pa55w0rd";

    public static final String VALID_DESCRIPTION_BOB_LEAVE = "Bob's wedding anniversary";
    public static final StatusEnum.Status VALID_LEAVESTATUS_BOB_LEAVE = StatusEnum.Status.REJECTED;
    public static final String VALID_LEAVEDATE_STRING_BOB_LEAVE = "2018-10-30";
    public static final LocalDate VALID_LEAVEDATE_BOB_LEAVE = LocalDate.of(2018, 10, 30);

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + " " + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + " " + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + " " + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + " " + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + " " + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + " " + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + " " + VALID_ADDRESS_BOB;
    public static final String PROJECT_DESC_OASIS = " " + PREFIX_PROJECT + " " + VALID_PROJECT_OASIS;
    public static final String PROJECT_DESC_FALCON = " " + PREFIX_PROJECT + " " + VALID_PROJECT_FALCON;
    public static final String SALARY_DESC_AMY = " " + PREFIX_SALARY + " " + VALID_SALARY_AMY;
    public static final String SALARY_DESC_BOB = " " + PREFIX_SALARY + " " + VALID_SALARY_BOB;
    public static final String USERNAME_DESC_AMY = " " + PREFIX_USERNAME + " " + VALID_USERNAME_AMY;
    public static final String USERNAME_DESC_BOB = " " + PREFIX_USERNAME + " " + VALID_USERNAME_BOB;
    public static final String NAME_ASSIGNMENT_DESC_OASIS = " " + PREFIX_ASSIGNMENT_NAME + " " + VALID_PROJECT_OASIS;
    public static final String AUTHOR_ASSIGNMENT_DESC_OASIS = " " + PREFIX_AUTHOR + " " + VALID_NAME_AMY;
    public static final String ASSIGNMENT_DESC_OASIS =
            " " + PREFIX_ASSIGNMENT_DESCRIPTION + " " + VALID_DESCRIPTION_OASIS;
    public static final String NAME_ASSIGNMENT_DESC_FALCON = " " + PREFIX_ASSIGNMENT_NAME + " " + VALID_PROJECT_FALCON;
    public static final String AUTHOR_ASSIGNMENT_DESC_FALCON = " " + PREFIX_AUTHOR + " " + VALID_NAME_BOB;
    public static final String ASSIGNMENT_DESC_FALCON =
            " " + PREFIX_ASSIGNMENT_DESCRIPTION + " " + VALID_DESCRIPTION_FALCON;

    public static final String LEAVEDESCIPTION_DESC_ALICE_LEAVE = " " + PREFIX_LEAVE_DESCRIPTION
            + " " + "Alice family holiday";
    public static final String LEAVEDESCIPTION_DESC_BENSON_LEAVE = " " + PREFIX_LEAVE_DESCRIPTION
            + " " + "Benson's brother's wedding";
    public static final String LEAVEDESCIPTION_DESC_BOB_LEAVE = " " + PREFIX_LEAVE_DESCRIPTION
            + " " + VALID_DESCRIPTION_BOB_LEAVE;
    public static final String LEAVEDATES_DESC_ALICE_LEAVE = " "
            + PREFIX_LEAVE_DATE + " " + "2018-10-23" + " "
            + PREFIX_LEAVE_DATE + " " + "2018-10-24";
    public static final String LEAVEDATES_DESC_BENSON_LEAVE = " "
            + PREFIX_LEAVE_DATE + " " + "2018-10-25" + " "
            + PREFIX_LEAVE_DATE + " " + "2018-10-26";
    public static final String LEAVEDATES_DESC_BOB_LEAVE = " "
            + PREFIX_LEAVE_DATE + " " + "2018-10-30";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " " + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + " " + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + " " + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_PROJECT_DESC = " " + PREFIX_PROJECT + " " + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_SALARY_DESC = " " + PREFIX_SALARY; // empty string not allowed for salary
    public static final String INVALID_ASSIGNMENT_NAME_DESC =
            " " + PREFIX_ASSIGNMENT_NAME + " " + "Alibabaa&"; // '&' not allowed in names
    public static final String INVALID_ASSIGNMENT_AUTHOR_DESC =
            " " + PREFIX_AUTHOR + " " + "911a&"; // '&' not allowed in author

    public static final String INVALID_LEAVEDESCIPTION_DESC = " " + PREFIX_LEAVE_DESCRIPTION + " "
            + " "; // blank description not allowed
    public static final String INVALID_LEAVEDATES_DESC = " " + PREFIX_LEAVE_DATE + " "
            + "2018-10-123"; // wrong date format

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    public static final EditAssignmentCommand.EditAssignmentDescriptor DESC_OASIS;
    public static final EditAssignmentCommand.EditAssignmentDescriptor DESC_FALCON;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withSalary(VALID_SALARY_AMY).withProjects(VALID_PROJECT_OASIS)
                .build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withSalary(VALID_SALARY_BOB).withProjects(VALID_PROJECT_OASIS)
                .build();
        DESC_OASIS = new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_PROJECT_OASIS)
                .withAuthor(VALID_NAME_AMY).withDescription(VALID_DESCRIPTION_OASIS).build();
        DESC_FALCON = new EditAssignmentDescriptorBuilder().withAssignmentName(VALID_PROJECT_FALCON)
                .withAuthor(VALID_NAME_BOB).withDescription(VALID_DESCRIPTION_FALCON).build();
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
            assertEquals(expectedMessage, result.getFeedbackToUser());
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

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
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
     * Updates {@code model}'s filtered list to show only the assignment at the given {@code targetIndex} in the
     * {@code model}'s assignment list.
     */
    public static void showAssignmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAssignmentList().size());

        Assignment assignment = model.getFilteredAssignmentList().get(targetIndex.getZeroBased());
        final String[] splitName = assignment.getProjectName().fullProjectName.split("\\s+");
        model.updateFilteredAssignmentList(new AssignmentContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredAssignmentList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Updates {@code model}'s filtered leave list to show only the leave application at the given {@code targetIndex}
     * in the {@code model}'s address book.
     */
    public static void showLeaveApplicationAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredLeaveApplicationList().size());

        LeaveApplicationWithEmployee leaveApplication = model.getFilteredLeaveApplicationList()
                .get(targetIndex.getZeroBased());
        final String[] splitDesc = leaveApplication.getDescription().value.split("\\s+");
        model.updateFilteredLeaveApplicationList(new LeaveDescriptionContainsKeywordsPredicate(
                Arrays.asList(splitDesc[0])));

        assertEquals(1, model.getFilteredLeaveApplicationList().size());
    }

    /**
     * Updates {@code model}'s filtered leave list to show only the leave application for a given {@code Person}
     * in the {@code model}'s address book.
     */
    public static void showLeaveApplicationForPerson(Model model, Person person) {
        assertTrue(model.hasPerson(person));

        model.updateFilteredLeaveApplicationList(new LeaveEmployeeEqualsPredicate(person));
    }
}
