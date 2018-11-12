package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_NAME;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.UpdateTutorialGroupCommand;

public class UpdateTutorialGroupParserTest {
    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                       UpdateTutorialGroupCommand.MESSAGE_USAGE);
    private UpdateTutorialGroupCommandParser parser = new UpdateTutorialGroupCommandParser();

    private String typicalId = "04a";
    private String typicalName = "CS1101S Studio 04A";


    @Test
    public void parse_missingId_failure() {
        assertParseFailure(parser, "test", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noFieldsUpdated_failure() {
        String userInput = " " + PREFIX_TUTORIAL_GROUP_ID + typicalId;
        assertParseFailure(parser, userInput, UpdateTutorialGroupCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = " " + PREFIX_TUTORIAL_GROUP_ID + typicalId + " "
            + PREFIX_TUTORIAL_GROUP_NAME + typicalName;
        UpdateTutorialGroupCommand.UpdateTutorialGroupDescriptor descriptor = new UpdateTutorialGroupCommand
            .UpdateTutorialGroupDescriptor();
        descriptor.setName(typicalName);
        UpdateTutorialGroupCommand expectedCommand = new UpdateTutorialGroupCommand(typicalId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
