package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;

/**
 * Filter entries to ResuMaker.
 */
public class TagListCommand extends TagCommand {
    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD2 = "ls";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD
            + ": Filter list of entries \n"
            + "Parameters: "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "work "
            + PREFIX_TAG + "java ";

    private String category;
    private List<String> tags;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public TagListCommand(String category, List<String> tags) {
        this.category = category;
        this.tags = tags;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (category.equals("") && tags.size() == 0) {
            model.updateFilteredEntryList(model.PREDICATE_SHOW_ALL_ENTRIES);
        } else {
            model.updateFilteredEntryList(model.mkPredicate(category, tags));
        }

        return new CommandResult(String.format(
                    Messages.MESSAGE_ENTRIES_LISTED_OVERVIEW,
                    model.getFilteredEntryList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagListCommand // instanceof handles nulls
                && category.equals(((TagListCommand) other).category)
                && tags.equals(((TagListCommand) other).tags));
    }
}
