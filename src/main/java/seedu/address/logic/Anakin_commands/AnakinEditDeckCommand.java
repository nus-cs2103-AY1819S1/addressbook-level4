package seedu.address.logic.Anakin_commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.Anakin_Model;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Edits the details of an existing deck in the address book.
 */
public class AnakinEditDeckCommand extends Anakin_Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deck identified "
            + "by the index number used in the displayed person list. "
            + "Changes its name to NAME.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME;

    public static final String MESSAGE_EDIT_DECK_SUCCESS = "Edited Deck: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This deck already exists in the address book.";

    private final Index index;
    private final EditDeckDescriptor editDeckDescriptor;

    /**
     * @param index of the deck in the filtered deck list to edit
     * @param editDeckDescriptor details to edit the deck with
     */
    public EditCommand(Index index, EditDeckDescriptor editDeckDescriptor) {
        requireNonNull(index);
        requireNonNull(editDeckDescriptor);

        this.index = index;
        this.editDeckDescriptor = new EditDeckDescriptor(editDeckDescriptor);
    }

    @Override
    public CommandResult execute(Anakin_Model anakinModel, CommandHistory history) throws CommandException {
        requireNonNull(anakinModel);
        List<Anakin_Deck> lastShownList = anakinModel.getFilteredDeckList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Anakin_Deck deckToEdit = lastShownList.get(index.getZeroBased());
        Anakin_Deck editedDeck = createEditedDeck(deckToEdit, editDeckDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && anakinModel.hasDeck(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        anakinModel.updateDeck(deckToEdit, editedDeck);
        anakinModel.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECK);
        anakinModel.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck));
    }

    /**
     * Creates and returns a {@code Deck} with the details of {@code deckToEdit}
     * edited with {@code editDeckDescriptor}.
     */
    private static Anakin_Deck createEditedDeck(Anakin_Deck deckToEdit, EditDeckDescriptor editDeckDescriptor) {
        assert deckToEdit != null;

        Name updatedName = editDeckDescriptor.getName().orElse(deckToEdit.getName());
        Set<Tag> updatedTags = editDeckDescriptor.getTags().orElse(deckToEdit.getTags());

        return new Anakin_Deck(updatedName, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editDeckDescriptor.equals(e.editDeckDescriptor);
    }

    /**
     * Stores the details to edit the deck with. Each non-empty field value will replace the
     * corresponding field value of the deck.
     */
    public static class EditDeckDescriptor {
        private Name name;
        private Set<Tag> tags;

        public EditDeckDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeckDescriptor(EditDeckDescriptor toCopy) {
            setName(toCopy.name);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
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
            if (!(other instanceof EditDeckDescriptor)) {
                return false;
            }

            // state check
            EditDeckDescriptor e = (EditDeckDescriptor) other;

            return getName().equals(e.getName())
                    && getTags().equals(e.getTags());
        }
    }
}
