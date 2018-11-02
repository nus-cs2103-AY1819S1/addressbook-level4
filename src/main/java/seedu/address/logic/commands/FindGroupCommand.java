package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Finds and lists all groups in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindGroupCommand extends FindCommand<Group> {

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
                String.format(Messages.MESSAGE_GROUPS_FOUND_OVERVIEW, model.getFilteredGroupList().size()));
    }
}
