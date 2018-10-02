package seedu.address.logic.Anakin_commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
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

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Deck: %1$s";
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
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Person deckToEdit = lastShownList.get(index.getZeroBased());
        Person editedDeck = createEditedDeck(deckToEdit, editDeckDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && model.hasDeck(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updateDeck(personToEdit, editedDeck);
        model.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECK);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck));
    }

    /**
     * Creates and returns a {@code Deck} with the details of {@code deckToEdit}
     * edited with {@code editDeckDescriptor}.
     */
    private static Deck createEditedDeck(Deck personToEdit, EditDeckDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editDeckDescriptor.getName().orElse(deckToEdit.getName());
        Phone updatedPhone = editDeckDescriptor.getPhone().orElse(deckToEdit.getPhone());
        Email updatedEmail = editDeckDescriptor.getEmail().orElse(deckToEdit.getEmail());
        Address updatedAddress = editDeckDescriptor.getAddress().orElse(deckToEdit.getAddress());
        Set<Tag> updatedTags = editDeckDescriptor.getTags().orElse(deckToEdit.getTags());

        return new Deck(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditDeckDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeckDescriptor(EditDeckDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
