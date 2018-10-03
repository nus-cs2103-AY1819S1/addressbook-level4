package seedu.address.logic.Anakin_commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Anakin_Model.PREDICATE_SHOW_ALL_DECKS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import seedu.address.commons.core.AnakinMessages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Anakin_deck.Name;
import seedu.address.model.Anakin_Model;
import seedu.address.model.Anakin_deck.Anakin_Card;
import seedu.address.model.Anakin_deck.Anakin_Deck;

/**
 * Edits the details of an existing deck in the address book.
 */
public class AnakinEditDeckCommand extends Anakin_Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the deck identified "
            + "by the index number used in the displayed deck list. "
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
    public AnakinEditDeckCommand(Index index, EditDeckDescriptor editDeckDescriptor) {
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
            throw new CommandException(AnakinMessages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        Anakin_Deck deckToEdit = lastShownList.get(index.getZeroBased());
        Anakin_Deck editedDeck = createEditedDeck(deckToEdit, editDeckDescriptor);

        if (!deckToEdit.isSameDeck(editedDeck) && anakinModel.hasDeck(editedDeck)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        anakinModel.updateDeck(deckToEdit, editedDeck);
        anakinModel.updateFilteredDeckList(PREDICATE_SHOW_ALL_DECKS);
        anakinModel.commitAnakin();
        return new CommandResult(String.format(MESSAGE_EDIT_DECK_SUCCESS, editedDeck));
    }

    /**
     * Creates and returns a {@code Deck} with the details of {@code deckToEdit}
     * edited with {@code editDeckDescriptor}.
     */
    private static Anakin_Deck createEditedDeck(Anakin_Deck deckToEdit, EditDeckDescriptor editDeckDescriptor) {
        assert deckToEdit != null;

        Name updatedName = editDeckDescriptor.getName().orElse(deckToEdit.getName());
        //List<Anakin_Card> updatedList = editDeckDescriptor.getCards().orElse(deckToEdit.getCards());

        return new Anakin_Deck(updatedName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AnakinEditDeckCommand)) {
            return false;
        }

        // state check
        AnakinEditDeckCommand e = (AnakinEditDeckCommand) other;
        return index.equals(e.index)
                && editDeckDescriptor.equals(e.editDeckDescriptor);
    }

    /**
     * Stores the details to edit the deck with. Each non-empty field value will replace the
     * corresponding field value of the deck.
     */
    public static class EditDeckDescriptor {
        private Name name;
        private List<Anakin_Card> cards;

        public EditDeckDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditDeckDescriptor(EditDeckDescriptor toCopy) {
            setName(toCopy.name);
            setCards(toCopy.cards);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, cards);
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
        public void setCards(List<Anakin_Card> cards) {
            this.cards = (cards != null) ? new ArrayList<>(cards) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<List<Anakin_Card>> getCards() {
            return (cards != null) ? Optional.of(Collections.unmodifiableList(cards)) : Optional.empty();
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
                    && getCards().equals(e.getCards());
        }
    }
}
