package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBHEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ENTRIES;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryDescription;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;

/**
 * Edits the entryInfo of a particular major entry.
 */
public class EditEntryInfoCommand extends Command {
    public static final String COMMAND_WORD = "editEntryInfo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the title, subtitle or duration of the entry identified "
            + "by the index number used in the displayed entry list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_SUBHEADER + "SUBTITLE] "
            + "[" + PREFIX_DURATION + "DURATION]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TITLE + "NUS Education"
            + PREFIX_SUBHEADER + "Bachelor of Computing in Computer Science";

    public static final String MESSAGE_EDIT_ENTRYINFO_SUCCESS = "Edited Entry: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ENTRY = "This entry already exists in the entry book.";
    public static final String MESSAGE_NON_MAJOR_ENTRY = "The entry is not "
            + "a major entry, i.e. does not contain title,subtitle or duration.";

    private final Index index;
    private final EditEntryInfoDescriptor editEntryInfoDescriptor;

    /**
     * @param index of the entry in the filtered entry list to edit
     * @param editEntryInfoDescriptor EditEntryInfoDescriptor instance to edit
     */
    public EditEntryInfoCommand(Index index, EditEntryInfoDescriptor editEntryInfoDescriptor) {
        requireNonNull(index);
        requireNonNull(editEntryInfoDescriptor);

        this.index = index;
        this.editEntryInfoDescriptor = editEntryInfoDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<ResumeEntry> lastShownList = model.getFilteredEntryList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ENTRY_DISPLAYED_INDEX);
        }

        ResumeEntry entryToEdit = lastShownList.get(index.getZeroBased());

        if (entryToEdit.isMinorEntry()) {
            throw new CommandException(MESSAGE_NON_MAJOR_ENTRY);
        }

        ResumeEntry editedEntry = createEditedEntry(entryToEdit, editEntryInfoDescriptor);
        if (!entryToEdit.isSameEntry(editedEntry) && model.hasEntry(editedEntry)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENTRY);
        }

        model.updateEntry(entryToEdit, editedEntry);
        model.updateFilteredEntryList(PREDICATE_SHOW_ALL_ENTRIES);
        model.commitEntryBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ENTRYINFO_SUCCESS, editedEntry));
    }


    /**
     * Creates and returns a {@code ResumeEntry} with the details of {@code entryToEdit}
     * edited with {@code editEntryInfoDescriptor}.
     */
    private static ResumeEntry createEditedEntry(ResumeEntry entryToEdit,
                                                 EditEntryInfoDescriptor editEntryInfoDescriptor) {
        assert entryToEdit != null;
        // non EntryInfo fields preserved
        Category updatedCategory = entryToEdit.getCategory();
        Set<Tag> updatedTags = entryToEdit.getTags();
        EntryDescription updatedEntryDescription = entryToEdit.getDescription();


        String title = editEntryInfoDescriptor.getTitle().orElse(entryToEdit.getEntryInfo().getTitle());
        String subtitle = editEntryInfoDescriptor.getSubtitle().orElse(entryToEdit.getEntryInfo().getSubHeader());
        String duration = editEntryInfoDescriptor.getDuration().orElse(entryToEdit.getEntryInfo().getDuration());
        EntryInfo updatedEntryInfo = new EntryInfo(title, subtitle, duration);

        return new ResumeEntry(updatedCategory, updatedEntryInfo, updatedTags, updatedEntryDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditEntryInfoCommand)) {
            return false;
        }

        // state check
        EditEntryInfoCommand e = (EditEntryInfoCommand) other;
        return index.equals(e.index)
                && editEntryInfoDescriptor.equals(e.editEntryInfoDescriptor);
    }


    /**
     * Stores the details to edit the entry with. Each non-empty field value will replace the
     * corresponding field value of the entry.
     */
    public static class EditEntryInfoDescriptor {
        private String title;
        private String subtitle;
        private String duration;

        public EditEntryInfoDescriptor() {}

        /**
         * Constructing a duplicated copy of EditEntryInfoDescriptor
         * @param toCopy EditEntryInfoDescriptor instance to be duplicated
         */
        public EditEntryInfoDescriptor(EditEntryInfoDescriptor toCopy) {
            setTitle(toCopy.title);
            setSubtitle(toCopy.subtitle);
            setDuration(toCopy.duration);
        }

        /**
         * Constructing a EditEntryInfoDescriptor by extracting the title, subtitle and duration of an entry.
         * @param entry ResumeEntry instance to be extracted
         */
        public EditEntryInfoDescriptor(ResumeEntry entry) {
            setTitle(entry.getEntryInfo().getTitle());
            setSubtitle(entry.getEntryInfo().getSubHeader());
            setDuration(entry.getEntryInfo().getDuration());
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(title, subtitle, duration);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Optional<String> getSubtitle() {
            return Optional.ofNullable(subtitle);
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public Optional<String> getDuration() {
            return Optional.ofNullable(duration);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEntryInfoDescriptor)) {
                return false;
            }

            // state check
            EditEntryInfoDescriptor e = (EditEntryInfoDescriptor) other;


            return getTitle().equals(e.getTitle())
                    && getSubtitle().equals(e.getSubtitle())
                    && getDuration().equals(e.getDuration());
        }

        @Override
        public String toString() {
            return title + " " + subtitle + " " + duration;
        }
    }



}

