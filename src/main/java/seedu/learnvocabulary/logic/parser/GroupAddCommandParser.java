package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.learnvocabulary.logic.commands.Command;
import seedu.learnvocabulary.logic.commands.GroupaddCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;

//@@author Harryqu123
/**
 * Create a new groupAdd command with given user input
 */
public class GroupAddCommandParser<T extends Command> implements Parser<GroupaddCommand> {
    @Override
    public GroupaddCommand parse(String userInput) throws ParseException {
        if (userInput.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupaddCommand.MESSAGE_USAGE));
        }
        Tag tag = new Tag(userInput.trim());
        return new GroupaddCommand(tag);
    }
}
//@@author
