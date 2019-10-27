package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_FACEBOOK;
import static seedu.address.logic.commands.CommandTestUtil.SUBTITLE_DESC_FACEBOOK;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_FACEBOOK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_FACEBOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEntryInfoCommand;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.util.EntryBuilder;

public class EditEntryInfoCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEntryInfoCommand.MESSAGE_USAGE);

    private EditEntryInfoCommandParser parser = new EditEntryInfoCommandParser();

    private ResumeEntry editedEntry = new EntryBuilder().withCategory("work")
            .withTitle("Facebook").withDuration("2010 - 2013")
            .withSubHeader("software engineering intern")
            .withTags("java").build();
    private EditEntryInfoCommand.EditEntryInfoDescriptor descriptor =
            new EditEntryInfoCommand.EditEntryInfoDescriptor(editedEntry);

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_FACEBOOK, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEntryInfoCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_ENTRY;
        String userInput = targetIndex.getOneBased()
                + TITLE_DESC_FACEBOOK + SUBTITLE_DESC_FACEBOOK + DURATION_FACEBOOK;

        EditEntryInfoCommand expectedCommand = new EditEntryInfoCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
