package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.learnvocabulary.logic.commands.Command;
import seedu.learnvocabulary.logic.commands.GroupdeleteCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;

//@@author Harryqu123
/**
 * Create a new groupDelete command with given user input
 */
public class GroupDeleteCommandParser<T extends Command> implements Parser<GroupdeleteCommand> {
    @Override
    public GroupdeleteCommand parse(String userInput) throws ParseException {
        if (userInput.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupdeleteCommand.MESSAGE_USAGE));
        }
        if (userInput.trim().contains(" ")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupdeleteCommand.MESSAGE_MULTI_GROUP));
        }
        if (!Tag.isValidTagName(userInput.trim())) {
            throw new ParseException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
        Tag tag = new Tag(userInput.trim());
        return new GroupdeleteCommand(tag);
    }
}
//@@author
