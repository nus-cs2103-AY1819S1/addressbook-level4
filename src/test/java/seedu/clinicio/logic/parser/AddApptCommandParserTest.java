package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.ADDRESS_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.DATE_DESC_BENSON;
import static seedu.clinicio.logic.commands.CommandTestUtil.EMAIL_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.NAME_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.NRIC_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.NRIC_DESC_BRYAN;
import static seedu.clinicio.logic.commands.CommandTestUtil.PHONE_DESC_ALEX;
import static seedu.clinicio.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.clinicio.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.TIME_DESC_BENSON;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TIME_AMY;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.clinicio.testutil.TypicalPersons.ALEX_APPT;

import org.junit.Test;

import seedu.clinicio.logic.commands.AddApptCommand;
import seedu.clinicio.model.appointment.Appointment;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;
import seedu.clinicio.model.patient.Nric;
import seedu.clinicio.testutil.AppointmentBuilder;

public class AddApptCommandParserTest {
    private AddApptCommandParser parser = new AddApptCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppt = new AppointmentBuilder(ALEX_APPT).build();
        //expectedAppt.setAssignedStaff(null);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_AMY + TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_ALEX
                + NRIC_DESC_ALEX
                + PHONE_DESC_ALEX
                + EMAIL_DESC_ALEX
                + ADDRESS_DESC_ALEX, new AddApptCommand(expectedAppt));

        //multiple dates - last accepted
        assertParseSuccess(parser, DATE_DESC_BENSON + DATE_DESC_AMY + TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_ALEX
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple times - last accepted
        assertParseSuccess(parser, DATE_DESC_AMY + TIME_DESC_BENSON + TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_ALEX
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple ids - last accepted
        assertParseSuccess(parser, DATE_DESC_AMY + TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_BRYAN
                + NRIC_DESC_ALEX
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, new AddApptCommand(expectedAppt));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE);

        //missing date
        assertParseFailure(parser, TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_BRYAN
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing date prefix
        assertParseFailure(parser, VALID_DATE_AMY
                + TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_BRYAN
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing time
        assertParseFailure(parser, DATE_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_BRYAN
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing time prefix
        assertParseFailure(parser, DATE_DESC_AMY
                + VALID_TIME_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + NRIC_DESC_BRYAN
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing type prefix
        assertParseFailure(parser, DATE_DESC_AMY
                + VALID_TIME_AMY
                + " followup"
                + NAME_DESC_AMY
                + NRIC_DESC_BRYAN
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing ic prefix
        assertParseFailure(parser, DATE_DESC_AMY
                + VALID_TIME_AMY
                + " followup"
                + NAME_DESC_AMY
                + "S1111111G"
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid date
        assertParseFailure(parser, INVALID_DATE_DESC
                        + TIME_DESC_AMY
                        + " tp/followup"
                        + NAME_DESC_AMY
                        + NRIC_DESC_BRYAN
                        + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, Date.MESSAGE_DATE_CONSTRAINTS);

        //invalid time
        assertParseFailure(parser, DATE_DESC_AMY
                        + INVALID_TIME_DESC
                        + " tp/followup"
                        + NAME_DESC_AMY
                        + NRIC_DESC_BRYAN
                        + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, Time.MESSAGE_TIME_CONSTRAINTS);

        //invalid ic
        assertParseFailure(parser, DATE_DESC_AMY
                        + TIME_DESC_AMY
                        + " tp/followup"
                        + NAME_DESC_AMY
                        + " ic/S22222222222G"
                        + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, Nric.MESSAGE_NRIC_CONSTRAINTS);
    }
}
