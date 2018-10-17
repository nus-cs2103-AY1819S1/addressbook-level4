package seedu.learnvocabulary.logic.parser;

import seedu.learnvocabulary.logic.commands.Command;
import seedu.learnvocabulary.logic.commands.GroupdeleteCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;

/**
 * Create a new groupDelete command with given user input
 */
public class GroupDeleteCommandParser<T extends Command> implements Parser<GroupdeleteCommand> {
    @Override
    public GroupdeleteCommand parse(String userInput) throws ParseException {
        Tag tag = new Tag(userInput.trim());
        return new GroupdeleteCommand(tag);
    }
}
