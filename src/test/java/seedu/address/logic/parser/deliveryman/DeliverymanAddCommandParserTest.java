package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_RAJUL;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_RAJUL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalDeliverymen.RAJUL;

import org.junit.Test;

import seedu.address.logic.commands.deliveryman.DeliverymanAddCommand;
import seedu.address.model.common.Name;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.testutil.DeliverymanBuilder;

public class DeliverymanAddCommandParserTest {
    private DeliverymanAddCommandParser parser = new DeliverymanAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Deliveryman expectedDeliveryman = new DeliverymanBuilder(RAJUL).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_RAJUL,
                new DeliverymanAddCommand(expectedDeliveryman));

        assertParseSuccess(parser, NAME_DESC_RAJUL,
                new DeliverymanAddCommand(expectedDeliveryman));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliverymanAddCommand.MESSAGE_USAGE);

        //missing name prefic
        assertParseFailure(parser, VALID_NAME_RAJUL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
    }
}
