package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ShowTagsRequestEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.logic.parser.CliSyntax;
import ssp.scheduleplanner.model.Model;

/**
 * Shows the tags under a specified category.
 */
public class ShowTagsCommand extends Command {

    public static final String COMMAND_WORD = "tags";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Expands the tags under the specified category. \n"
            + "Parameters: "
            + CliSyntax.PREFIX_CATEGORY + "CATEGORY "
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_CATEGORY + "Modules ";

    public static final String MESSAGE_SUCCESS = "The tags under that category are shown. ";
    public static final String MESSAGE_CATEGORY_NONEXISTENT = "This category does not exist.";

    private final String categoryName;

    /**
     * Creates a ShowTagsCommand to display tags under the specified {@code category}
     */
    public ShowTagsCommand(String category) {
        requireNonNull(category);
        categoryName = category;
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(categoryName)) {
            throw new CommandException(MESSAGE_CATEGORY_NONEXISTENT);
        }
        EventsCenter.getInstance().post(new ShowTagsRequestEvent(categoryName));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
