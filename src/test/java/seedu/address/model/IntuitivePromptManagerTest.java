package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.argumentmanagers.AddArgumentManager;
import seedu.address.model.argumentmanagers.EditArgumentManager;
import seedu.address.model.person.Role;

public class IntuitivePromptManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private IntuitivePromptManager intuitivePromptManager = new IntuitivePromptManager();

    @Test
    public void isIntuitiveMode_onCreation_returnsFalse() {
        assertFalse(intuitivePromptManager.isIntuitiveMode());
    }

    @Test
    public void addArgument_addCommandWord_addSuccessful() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        assertTrue(intuitivePromptManager.isIntuitiveMode());
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void getInstruction_getNextRequiredAddInstruction_getCorrectInstruction() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        assertEquals(AddArgumentManager.ROLE_INSTRUCTION, intuitivePromptManager.getInstruction());
    }

    @Test
    public void addArgument_addFirstArgument_addSuccessful() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(AddArgumentManager.PATIENT_ARG_IDENTIFIER);
        assertTrue(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void addArgument_invalidArgument_throwsCommandException() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);

        thrown.expect(CommandException.class);
        thrown.expectMessage(Role.MESSAGE_ROLE_CONSTRAINTS
                + "\n" + AddArgumentManager.ROLE_INSTRUCTION);
        intuitivePromptManager.addArgument("!@#$%");
    }

    /*
    Arguments Retrieval Tests
     */

    @Test
    public void retrieveArguments_addPatientWithoutTags_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(AddArgumentManager.PATIENT_ARG_IDENTIFIER);
        intuitivePromptManager.addArgument("John Doe");
        intuitivePromptManager.addArgument("95592345");
        intuitivePromptManager.addArgument("doe@gmail.com");
        intuitivePromptManager.addArgument("Blk 123 Smith Street");
        intuitivePromptManager.addArgument("//");
        intuitivePromptManager.addArgument("S0798129E");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, AddCommand.COMMAND_WORD + " "
                + PREFIX_ROLE + "patient "
                + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "95592345 "
                + PREFIX_EMAIL + "doe@gmail.com "
                + PREFIX_ADDRESS + "Blk 123 Smith Street "
                + PREFIX_NRIC + "S0798129E");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void retrieveArguments_addPatientWithTags_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(AddArgumentManager.PATIENT_ARG_IDENTIFIER);
        intuitivePromptManager.addArgument("John Doe");
        intuitivePromptManager.addArgument("95592345");
        intuitivePromptManager.addArgument("doe@gmail.com");
        intuitivePromptManager.addArgument("Blk 123 Smith Street");
        intuitivePromptManager.addArgument("vegetarian,prefersTablets");
        intuitivePromptManager.addArgument("S0305372E");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, AddCommand.COMMAND_WORD + " "
                + PREFIX_ROLE + "patient "
                + PREFIX_NAME + "John Doe "
                + PREFIX_PHONE + "95592345 "
                + PREFIX_EMAIL + "doe@gmail.com "
                + PREFIX_ADDRESS + "Blk 123 Smith Street "
                + PREFIX_TAG + "vegetarian "
                + PREFIX_TAG + "prefersTablets "
                + PREFIX_NRIC + "S0305372E");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void retrieveArguments_editPerson_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(EditCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("2");
        intuitivePromptManager.addArgument("1 3"); //edit name and email
        intuitivePromptManager.addArgument("Jane Watson");
        intuitivePromptManager.addArgument("watson@gmail.com");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, "edit 2 n/Jane Watson e/watson@gmail.com");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void retrieveArguments_editClearPersonTags_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(EditCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("2");
        intuitivePromptManager.addArgument("5"); //edit tags
        intuitivePromptManager.addArgument(EditArgumentManager.EDIT_CLEAR_TAGS_COMMAND);

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, "edit 2 t/");
        assertFalse(intuitivePromptManager.areArgsAvailable());

    }

    @Test
    public void retrieveArguments_deletePerson_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(DeleteCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("3");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, "delete 3");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }

    @Test
    public void retrieveArguments_scheduleAppointment_successfulRetrieval() throws Exception {
        intuitivePromptManager.addArgument(ScheduleCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("1");
        intuitivePromptManager.addArgument("12.12.2018");
        intuitivePromptManager.addArgument("1500");
        intuitivePromptManager.addArgument("1600");
        //doctor details
        intuitivePromptManager.addArgument("Jane Smith");
        intuitivePromptManager.addArgument("S6219609B");
        //patient details
        intuitivePromptManager.addArgument("Bob Carpenter");
        intuitivePromptManager.addArgument("S5665160H");

        assertFalse(intuitivePromptManager.isIntuitiveMode());
        String retrievedArguments = intuitivePromptManager.retrieveArguments();
        assertEquals(retrievedArguments, "schedule 1 d/12.12.2018 st/1500 et/1600 dn/Jane Smith di/S6219609B "
                + "pn/Bob Carpenter pi/S5665160H");
        assertFalse(intuitivePromptManager.areArgsAvailable());
    }


    /*
    Instruction Retrieval Tests
     */

    @Test
    public void getInstruction_goBackForAddCommand_correctInstruction() throws Exception {
        intuitivePromptManager.addArgument(AddCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument(AddArgumentManager.DOCTOR_ARG_IDENTIFIER);
        assertEquals(intuitivePromptManager.getInstruction(), AddArgumentManager.NAME_INSTRUCTION);

        intuitivePromptManager.removeArgument();
        assertEquals(intuitivePromptManager.getInstruction(), AddArgumentManager.ROLE_INSTRUCTION);
    }

    @Test
    public void getInstruction_goBackForEditCommand_correctInstruction() throws Exception {
        intuitivePromptManager.addArgument(EditCommand.COMMAND_WORD);
        intuitivePromptManager.addArgument("2");
        intuitivePromptManager.addArgument("1 3"); //edit name and email
        intuitivePromptManager.addArgument("Jane Watson");
        assertEquals(intuitivePromptManager.getInstruction(), EditArgumentManager.EDIT_EMAIL_INSTRUCTION);

        intuitivePromptManager.removeArgument();
        assertEquals(intuitivePromptManager.getInstruction(), EditArgumentManager.EDIT_FIELDS_INSTRUCTION);

        intuitivePromptManager.removeArgument();
        assertEquals(intuitivePromptManager.getInstruction(), EditArgumentManager.EDIT_TARGET_INSTRUCTION);
    }


}
