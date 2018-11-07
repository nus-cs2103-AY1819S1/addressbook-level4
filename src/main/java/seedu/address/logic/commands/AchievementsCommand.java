package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.achievement.AchievementRecord;

/**
 * Set achievement record's display preference:
 * display all-time's, today's or this week's achievements on achievement panel.
 */
public class AchievementsCommand extends Command {
    public static final String ALL_TIME_OPTION = "all-time";
    public static final String TODAY_OPTION = "today";
    public static final String THIS_WEEK_OPTION = "this week";

    public static final String COMMAND_WORD = "achievements";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display all-time, today's or this week's achievements on achievement panel.\n"
            + "Parameters: " + ALL_TIME_OPTION + " or " + TODAY_OPTION + " or " + THIS_WEEK_OPTION + "\n"
            + "Example: " + COMMAND_WORD + " " + THIS_WEEK_OPTION;

    public static final String MESSAGE_DISPLAY_ACHIEVEMENT_SUCCESS = "%s's achievements successfully displayed!";


    private final String displayTimeSpan;

    private final int displayOption;

    /**
     * Create an {@code AchievementsCommand} to display the achievements in the specified {@param displayTimeSpan}.
     */
    public AchievementsCommand(String displayTimeSpan) {
        this.displayTimeSpan = displayTimeSpan;
        this.displayOption = getDisplayOptionFromTimeSpan(displayTimeSpan);
    }


    private int getDisplayOptionFromTimeSpan(String displayTimeSpan) {
        switch (displayTimeSpan) {
        case ALL_TIME_OPTION:
            return AchievementRecord.DISPLAY_ALL_TIME;
        case TODAY_OPTION:
            return AchievementRecord.DISPLAY_TODAY;
        case THIS_WEEK_OPTION:
            return AchievementRecord.DISPLAY_THIS_WEEK;
        default:
            // this case should never be reached given that command argument is correctly parsed
            assert false;
            return AchievementRecord.DISPLAY_ALL_TIME;
        }
    }

    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) {
        requireNonNull(model);

        model.updateAchievementDisplayOption(displayOption);
        model.commitTaskManager();
        return new CommandResult(String.format(MESSAGE_DISPLAY_ACHIEVEMENT_SUCCESS, displayTimeSpan));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AchievementsCommand // instanceof handles nulls
                && displayTimeSpan.equals(((AchievementsCommand) other).displayTimeSpan));
    }
}
