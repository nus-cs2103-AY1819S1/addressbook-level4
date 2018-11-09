package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class PromoteIndividualCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void executePromoteOutOfBoundIndexFailure() throws CommandException {
        thrown.expect(CommandException.class);
        new PromoteIndividualCommand("1 2 8").execute(model, commandHistory);
    }

    @Test
    public void executePromoteNegativeIndexFailure() throws IndexOutOfBoundsException {
        thrown.expect(IndexOutOfBoundsException.class);
        new PromoteIndividualCommand("-1 2 8");
    }

    // Promote a final year student into a graduated student.
    @Test
    public void executePromoteGraduatedIndexSuccess() {
        Person graduatedStudent = new PersonBuilder().build();
        graduatedStudent.addTag(new Tag("Graduated"));
        PromoteIndividualCommand promoteIndividualCommand = new PromoteIndividualCommand("3");

        String expectedMessage = String.format(PromoteIndividualCommand.MESSAGE_SUCCESS
                + PromoteIndividualCommand.MESSAGE_GRADUATED_STUDENTS, 0, "");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(2), graduatedStudent);
        expectedModel.commitAddressBook();

        assertCommandSuccess(promoteIndividualCommand, expectedModel, commandHistory, expectedMessage, expectedModel);
    }

    // Promote a student who has already graduated
    @Test
    public void executePromoteFirstIndexSuccess() {
        Person promotedStudent = new PersonBuilder(model.getFilteredPersonList().get(0)).build();
        promotedStudent.addTag(new Tag("Graduated"));
        PromoteIndividualCommand promoteIndividualCommand = new PromoteIndividualCommand("1");

        String expectedMessage = String.format(PromoteIndividualCommand.MESSAGE_SUCCESS
                + PromoteIndividualCommand.MESSAGE_GRADUATED_STUDENTS, 1, promotedStudent.getName().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), promotedStudent);
        expectedModel.commitAddressBook();


        assertCommandSuccess(promoteIndividualCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    // Promote a student who has not graduated and not in his/her final year
    @Test
    public void executePromoteOneStudentSuccess() {
        Person promotedStudent = new PersonBuilder(model.getFilteredPersonList().get(5))
                .withEducation("Primary 4").build();

        PromoteIndividualCommand promoteIndividualCommand = new PromoteIndividualCommand("6");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(5), promotedStudent);
        expectedModel.commitAddressBook();

        String expectedMessage = String.format(PromoteIndividualCommand.MESSAGE_SUCCESS
                + PromoteIndividualCommand.MESSAGE_GRADUATED_STUDENTS, 1, "");

        assertCommandSuccess(promoteIndividualCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    // Promote a student who has not graduated and not in his/her final year
    @Test
    public void executePromoteMultipleStudentSuccess() {
        Person alice = new PersonBuilder(model.getFilteredPersonList().get(0))
                .withEducation("Secondary 4").build();
        alice.addTag(new Tag("Graduated"));

        Person fiona = new PersonBuilder(model.getFilteredPersonList().get(5))
                .withEducation("Primary 4").build();

        Person george = new PersonBuilder(model.getFilteredPersonList().get(6))
                .withEducation("Primary 2").build();

        PromoteIndividualCommand promoteIndividualCommand = new PromoteIndividualCommand("1 6 7");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), alice);
        expectedModel.updatePerson(model.getFilteredPersonList().get(5), fiona);
        expectedModel.updatePerson(model.getFilteredPersonList().get(6), george);
        expectedModel.commitAddressBook();

        String expectedMessage = String.format(PromoteIndividualCommand.MESSAGE_SUCCESS
                + PromoteIndividualCommand.MESSAGE_GRADUATED_STUDENTS, 3, alice.getName().toString());

        assertCommandSuccess(promoteIndividualCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
