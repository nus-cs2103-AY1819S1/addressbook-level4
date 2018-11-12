package tutorhelper.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_DAY_AND_TIME;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_EMAIL;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_NAME;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_PHONE;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_SYLLABUS;
import static tutorhelper.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorhelper.commons.core.index.Index;
import tutorhelper.logic.CommandHistory;
import tutorhelper.logic.commands.exceptions.CommandException;
import tutorhelper.model.Model;
import tutorhelper.model.TutorHelper;
import tutorhelper.model.student.NameContainsKeywordsPredicate;
import tutorhelper.model.student.Student;
import tutorhelper.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NAME_CATHY = "Cathy Ho";
    public static final String VALID_NAME_ALCYONE = "Alcyone Star";
    public static final String VALID_NAME_BILLY = "Billy Goat";
    public static final String VALID_NAME_CABBAGE = "Cabbage Green";
    public static final String VALID_NAME_DAISY = "Daisy Lane";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_PHONE_CATHY = "33333333";
    public static final String VALID_PHONE_ALCYONE = "11112222";
    public static final String VALID_PHONE_BILLY = "33334444";
    public static final String VALID_PHONE_CABBAGE = "44445555";
    public static final String VALID_PHONE_DAISY = "44444444";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_EMAIL_CATHY = "cathy@example.com";
    public static final String VALID_EMAIL_ALCYONE = "alcyone@example.com";
    public static final String VALID_EMAIL_BILLY = "billy@example.com";
    public static final String VALID_EMAIL_CABBAGE = "cabbage@example.com";
    public static final String VALID_EMAIL_DAISY = "daisy@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_ADDRESS_CATHY = "Block 213, Cathy Street 2";
    public static final String VALID_ADDRESS_ALCYONE = "Block 111, Alcyone Constellation 3";
    public static final String VALID_ADDRESS_BILLY = "Block 210, Billy Mountain 88";
    public static final String VALID_ADDRESS_CABBAGE = "Block 274, Cabbage Patch 95";
    public static final String VALID_ADDRESS_DAISY = "Block 232, Daisy Lane 55";
    public static final String VALID_SUBJECT_AMY = "Mathematics";
    public static final String VALID_SUBJECT_BOB = "Economics";
    public static final String VALID_SUBJECT_CATHY = "Physics";
    public static final String VALID_SUBJECT_ALCYONE = "Economics";
    public static final String VALID_SUBJECT_BILLY = "Physics";
    public static final String VALID_SUBJECT_CABBAGE = "Chemistry";
    public static final String VALID_SUBJECT_DAISY = "Chemistry";
    public static final String VALID_TUITION_TIMING_AMY = "Monday 4:00pm";
    public static final String VALID_TUITION_TIMING_BOB = "Tuesday 8:00pm";
    public static final String VALID_TUITION_TIMING_CATHY = "Wednesday 7:30pm";
    public static final String VALID_TUITION_TIMING_ALCYONE = "Friday 6:30pm";
    public static final String VALID_TUITION_TIMING_BILLY = "Saturday 7:30pm";
    public static final String VALID_TUITION_TIMING_CABBAGE = "Sunday 7:30pm";
    public static final String VALID_TUITION_TIMING_DAISY = "Thursday 7:30pm";
    public static final String VALID_TUITION_TIMING_MEIER = "Monday 5:00pm";

    public static final String VALID_TAG_WEAK = "weak";
    public static final String VALID_TAG_EXAM = "exam";
    public static final String DUPLICATE_SYLLABUS = "Integration";
    public static final String VALID_SYLLABUS_DIFFERENTIATION = "Differentiation";
    public static final String VALID_SYLLABUS_KINETICS = "Kinetics";
    public static final String VALID_SYLLABUS_ORGANIC = "Organic Chemistry";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String SUBJECT_DESC_AMY = " " + PREFIX_SUBJECT + VALID_SUBJECT_AMY;
    public static final String SUBJECT_DESC_BOB = " " + PREFIX_SUBJECT + VALID_SUBJECT_BOB;
    public static final String TUITION_TIMING_DESC_AMY = " " + PREFIX_DAY_AND_TIME + VALID_TUITION_TIMING_AMY;
    public static final String TUITION_TIMING_DESC_BOB = " " + PREFIX_DAY_AND_TIME + VALID_TUITION_TIMING_BOB;
    public static final String TAG_DESC_EXAM = " " + PREFIX_TAG + VALID_TAG_EXAM;
    public static final String TAG_DESC_WEAK = " " + PREFIX_TAG + VALID_TAG_WEAK;
    public static final String TAG_DESC_WEAK_EXAM = " " + PREFIX_TAG + VALID_TAG_WEAK + ", " + VALID_TAG_EXAM;
    public static final String SYLLABUS_DESC_DIFFERENTIATION = PREFIX_SYLLABUS + VALID_SYLLABUS_DIFFERENTIATION;
    public static final String SYLLABUS_DESC_INTEGRATION = PREFIX_SYLLABUS + DUPLICATE_SYLLABUS;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_SUBJECT_DESC = " " + PREFIX_SUBJECT; //empty string not allowed for subjects
    public static final String INVALID_TUITION_TIMING_DESC = " "
            + PREFIX_DAY_AND_TIME + "Monday 12pm"; // time needs to be in standard 12hr timing
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditStudentDescriptor DESC_AMY;
    public static final EditCommand.EditStudentDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditStudentDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withEmptyPayments().withTags(VALID_TAG_EXAM).build();
        DESC_BOB = new EditStudentDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_WEAK, VALID_TAG_EXAM).build();
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
     * - the TutorHelper and the filtered student list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        TutorHelper expectedTutorHelper = new TutorHelper(actualModel.getTutorHelper());
        List<Student> expectedFilteredList = new ArrayList<>(actualModel.getFilteredStudentList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedTutorHelper, actualModel.getTutorHelper());
            assertEquals(expectedFilteredList, actualModel.getFilteredStudentList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the student at the given {@code targetIndex} in the
     * {@code model}'s TutorHelper.
     */
    public static void showStudentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredStudentList().size());

        Student student = (Student) model.getFilteredStudentList().get(targetIndex.getZeroBased());
        final String[] splitName = student.getName().fullName.split("\\s+");
        model.updateFilteredStudentList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredStudentList().size());
    }

    /**
     * Deletes the first student in {@code model}'s filtered list from {@code model}'s TutorHelper.
     */
    public static void deleteFirstStudent(Model model) {
        Student firstStudent = model.getFilteredStudentList().get(0);
        model.deleteStudent(firstStudent);
        model.commitTutorHelper();
    }

}
