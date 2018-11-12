package seedu.address.logic.parser.jio;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.jio.JioTestUtil.ADDRESS_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.DAY_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_DAY_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_NAME_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_TIME_DESC;
import static seedu.address.model.jio.JioTestUtil.INVALID_WEEK_DESC;
import static seedu.address.model.jio.JioTestUtil.MALA;
import static seedu.address.model.jio.JioTestUtil.NAME_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.TIME_DESC_MALA;
import static seedu.address.model.jio.JioTestUtil.WEEK_DESC_MALA;

import org.junit.Test;

import seedu.address.logic.commands.jio.CreateJioCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Name;
import seedu.address.model.jio.Jio;
import seedu.address.model.restaurant.Address;
import seedu.address.model.timetable.Day;
import seedu.address.model.timetable.Time;
import seedu.address.model.timetable.Week;

public class CreateJioCommandParserTest {
    private CreateJioCommandParser parser = new CreateJioCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Jio expectedJio = MALA;

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA
                + TIME_DESC_MALA + ADDRESS_DESC_MALA, new CreateJioCommand(expectedJio));
    }

    @Test
    public void parse_groupJio_success() {
        //Jio expectedJio = GROUPJIO;
        //assertParseSuccess(parser, NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA
        //        + TIME_DESC_MALA + ADDRESS_DESC_MALA + GROUP_DESC_MALA, new CreateJioCommand(expectedJio));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, WEEK_DESC_MALA + DAY_DESC_MALA + TIME_DESC_MALA + ADDRESS_DESC_MALA,
                expectedMessage);

        // missing week prefix
        assertParseFailure(parser, NAME_DESC_MALA + DAY_DESC_MALA + TIME_DESC_MALA + ADDRESS_DESC_MALA,
                expectedMessage);

        // missing week prefix
        assertParseFailure(parser, NAME_DESC_MALA + WEEK_DESC_MALA + TIME_DESC_MALA + ADDRESS_DESC_MALA,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA + ADDRESS_DESC_MALA,
                expectedMessage);

        // all address missing
        assertParseFailure(parser, NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA + TIME_DESC_MALA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + WEEK_DESC_MALA + DAY_DESC_MALA
                + TIME_DESC_MALA + ADDRESS_DESC_MALA, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid week
        assertParseFailure(parser, NAME_DESC_MALA + INVALID_WEEK_DESC + DAY_DESC_MALA
                + TIME_DESC_MALA + ADDRESS_DESC_MALA, Week.MESSAGE_WEEK_CONSTRAINTS);

        // invalid day
        assertParseFailure(parser, NAME_DESC_MALA + WEEK_DESC_MALA + INVALID_DAY_DESC
                + TIME_DESC_MALA + ADDRESS_DESC_MALA, Day.MESSAGE_DAY_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA
                + INVALID_TIME_DESC + ADDRESS_DESC_MALA, Time.MESSAGE_TIME_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA
                + TIME_DESC_MALA + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_WEEK_DESC + DAY_DESC_MALA
                        + TIME_DESC_MALA + ADDRESS_DESC_MALA,
                Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_MALA + WEEK_DESC_MALA + DAY_DESC_MALA
                        + TIME_DESC_MALA + ADDRESS_DESC_MALA,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateJioCommand.MESSAGE_USAGE));
    }
}
