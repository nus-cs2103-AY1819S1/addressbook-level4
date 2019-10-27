package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddBulletCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AddBulletCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AddBulletCommandParser parser = new AddBulletCommandParser();

    private String description = "attained Best Financial Hack Award";

    @Test
    public void parse_allFieldsPresent_success() {
        // normal command index separated by 1 space
        assertParseSuccess(parser, "1 " + description,
                new AddBulletCommand(Index.fromOneBased(1), description));

        assertParseSuccess(parser, "1 " + " " + description,
                new AddBulletCommand(Index.fromOneBased(1), description));
    }

    @Test
    public void parse_invalidIndex_failure() throws Exception {
        thrown.expect(ParseException.class);
        parser.parse("-9 " + description);
    }

    @Test
    public void parse_noDescription_failure() throws Exception {
        thrown.expect(ParseException.class);
        parser.parse("1");
    }

}
