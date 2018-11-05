package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_ID_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.CONSUMPTION_PER_DAY_DESC_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.CONSUMPTION_PER_DAY_DESC_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.DOSAGE_DESC_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.DOSAGE_DESC_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CONSUMPTION_PER_DAY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOSAGE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_MEDICINE_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_NAME_DESC_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.MEDICINE_NAME_DESC_VICODIN;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_ID_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONSUMPTION_PER_DAY_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOSAGE_PARACETAMOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEDICINE_NAME_PARACETAMOL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddPrescriptionCommand;
import seedu.address.model.appointment.ConsumptionPerDay;
import seedu.address.model.appointment.Dosage;
import seedu.address.model.appointment.MedicineName;
import seedu.address.model.appointment.Prescription;
import seedu.address.testutil.PrescriptionBuilder;

public class AddPrescriptionCommandParserTest {

    public static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddPrescriptionCommand.MESSAGE_USAGE);

    private AddPrescriptionCommandParser parser = new AddPrescriptionCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Prescription expectedPrescription = new PrescriptionBuilder().build();

        // multiple medicine names - last medicine name accepted
        assertParseSuccess(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_VICODIN
                        + MEDICINE_NAME_DESC_PARACETAMOL + DOSAGE_DESC_PARACETAMOL
                        + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                new AddPrescriptionCommand(VALID_APPOINTMENT_ID_FIRST, expectedPrescription));

        // multiple dosages - last dosage accepted
        assertParseSuccess(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL
                        + DOSAGE_DESC_VICODIN + DOSAGE_DESC_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                new AddPrescriptionCommand(VALID_APPOINTMENT_ID_FIRST, expectedPrescription));

        // multiple consumption per day - last consumption per day accepted
        assertParseSuccess(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL
                        + DOSAGE_DESC_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_VICODIN
                        + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                new AddPrescriptionCommand(VALID_APPOINTMENT_ID_FIRST, expectedPrescription));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPrescriptionCommand.MESSAGE_USAGE);

        // missing appointment id
        assertParseFailure(parser, MEDICINE_NAME_DESC_PARACETAMOL
                        + DOSAGE_DESC_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                expectedMessage);

        // missing medicine name prefix
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + VALID_MEDICINE_NAME_PARACETAMOL
                        + DOSAGE_DESC_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                expectedMessage);

        // missing dosage prefix
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL
                        + VALID_DOSAGE_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                expectedMessage);

        // missing consumption per day prefix
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL
                        + DOSAGE_DESC_PARACETAMOL + VALID_CONSUMPTION_PER_DAY_PARACETAMOL,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + VALID_MEDICINE_NAME_PARACETAMOL
                        + VALID_DOSAGE_PARACETAMOL + VALID_CONSUMPTION_PER_DAY_PARACETAMOL,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid medicine name
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + INVALID_MEDICINE_NAME_DESC
                        + DOSAGE_DESC_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);

        // invalid dosage
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL
                + INVALID_DOSAGE_DESC + CONSUMPTION_PER_DAY_DESC_PARACETAMOL, Dosage.MESSAGE_CONSTRAINTS);

        // invalid consumption per day
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + MEDICINE_NAME_DESC_PARACETAMOL
                + DOSAGE_DESC_PARACETAMOL + INVALID_CONSUMPTION_PER_DAY_DESC,
                ConsumptionPerDay.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, VALID_APPOINTMENT_ID_FIRST + INVALID_MEDICINE_NAME_DESC
                + DOSAGE_DESC_PARACETAMOL + INVALID_CONSUMPTION_PER_DAY_DESC,
                MedicineName.MESSAGE_MEDICINE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + APPOINTMENT_ID_DESC_FIRST
                + MEDICINE_NAME_DESC_PARACETAMOL + DOSAGE_DESC_PARACETAMOL + CONSUMPTION_PER_DAY_DESC_PARACETAMOL,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPrescriptionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + MEDICINE_NAME_DESC_PARACETAMOL + DOSAGE_DESC_PARACETAMOL
                + CONSUMPTION_PER_DAY_DESC_PARACETAMOL , MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + MEDICINE_NAME_DESC_PARACETAMOL + DOSAGE_DESC_PARACETAMOL
                + CONSUMPTION_PER_DAY_DESC_PARACETAMOL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
}
