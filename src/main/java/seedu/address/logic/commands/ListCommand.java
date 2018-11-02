package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    // @@author jeffreyooi
    /**
     * Available list command types
     */
    public enum ListCommandType {
        GROUP, PERSON, MEETING
    }

    public static final String COMMAND_WORD = "list";

    public static final String COMMAND_PARAM_GROUP = "group";
    public static final String COMMAND_PARAM_GROUP_SHORT = "g";
    public static final String COMMAND_PARAM_PERSON = "person";
    public static final String COMMAND_PARAM_PERSON_SHORT = "p";
    public static final String COMMAND_PARAM_MEETING = "meeting";
    public static final String COMMAND_PARAM_MEETING_SHORT = "m";

    public static final String MESSAGE_SUCCESS_PERSON = "Listed all persons";
    public static final String MESSAGE_SUCCESS_GROUP = "Listed all groups";
    public static final String MESSAGE_SUCCESS_MEETING = "Listed all meetings";

    public static final String MESSAGE_USAGE = "";

    private ListCommandType listCommandType;

    public ListCommand(ListCommandType listCommandType) {
        this.listCommandType = listCommandType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        switch (listCommandType) {
        case PERSON:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_PERSON);
        case GROUP:
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            return new CommandResult(MESSAGE_SUCCESS_GROUP);
        case MEETING:
            model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
            return new CommandResult(MESSAGE_SUCCESS_MEETING);
        default:
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ListCommand // instanceof handles nulls
            && listCommandType.equals(((ListCommand) other).listCommandType)); // state check
    }
}
