package seedu.meeting.logic.commands;

import static seedu.meeting.logic.commands.FindGroupCommand.FIND_GROUP_PARAM;
import static seedu.meeting.logic.commands.FindGroupCommand.FIND_GROUP_PARAM_SHORT;
import static seedu.meeting.logic.commands.FindMeetingCommand.FIND_MEETING_PARAM;
import static seedu.meeting.logic.commands.FindMeetingCommand.FIND_MEETING_PARAM_SHORT;
import static seedu.meeting.logic.commands.FindPersonCommand.FIND_PERSON_PARAM;
import static seedu.meeting.logic.commands.FindPersonCommand.FIND_PERSON_PARAM_SHORT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_ALL;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NONE;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_SOME;

import java.util.function.Predicate;

/**
 * Finds and lists all entities in MeetingBook whose property contains any of the argument keywords.
 * Keyword matching is case insensitive.
 * @param <E> The type of the entity.
 */
public abstract class FindCommand<E> extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons, groups, or meetings whose names "
        + "or titles contain any of "
        + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
        + "Parameters: "
        + "[" + FIND_PERSON_PARAM + "/" + FIND_PERSON_PARAM_SHORT
        + "/" + FIND_GROUP_PARAM + "/" + FIND_GROUP_PARAM_SHORT
        + "/" + FIND_MEETING_PARAM + "/" + FIND_MEETING_PARAM_SHORT + "] "
        + "[" + PREFIX_ALL + "KEYWORDS...(matches all keywords given)] "
        + "[" + PREFIX_SOME + "KEYWORDS...(matches some of the keywords given)] "
        + "[" + PREFIX_NONE + "KEYWORDS...(matches none of the keywords given)]\n"
        + "If no prefixes are used, it will default to match all keywords given.\n"
        + "Example: " + COMMAND_WORD + " " + FIND_PERSON_PARAM_SHORT + " "
        + PREFIX_ALL + "alice "
        + PREFIX_SOME + "bob "
        + PREFIX_NONE + "charlie\n"
        + "Prefixes not used: " + COMMAND_WORD + " " + FIND_GROUP_PARAM_SHORT + " friends";

    protected final Predicate<E> predicate;

    FindCommand(Predicate<E> predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof FindCommand // instanceof handles nulls
            && predicate.equals(((FindCommand) other).predicate)); // state check
    }
}
