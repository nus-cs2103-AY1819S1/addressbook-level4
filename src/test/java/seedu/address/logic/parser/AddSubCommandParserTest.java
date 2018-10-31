package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddSubCommand;
import seedu.address.model.subject.Subject;

public class AddSubCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSubCommand.MESSAGE_USAGE);

    private AddSubCommandParser parser = new AddSubCommandParser();

    @Test
    public void parse_validArgs_returnsAddSubCommand() {
        assertParseSuccess(parser, "1 s/Physics",
                new AddSubCommand(INDEX_FIRST_PERSON, Subject.makeSubject("Physics")));
        assertParseSuccess(parser, "2 s/Mathematics",
                new AddSubCommand(INDEX_SECOND_PERSON, Subject.makeSubject("Mathematics")));
        assertParseSuccess(parser, "3 s/Chemistry",
                new AddSubCommand(INDEX_THIRD_PERSON, Subject.makeSubject("Chemistry")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Negative index
        assertParseFailure(parser, "-1 s/Physics", MESSAGE_INVALID_FORMAT);

        // Zero index
        assertParseFailure(parser, "0 s/Mathematics", MESSAGE_INVALID_FORMAT);

        // Invalid index
        assertParseFailure(parser, "a", MESSAGE_INVALID_FORMAT);

        // Missing subject
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // Invalid subject
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // Error on the syllabus parsing should show syllabus constraints instead
        assertParseFailure(parser, "1 s/ ", Subject.MESSAGE_SUBJECT_CONSTRAINTS);
    }

}
