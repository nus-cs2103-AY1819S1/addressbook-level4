package seedu.learnvocabulary.logic.parser;

import seedu.learnvocabulary.logic.commands.Command;
import seedu.learnvocabulary.logic.commands.GroupaddCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;

/**
 * Create a new groupAdd command with given user input
 */
public class GroupAddCommandParser<T extends Command> implements Parser<GroupaddCommand> {
    @Override
    public GroupaddCommand parse(String userInput) throws ParseException {
        Tag tag = new Tag(userInput.trim());
        return new GroupaddCommand(tag);
    }
}
