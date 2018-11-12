package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AttachmentCommand.COMMAND_ADD_ACTION;
import static seedu.address.logic.commands.AttachmentCommand.COMMAND_DELETE_ACTION;
import static seedu.address.logic.commands.AttachmentCommand.COMMAND_GET_ACTION;
import static seedu.address.logic.commands.AttachmentCommand.COMMAND_LIST_ACTION;
import static seedu.address.logic.commands.AttachmentCommand.MESSAGE_MISSING_ARGUMENTS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttachmentCommand;
import seedu.address.logic.parser.exceptions.SimpleParseException;
import seedu.address.logic.parser.tokenizer.ArgumentMultimap;
import seedu.address.logic.parser.tokenizer.exceptions.TokenizationException;

/**
 * Parses input arguments and creates a new AttachmentCommand object
 */
public class AttachmentCommandParser implements Parser<AttachmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AttachmentCommand and returns an
     * AttachmentCommand object for execution.
     *
     * @throws SimpleParseException if the user input does not conform the expected format
     */
    public AttachmentCommand parse(String args) throws SimpleParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap;
        try {
            argMultimap =
                    ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH, PREFIX_FILENAME);
        } catch (TokenizationException ime) {
            throw new SimpleParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachmentCommand.MESSAGE_USAGE), ime);
        }

        Index index;
        String actionWord;
        try {
            String[] indexAndCommand = argMultimap.getPreamble().split(" ");
            index = ParserUtil.parseIndex(indexAndCommand[0]);
            actionWord = indexAndCommand[1].trim();
        } catch (SimpleParseException pe) {
            throw new SimpleParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachmentCommand.MESSAGE_USAGE), pe);
        } catch (ArrayIndexOutOfBoundsException ae) {
            throw new SimpleParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachmentCommand.MESSAGE_USAGE), ae);
        }

        switch (actionWord) {
        case COMMAND_ADD_ACTION:
            return parseAddAction(index, argMultimap);
        case COMMAND_LIST_ACTION:
            return parseListAction(index, argMultimap);
        case COMMAND_DELETE_ACTION:
            return parseDeleteAction(index, argMultimap);
        case COMMAND_GET_ACTION:
            return parseGetAction(index, argMultimap);
        default:
            throw new SimpleParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttachmentCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Generates the attachment command for an add attachment action.
     */
    private AttachmentCommand parseAddAction(Index index, ArgumentMultimap argMultimap) throws SimpleParseException {
        if (!argMultimap.getValue(PREFIX_FILEPATH).isPresent()) {
            throw new SimpleParseException(String.format(MESSAGE_MISSING_ARGUMENTS, "FILEPATH", "add"));
        }
        String filePath = argMultimap.getValue(PREFIX_FILEPATH).get();
        AttachmentCommand.AttachmentAction action = new AttachmentCommand.AddAttachmentAction(filePath);
        return new AttachmentCommand(index, action);
    }


    /**
     * Generates the attachment command for a list attachment action.
     */
    private AttachmentCommand parseListAction(Index index, ArgumentMultimap argMultimap) throws SimpleParseException {
        AttachmentCommand.AttachmentAction action = new AttachmentCommand.ListAttachmentAction();
        return new AttachmentCommand(index, action);
    }

    /**
     * Generates the attachment command for a delete attachment action.
     */
    private AttachmentCommand parseDeleteAction(Index index, ArgumentMultimap argMultimap) throws SimpleParseException {
        if (!argMultimap.getValue(PREFIX_FILENAME).isPresent()) {
            throw new SimpleParseException(String.format(MESSAGE_MISSING_ARGUMENTS, "FILENAME", "delete"));
        }
        String fileName = argMultimap.getValue(PREFIX_FILENAME).get();
        AttachmentCommand.AttachmentAction action = new AttachmentCommand.DeleteAttachmentAction(fileName);
        return new AttachmentCommand(index, action);
    }

    /**
     * Generates the attachment command for a delete attachment action.
     */
    private AttachmentCommand parseGetAction(Index index, ArgumentMultimap argMultimap) throws SimpleParseException {
        if (!argMultimap.getValue(PREFIX_FILENAME).isPresent()) {
            throw new SimpleParseException(String.format(MESSAGE_MISSING_ARGUMENTS, "FILENAME", "get"));
        }
        if (!argMultimap.getValue(PREFIX_FILEPATH).isPresent()) {
            throw new SimpleParseException(String.format(MESSAGE_MISSING_ARGUMENTS, "FILEPATH", "get"));
        }
        String fileName = argMultimap.getValue(PREFIX_FILENAME).get();
        String filePath = argMultimap.getValue(PREFIX_FILEPATH).get();
        AttachmentCommand.AttachmentAction action = new AttachmentCommand.GetAttachmentAction(fileName, filePath);
        return new AttachmentCommand(index, action);
    }
}
