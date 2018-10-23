package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BENSON;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_BENSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY_APPT;

import org.junit.Test;

import seedu.address.logic.commands.AddApptCommand;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

public class AddApptCommandParserTest {
    private AddApptCommandParser parser = new AddApptCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment expectedAppt = new AppointmentBuilder(AMY_APPT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DATE_DESC_AMY + TIME_DESC_AMY
                + ID_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple dates - last accepted
        assertParseSuccess(parser, DATE_DESC_BENSON + DATE_DESC_AMY + TIME_DESC_AMY
                + ID_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple times - last accepted
        assertParseSuccess(parser, DATE_DESC_AMY + TIME_DESC_BENSON + TIME_DESC_AMY
                + ID_DESC_AMY, new AddApptCommand(expectedAppt));

        //multiple ids - last accepted
        assertParseSuccess(parser, DATE_DESC_AMY + TIME_DESC_AMY + ID_DESC_BENSON
                + ID_DESC_BENSON, new AddApptCommand(expectedAppt));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
    }

    @Test
    public void parse_invalidValue_failure() {
    }
}
