package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELIVERYMAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;

public class AssignCommandParserTest {
    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_validArgs_returnsCreateRouteCommand() {
        Set<Index> orderIds = new HashSet<>();
        orderIds.add(INDEX_FIRST);
        orderIds.add(INDEX_SECOND);
        assertParseSuccess(parser, " " + PREFIX_DELIVERYMAN + "1 " + PREFIX_ORDER + "1 "
                + PREFIX_ORDER + "2", new AssignCommand(INDEX_FIRST, orderIds));
    }

    @Test
    public void parse_invalidOrderIndexNonNumber_failure() {
        // invalid index
        assertParseFailure(parser, " " + PREFIX_DELIVERYMAN + "1 " + PREFIX_ORDER + "a", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidDeliverymanIndexNonNumber_failure() {
        // invalid index
        assertParseFailure(parser, " " + PREFIX_DELIVERYMAN + "a " + PREFIX_ORDER + "1", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_missingDeliverymanIndex_failure() {
        // invalid index
        assertParseFailure(parser, " " + PREFIX_ORDER + "1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
    }
}
