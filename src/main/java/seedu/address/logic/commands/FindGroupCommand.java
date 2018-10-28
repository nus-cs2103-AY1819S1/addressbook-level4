package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.tag.Tag;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class FindGroupCommand extends FindCommand<Group>{

    public static final String FIND_GROUP_PARAM = "group";
    public static final String FIND_GROUP_PARAM_SHORT = "g";

    public FindGroupCommand(Predicate<Group> predicate) {
        super(predicate);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredGroupList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
