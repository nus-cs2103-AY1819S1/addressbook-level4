package seedu.clinicio.logic.parser;

import static seedu.clinicio.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.clinicio.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.clinicio.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.clinicio.logic.commands.CommandTestUtil.VALID_TIME_AMY;
import static seedu.clinicio.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.clinicio.logic.commands.AddApptCommand;
import seedu.clinicio.model.appointment.Date;
import seedu.clinicio.model.appointment.Time;

public class AddApptCommandParserTest {
    private AddApptCommandParser parser = new AddApptCommandParser();

    //TODO:
    /*@Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppt = new AppointmentBuilder(AMY_APPT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_AMY + TIME_DESC_AMY
                + IC_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple dates - last accepted
        assertParseSuccess(parser, DATE_DESC_BENSON + DATE_DESC_AMY + TIME_DESC_AMY
                + IC_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple times - last accepted
        assertParseSuccess(parser, DATE_DESC_AMY + TIME_DESC_BENSON + TIME_DESC_AMY
                + IC_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple ids - last accepted
        assertParseSuccess(parser, DATE_DESC_AMY + TIME_DESC_AMY + IC_DESC_BENSON
                + IC_DESC_BENSON, new AddApptCommand(expectedAppt));
    }*/

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddApptCommand.MESSAGE_USAGE);

        //missing date
        assertParseFailure(parser, TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing date prefix
        assertParseFailure(parser, VALID_DATE_AMY
                + TIME_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing time
        assertParseFailure(parser, DATE_DESC_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing time prefix
        assertParseFailure(parser, DATE_DESC_AMY
                + VALID_TIME_AMY
                + " tp/followup"
                + NAME_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //missing type prefix
        assertParseFailure(parser, DATE_DESC_AMY
                + VALID_TIME_AMY
                + " followup"
                + NAME_DESC_AMY
                + PHONE_DESC_AMY
                + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY, expectedMessage);

        //TODO: missing patient
    }

    @Test
    public void parse_invalidValue_failure() {
        //invalid date
        assertParseFailure(parser, INVALID_DATE_DESC
                        + TIME_DESC_AMY
                        + " tp/followup"
                        + NAME_DESC_AMY
                        + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, Date.MESSAGE_DATE_CONSTRAINTS);

        //invalid time
        assertParseFailure(parser, DATE_DESC_AMY
                        + INVALID_TIME_DESC
                        + " tp/followup"
                        + NAME_DESC_AMY
                        + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY, Time.MESSAGE_TIME_CONSTRAINTS);

        //TODO: invalid patient
    }
}
