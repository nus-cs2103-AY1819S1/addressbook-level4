package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.superta.commons.core.EventsCenter;
import seedu.superta.commons.events.ui.ViewAllTutorialGroupsEvent;
import seedu.superta.logic.CommandHistory;
import seedu.superta.model.Model;
import seedu.superta.model.tutorialgroup.TutorialGroup;

// @@author Caephler
/**
 * Command to list all tutorial groups.
 */
public class ListTutorialGroupsCommand extends Command {
    public static final String COMMAND_WORD = "list-tutorial-groups";

    public static final String MESSAGE_SUCCESS = "Listed all tutorial groups. \n%1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) {
        requireNonNull(model);
        List<TutorialGroup> tutorialGroups = model.getTutorialGroupList();
        String result = String.join("\n", tutorialGroups.stream().map(TutorialGroup::toString)
            .collect(Collectors.toList()));
        EventsCenter.getInstance().post(new ViewAllTutorialGroupsEvent());
        return new CommandResult(String.format(MESSAGE_SUCCESS, result));

    }
}
