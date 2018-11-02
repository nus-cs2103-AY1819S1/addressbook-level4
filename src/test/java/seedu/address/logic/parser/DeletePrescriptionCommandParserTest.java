package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_ID_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_ID_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDICINE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_NAME_DESC_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_NAME_DESC_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PARACETAMOL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.DeletePrescriptionCommand;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;
import seedu.address.testutil.PrescriptionBuilder;

public class DeletePrescriptionCommandParserTest {
    private DeletePrescriptionCommandParser parser = new DeletePrescriptionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Prescription expectedPrescription = new PrescriptionBuilder().build();
        int expectedId = expectedPrescription.getId();
        MedicineName expectedMedicineName = expectedPrescription.getMedicineName();

        // multiple appointment id - last id accepted
        assertParseSuccess(parser, APPOINTMENT_ID_DESC_SECOND + APPOINTMENT_ID_DESC_FIRST
                + MEDICINE_NAME_DESC_PARACETAMOL, new DeletePrescriptionCommand(expectedId, expectedMedicineName));

        // multiple medicine names - last medicine name accepted
        assertParseSuccess(parser, APPOINTMENT_ID_DESC_FIRST + MEDICINE_NAME_DESC_VICODIN
                + MEDICINE_NAME_DESC_PARACETAMOL, new DeletePrescriptionCommand(expectedId, expectedMedicineName));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePrescriptionCommand.MESSAGE_USAGE);

        // missing appointment id prefix
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL,
                expectedMessage);

        // missing medicineName prefix
        assertParseFailure(parser, APPOINTMENT_ID_DESC_FIRST + VALID_MEDICINE_NAME_PARACETAMOL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + VALID_MEDICINE_NAME_PARACETAMOL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid medicine name
        assertParseFailure(parser, APPOINTMENT_ID_DESC_FIRST + INVALID_MEDICINE_NAME_DESC,
                MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + APPOINTMENT_ID_DESC_FIRST
                + MEDICINE_NAME_DESC_PARACETAMOL, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeletePrescriptionCommand.MESSAGE_USAGE));
    }
}
