package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.TagAddCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagListCommand;
import seedu.address.logic.commands.TagRetagCommand;
import seedu.address.logic.commands.TagRmCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Sub parser for tag related functions
 */
public class TagCommandParser implements Parser<TagCommand> {
    public static final String MESSAGE_USAGE = TagCommand.MESSAGE_USAGE
        + "- " + TagCommand.COMMAND_WORD + " " + TagListCommand.COMMAND_WORD + " help\n"
        + "- " + TagCommand.COMMAND_WORD + " " + TagAddCommand.COMMAND_WORD + " help\n"
        + "- " + TagCommand.COMMAND_WORD + " " + TagRmCommand.COMMAND_WORD + " help\n"
        + "- " + TagCommand.COMMAND_WORD + " " + TagRetagCommand.COMMAND_WORD + " help\n";

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into tag related commands.
     *
     * Parses the given {@code String} of arguments in the context of the relevant TagCommand
     * and returns an TagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {
        case TagListCommand.COMMAND_WORD:
        case TagListCommand.COMMAND_WORD2:
            return new TagListCommandParser().parse(arguments);

        case TagAddCommand.COMMAND_WORD:
            return new TagAddCommandParser().parse(arguments);

        case TagRmCommand.COMMAND_WORD:
        case TagRmCommand.COMMAND_WORD2:
            return new TagRmCommandParser().parse(arguments);

        case TagRetagCommand.COMMAND_WORD:
        case TagRetagCommand.COMMAND_WORD2:
            return new TagRetagCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
