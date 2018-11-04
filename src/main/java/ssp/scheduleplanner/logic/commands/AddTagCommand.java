package ssp.scheduleplanner.logic.commands;

import static java.util.Objects.requireNonNull;

import ssp.scheduleplanner.commons.core.EventsCenter;
import ssp.scheduleplanner.commons.events.ui.ChangeViewEvent;
import ssp.scheduleplanner.logic.CommandHistory;
import ssp.scheduleplanner.logic.commands.exceptions.CommandException;
import ssp.scheduleplanner.logic.parser.CliSyntax;
import ssp.scheduleplanner.model.Model;
import ssp.scheduleplanner.model.tag.Tag;

/**
 * Adds a tag to the given category.
 */
public class AddTagCommand extends Command {
    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a tag to selected category in the Schedule Planner. "
            + "Parameters: "
            + CliSyntax.PREFIX_CATEGORY + "CATEGORY "
            + CliSyntax.PREFIX_TAG + "TAG "
            + "Example: " + COMMAND_WORD + " "
            + CliSyntax.PREFIX_CATEGORY + "Modules "
            + CliSyntax.PREFIX_TAG + "CS2103T ";

    public static final String MESSAGE_SUCCESS = "New tag %1$s added to category %2$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This tag already exists in this category";
    public static final String MESSAGE_CATEGORY_NONEXISTENT = "This category does not exist.";

    private final Tag tagToAdd;
    private final String categoryName;

    /**
     * Creates an AddTagCommand to add the specified {@code Tag}
     */
    public AddTagCommand(Tag tag, String addTo) {
        requireNonNull(tag);
        tagToAdd = tag;
        categoryName = addTo;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasCategory(categoryName)) {
            throw new CommandException(MESSAGE_CATEGORY_NONEXISTENT);
        }

        if (model.hasTagInCategory(tagToAdd, model.getCategory(categoryName))) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }

        model.addTag(tagToAdd, categoryName);
        model.commitSchedulePlanner();
        EventsCenter.getInstance().post(new ChangeViewEvent(ChangeViewEvent.View.NORMAL));
        return new CommandResult(String.format(MESSAGE_SUCCESS, tagToAdd, categoryName));
    }
}
