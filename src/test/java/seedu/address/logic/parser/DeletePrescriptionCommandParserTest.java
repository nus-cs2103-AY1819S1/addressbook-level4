package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDICINE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_NAME_DESC_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_NAME_DESC_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PARACETAMOL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeletePrescriptionCommand;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;
import seedu.address.testutil.PrescriptionBuilder;

public class DeletePrescriptionCommandParserTest {

    public static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeletePrescriptionCommand.MESSAGE_USAGE);

    private DeletePrescriptionCommandParser parser = new DeletePrescriptionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Prescription expectedPrescription = new PrescriptionBuilder().build();
        int expectedId = expectedPrescription.getId();
        MedicineName expectedMedicineName = expectedPrescription.getMedicineName();

        // multiple appointment id - last id accepted
        assertParseSuccess(parser, VALID_APPOINTMENT_ID_SECOND + VALID_APPOINTMENT_ID_FIRST
                + MEDICINE_NAME_DESC_PARACETAMOL, new DeletePrescriptionCommand(expectedId, expectedMedicineName));

        // multiple medicine names - last medicine name accepted
        assertParseSuccess(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_VICODIN
                + MEDICINE_NAME_DESC_PARACETAMOL, new DeletePrescriptionCommand(expectedId, expectedMedicineName));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePrescriptionCommand.MESSAGE_USAGE);

        // missing appointment id
        assertParseFailure(parser, MEDICINE_NAME_DESC_PARACETAMOL,
                expectedMessage);

        // missing medicineName prefix
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + VALID_MEDICINE_NAME_PARACETAMOL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + VALID_MEDICINE_NAME_PARACETAMOL,
                expectedMessage);
    }


    @Test
    public void parse_invalidValue_failure() {
        // invalid medicine name
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + INVALID_MEDICINE_NAME_DESC,
                MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);

    }


    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + MEDICINE_NAME_DESC_PARACETAMOL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + MEDICINE_NAME_DESC_PARACETAMOL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

}
