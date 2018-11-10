package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONDITION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONDITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEDICINE_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPatientsAndDoctors.ALICE_PATIENT;
import static seedu.address.testutil.TypicalPatientsAndDoctors.GEORGE_DOCTOR;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddMedicalHistoryCommand;
import seedu.address.logic.commands.AddPrescriptionCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CompleteAppointmentCommand;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteDoctorCommand;
import seedu.address.logic.commands.DeleteMedicalHistoryCommand;
import seedu.address.logic.commands.DeletePatientCommand;
import seedu.address.logic.commands.DeletePrescriptionCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterDoctorCommand;
import seedu.address.logic.commands.FilterPatientCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RegisterDoctorCommand;
import seedu.address.logic.commands.RegisterPatientCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewDoctorCommand;
import seedu.address.logic.commands.ViewPatientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.patient.Patient;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.AppointmentUtil;
import seedu.address.testutil.DoctorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;
import seedu.address.testutil.PrescriptionBuilder;

public class AddressBookParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().withRemark("").build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD + " 3") instanceof HistoryCommand);

        try {
            parser.parseCommand("histories");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, pe.getMessage());
        }
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_select() throws Exception {
        SelectCommand command = (SelectCommand) parser.parseCommand(
                SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new SelectCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_redoCommandWord_returnsRedoCommand() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand("redo 1") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_remarkCommandWord_returnsRemarkCommand() throws Exception {
        final Name name = new Name(VALID_NAME_AMY);
        final Phone phone = new Phone(VALID_PHONE_AMY);
        final Remark remark = new Remark(VALID_REMARK_AMY);
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD
                + " " + PREFIX_NAME + name.toString() + " " + PREFIX_PHONE + phone.toString() + " "
                + PREFIX_REMARK + remark.toString());
        assertEquals(new RemarkCommand(name, phone, remark), command);
    }

    @Test
    public void parseCommand_undoCommandWord_returnsUndoCommand() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand("undo 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_registerPatientCommandWord_returnsRegisterPatientCommand() throws Exception {
        final Patient patient = new PatientBuilder().build();
        RegisterPatientCommand command = (RegisterPatientCommand) parser.parseCommand(
                PersonUtil.getRegisterPatientCommand(patient));
        assertEquals(new RegisterPatientCommand(patient), command);
    }

    @Test
    public void parseCommand_registerDoctorCommandWord_returnsRegisterDoctorCommand() throws Exception {
        final Doctor doctor = new DoctorBuilder().build();
        RegisterDoctorCommand command = (RegisterDoctorCommand) parser.parseCommand(
                PersonUtil.getRegisterDoctorCommand(doctor));
        assertEquals(new RegisterDoctorCommand(doctor), command);
    }

    @Test
    public void parseCommand_deletePatientCommandWord_returnsDeletePatientCommand() throws Exception {
        DeletePatientCommand command = (DeletePatientCommand) parser.parseCommand(
                DeletePatientCommand.COMMAND_WORD + " " + PREFIX_NAME + ALICE_PATIENT.getName().fullName);
        assertEquals(new DeletePatientCommand(ALICE_PATIENT.getName(), null), command);
    }

    @Test
    public void parseCommand_deleteDoctorCommandWord_returnsDeleteDoctorCommand() throws Exception {
        DeleteDoctorCommand command = (DeleteDoctorCommand) parser.parseCommand(
                DeleteDoctorCommand.COMMAND_WORD + " " + PREFIX_NAME + GEORGE_DOCTOR.getName().fullName);
        assertEquals(new DeleteDoctorCommand(GEORGE_DOCTOR.getName(), null), command);
    }

    @Test
    public void parseCommand_viewPatientCommandWord_returnsViewPatientCommand() throws Exception {
        ViewPatientCommand command = (ViewPatientCommand) parser.parseCommand(
                ViewPatientCommand.COMMAND_WORD + " " + PREFIX_NAME + ALICE_PATIENT.getName().fullName);
        assertEquals(new ViewPatientCommand(ALICE_PATIENT.getName(), null), command);
    }

    @Test
    public void parseCommand_viewDoctorCommandWord_returnsViewDoctorCommand() throws Exception {
        ViewDoctorCommand command = (ViewDoctorCommand) parser.parseCommand(
                ViewDoctorCommand.COMMAND_WORD + " " + PREFIX_NAME + GEORGE_DOCTOR.getName().fullName);
        assertEquals(new ViewDoctorCommand(GEORGE_DOCTOR.getName(), null), command);
    }

    @Test
    public void parseCommand_filterDoctorCommandWord_returnsFilterDoctorCommand() throws Exception {
        assertTrue(parser.parseCommand(FilterDoctorCommand.COMMAND_WORD) instanceof FilterDoctorCommand);
        assertTrue(parser.parseCommand(FilterDoctorCommand.COMMAND_WORD + " 3") instanceof FilterDoctorCommand);
    }

    @Test
    public void parseCommand_filterPatientCommandWord_returnsFilterPatientCommand() throws Exception {
        assertTrue(parser.parseCommand(FilterPatientCommand.COMMAND_WORD) instanceof FilterPatientCommand);
        assertTrue(parser.parseCommand(FilterPatientCommand.COMMAND_WORD + " 3")
                instanceof FilterPatientCommand);
    }

    @Test
    public void parseCommand_addAppointmentCommandWord_returnsAddAppointmentCommand() throws Exception {
        final Appointment appointment = new AppointmentBuilder().build();
        AddAppointmentCommand command = (AddAppointmentCommand) parser.parseCommand(
                AppointmentUtil.getAddAppointmentCommand(appointment));
        assertEquals(new AddAppointmentCommand(appointment), command);
    }

    @Test
    public void parseCommand_deleteAppointmentCommandWord_returnsDeleteAppointmentCommand() throws Exception {
        DeleteAppointmentCommand command = (DeleteAppointmentCommand) parser.parseCommand(
                DeleteAppointmentCommand.COMMAND_WORD + " " + VALID_APPOINTMENT_ID_FIRST);
        assertEquals(new DeleteAppointmentCommand(VALID_APPOINTMENT_ID_FIRST), command);
    }

    @Test
    public void parseCommand_completeAppointmentCommandWord_returnsCompleteAppointmentCommand() throws Exception {
        CompleteAppointmentCommand command = (CompleteAppointmentCommand) parser.parseCommand(
                CompleteAppointmentCommand.COMMAND_WORD + " " + VALID_APPOINTMENT_ID_FIRST);
        CompleteAppointmentCommand newCommand = new CompleteAppointmentCommand(VALID_APPOINTMENT_ID_FIRST);
        assertEquals(newCommand, command);
    }

    @Test
    public void parseCommand_addPrescriptionCommandWord_returnsAddPrescriptionCommand() throws Exception {
        final Prescription prescription = new PrescriptionBuilder().build();
        AddPrescriptionCommand command = (AddPrescriptionCommand) parser.parseCommand(
                AppointmentUtil.getAddPrescriptionCommand(prescription));
        assertEquals(new AddPrescriptionCommand(VALID_APPOINTMENT_ID_FIRST, prescription), command);
    }

    @Test
    public void parseCommand_deletePrescriptionCommandWord_returnsDeletePrescriptionCommand() throws Exception {
        DeletePrescriptionCommand command = (DeletePrescriptionCommand) parser.parseCommand(
                DeletePrescriptionCommand.COMMAND_WORD + " " + VALID_APPOINTMENT_ID_FIRST + " "
                        + PREFIX_MEDICINE_NAME + VALID_MEDICINE_NAME_PARACETAMOL);
        assertEquals(new DeletePrescriptionCommand(VALID_APPOINTMENT_ID_FIRST,
                new MedicineName(VALID_MEDICINE_NAME_PARACETAMOL)), command);
    }

    @Test
    public void parseCommand_addMedicalHistoryCommandWord_returnsAddMedicalHistoryCommand() throws Exception {
        AddMedicalHistoryCommand command = (AddMedicalHistoryCommand) parser.parseCommand(
                AddMedicalHistoryCommand.COMMAND_WORD + " " + PREFIX_NAME + VALID_NAME_AMY + " "
                        + PREFIX_PHONE + VALID_PHONE_AMY + " " + PREFIX_ALLERGY + VALID_ALLERGY + " "
                        + PREFIX_CONDITION + VALID_CONDITION);
        assertEquals(new AddMedicalHistoryCommand(new Name(VALID_NAME_AMY), new Phone(VALID_PHONE_AMY),
                VALID_ALLERGY, VALID_CONDITION), command);
    }

    @Test
    public void parseCommand_deleteMedicalHistoryCommandWord_returnsDeleteMedicalHistoryCommand() throws Exception {
        DeleteMedicalHistoryCommand command = (DeleteMedicalHistoryCommand) parser.parseCommand(
                DeleteMedicalHistoryCommand.COMMAND_WORD + " " + PREFIX_NAME + VALID_NAME_AMY + " "
                        + PREFIX_PHONE + VALID_PHONE_AMY + " " + PREFIX_ALLERGY + VALID_ALLERGY + " "
                        + PREFIX_CONDITION + VALID_CONDITION);
        assertEquals(new DeleteMedicalHistoryCommand(new Name(VALID_NAME_AMY), new Phone(VALID_PHONE_AMY),
                VALID_ALLERGY, VALID_CONDITION), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        parser.parseCommand("");
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_UNKNOWN_COMMAND);
        parser.parseCommand("unknownCommand");
    }
}
