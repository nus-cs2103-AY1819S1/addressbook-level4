package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PICTURE_PATH;
import static seedu.address.logic.commands.CommandTestUtil.PICTURE_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.PictureCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Picture;

//@@author denzelchung
public class PictureCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, PictureCommand.MESSAGE_USAGE);

    private PictureCommandParser parser = new PictureCommandParser();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, PICTURE_PATH, MESSAGE_INVALID_FORMAT);

        // no file location specified
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + "", MESSAGE_INVALID_FORMAT);

        // no name and no file location specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + INDEX_FIRST_PERSON, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + INDEX_FIRST_PERSON, MESSAGE_INVALID_FORMAT);

        // positive index with a plus sign
        assertParseFailure(parser, "+5" + INDEX_FIRST_PERSON, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void runTest_invalidValue_failure() {
        // invalid image
        String userInput = INDEX_FIRST_PERSON.getOneBased() + INVALID_PICTURE_PATH;
        assertParseFailure(parser, userInput, Picture.MESSAGE_PICTURE_CONSTRAINTS);
    }
}
