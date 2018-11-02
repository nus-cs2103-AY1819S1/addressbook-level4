package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_OCCASIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.TypeUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing occasion in the address book.
 *
 * @author alistair
 * @author kongzijin
 */
public class EditOccasionCommand extends Command {

    public static final String COMMAND_WORD = "editoccasion";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the occasion identified "
            + "by the index number used in the displayed occasion list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_OCCASIONNAME + "OCCASION NAME] "
            + "[" + PREFIX_OCCASIONDATE + "OCCASION DATE] "
            + "[" + PREFIX_OCCASIONLOCATION + "OCCASION LOCATION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_OCCASIONNAME + "Barbecue "
            + PREFIX_OCCASIONDATE + "2019-06-17 "
            + PREFIX_OCCASIONLOCATION + "NUS";

    public static final String MESSAGE_EDIT_OCCASION_SUCCESS = "Edited Occasion: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_OCCASION = "This occasion already exists in the address book.";

    private final Index index;
    private final EditOccasionCommand.EditOccasionDescriptor editOccasionDescriptor;

    /**
     * @param index of the occasion in the filtered occasion list to edit
     * @param editOccasionDescriptor details to edit the occasion with
     */
    public EditOccasionCommand(Index index, EditOccasionCommand.EditOccasionDescriptor editOccasionDescriptor) {
        requireNonNull(index);
        requireNonNull(editOccasionDescriptor);

        this.index = index;
        this.editOccasionDescriptor = new EditOccasionCommand.EditOccasionDescriptor(editOccasionDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Occasion> lastShownList = model.getFilteredOccasionList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_OCCASION_DISPLAYED_INDEX);
        }

        Occasion occasionToEdit = lastShownList.get(index.getZeroBased());
        Occasion editedOccasion = createEditedOccasion(occasionToEdit, editOccasionDescriptor);

        if (!occasionToEdit.isSameOccasion(editedOccasion) && model.hasOccasion(editedOccasion)) {
            throw new CommandException(MESSAGE_DUPLICATE_OCCASION);
        }

        model.updateOccasion(occasionToEdit, editedOccasion);
        model.updateFilteredOccasionList(PREDICATE_SHOW_ALL_OCCASIONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_OCCASION_SUCCESS, editedOccasion));
    }

    /**
     * Creates and returns a {@code Occasion} with the details of {@code occasionToEdit}
     * edited with {@code editOccasionDescriptor}.
     */
    private static Occasion createEditedOccasion(Occasion occasionToEdit,
                                                 EditOccasionCommand.EditOccasionDescriptor editOccasionDescriptor) {
        assert occasionToEdit != null;

        OccasionName updatedOccasionName =
                editOccasionDescriptor.getOccasionName().orElse(occasionToEdit.getOccasionName());
        OccasionDate updatedOccasionDate =
                editOccasionDescriptor.getOccasionDate().orElse(occasionToEdit.getOccasionDate());
        OccasionLocation updatedOccasionLocation =
                editOccasionDescriptor.getOccasionLocation().orElse(occasionToEdit.getOccasionLocation());
        Set<Tag> updatedTags = editOccasionDescriptor.getTags().orElse(occasionToEdit.getTags());

        return new Occasion(updatedOccasionName, updatedOccasionDate, updatedOccasionLocation,
                updatedTags, TypeUtil.OCCASION);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditOccasionCommand)) {
            return false;
        }

        // state check
        EditOccasionCommand e = (EditOccasionCommand) other;
        return index.equals(e.index)
                && editOccasionDescriptor.equals(e.editOccasionDescriptor);
    }

    /**
     * Stores the details to edit the occasion with. Each non-empty field value will replace the
     * corresponding field value of the occasion.
     */
    public static class EditOccasionDescriptor {
        private OccasionName occasionName;
        private OccasionDate occasionDate;
        private OccasionLocation occasionLocation;
        private Set<Tag> tags;

        public EditOccasionDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditOccasionDescriptor(EditOccasionCommand.EditOccasionDescriptor toCopy) {
            setOccasionName(toCopy.occasionName);
            setOccasionDate(toCopy.occasionDate);
            setOccasionLocation(toCopy.occasionLocation);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(occasionName, occasionDate, occasionLocation, tags);
        }

        public void setOccasionName(OccasionName occasionName) {
            this.occasionName = occasionName;
        }

        public Optional<OccasionName> getOccasionName() {
            return Optional.ofNullable(occasionName);
        }

        public void setOccasionDate(OccasionDate occasionDate) {
            this.occasionDate = occasionDate;
        }

        public Optional<OccasionDate> getOccasionDate() {
            return Optional.ofNullable(occasionDate);
        }

        public void setOccasionLocation(OccasionLocation occasionLocation) {
            this.occasionLocation = occasionLocation;
        }

        public Optional<OccasionLocation> getOccasionLocation() {
            return Optional.ofNullable(occasionLocation);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditOccasionCommand.EditOccasionDescriptor)) {
                return false;
            }

            // state check
            EditOccasionCommand.EditOccasionDescriptor e = (EditOccasionCommand.EditOccasionDescriptor) other;

            return getOccasionName().equals(e.getOccasionName())
                    && getOccasionDate().equals(e.getOccasionDate())
                    && getOccasionLocation().equals(e.getOccasionLocation())
                    && getTags().equals(e.getTags());
        }
    }


}
