package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.MakeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class MakeCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private MakeCommandParser parser = new MakeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Path testPath = Paths.get("test", "path", "file.txt");
        assertParseSuccess(parser, "test/path/file.txt", new MakeCommand(testPath));
    }

    @Test
    public void parse_invalidArgument_exception() throws Exception {
        thrown.expect(ParseException.class);
        parser.parse("");
    }
}
