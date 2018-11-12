package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;

/**
 * Remove tags to specified entry.
 */
public class TagRmCommand extends TagCommand {
    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_WORD2 = "rm";

    public static final String MESSAGE_SUCCESS = "Removed tags from entry";

    public static final String MESSAGE_USAGE = TagCommand.COMMAND_WORD + " " + COMMAND_WORD + ": Remove tags from entry"
            + " (leave empty to remove all tags) \n"
            + "Parameters: INDEX "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + TagCommand.COMMAND_WORD + " " + COMMAND_WORD + " 1 "
            + PREFIX_TAG + "java ";

    private final Index index;
    private Set<Tag> tags;

    /**
     * Creates an TagRmCommand to edit the specified {@code ResumeEntry}
     */
    public TagRmCommand(Index index, Set<Tag> tags) {
        this.tags = tags;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ResumeEntry> lastShownList = model.getFilteredEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        ResumeEntry entryToEdit = lastShownList.get(index.getZeroBased());
        ResumeEntry editedEntry = newEntry(entryToEdit);

        model.updateEntry(entryToEdit, editedEntry);
        model.updateFilteredEntryList(Model.PREDICATE_SHOW_ALL_ENTRIES);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS));
    }

    /**
     * Takes an entry and return an {@code ResumeEntry} with specified tags removed.
     */
    private ResumeEntry newEntry(ResumeEntry entry) {
        Set<Tag> newTags = new HashSet<>(entry.getTags());
        for (Tag tag : tags) {
            if (newTags.contains(tag)) {
                newTags.remove(tag);
            }
        }

        if (tags.size() == 0) {
            newTags = new HashSet<Tag>();
        }

        return new ResumeEntry(
                entry.getCategory(), entry.getEntryInfo(),
                newTags, entry.getDescription());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagRmCommand // instanceof handles nulls
                && index.equals(((TagRmCommand) other).index)
                && tags.equals(((TagRmCommand) other).tags));
    }
}
