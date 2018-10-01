package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARPARK;

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
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.carpark.Address;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing carpark in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

//    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the carpark identified "
//            + "by the index number used in the displayed carpark list. "
//            + "Existing values will be overwritten by the input values.\n"
//            + "Parameters: INDEX (must be a positive integer) "
//            + "[" + PREFIX_NAME + "NAME] "
//            + "[" + PREFIX_PHONE + "PHONE] "
//            + "[" + PREFIX_EMAIL + "EMAIL] "
//            + "[" + PREFIX_ADDRESS + "ADDRESS] "
//            + "[" + PREFIX_TAG + "TAG]...\n"
//            + "Example: " + COMMAND_WORD + " 1 "
//            + PREFIX_PHONE + "91234567 "
//            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_USAGE = "We are not doing edits. Due for deletion.";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This carpark already exists in the address book.";

    private final Index index;
    private final EditCarparkDescriptor editCarparkDescriptor;

    /**
     * @param index of the carpark in the filtered carpark list to edit
     * @param editCarparkDescriptor details to edit the carpark with
     */
    public EditCommand(Index index, EditCarparkDescriptor editCarparkDescriptor) {
        requireNonNull(index);
        requireNonNull(editCarparkDescriptor);

        this.index = index;
        this.editCarparkDescriptor = new EditCarparkDescriptor(editCarparkDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Carpark> lastShownList = model.getFilteredCarparkList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Carpark carparkToEdit = lastShownList.get(index.getZeroBased());
        Carpark editedCarpark = createEditedCarpark(carparkToEdit, editCarparkDescriptor);

        if (!carparkToEdit.isSameCarpark(editedCarpark ) && model.hasCarpark(editedCarpark)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updateCarpark(carparkToEdit, editedCarpark );
        model.updateFilteredCarparkList(PREDICATE_SHOW_ALL_CARPARK);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedCarpark ));
    }

    /**
     * Creates and returns a {@code Carpark} with the details of {@code carparkToEdit}
     * edited with {@code editCarparkDescriptor}.
     */
    private static Carpark createEditedCarpark(Carpark carparkToEdit,
                                               EditCarparkDescriptor editCarparkDescriptor) {
        assert carparkToEdit != null;

        CarparkNumber updatedCarparkNumber = editCarparkDescriptor.getCarparkNumber()
                .orElse(carparkToEdit.getCarparkNumber());
        TotalLots updatedTotalLots = editCarparkDescriptor.getTotalLots()
                .orElse(carparkToEdit.getTotalLots());
        LotsAvailable updatedLotsAvailable = editCarparkDescriptor.getLotsAvailable()
                .orElse(carparkToEdit.getLotsAvailable());
        Address updatedAddress = editCarparkDescriptor.getAddress()
                .orElse(carparkToEdit.getAddress());
        Set<Tag> updatedTags = editCarparkDescriptor.getTags()
                .orElse(carparkToEdit.getTags());

        return new Carpark(updatedCarparkNumber, updatedTotalLots, updatedLotsAvailable,
                updatedAddress, updatedTags);
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
                && editCarparkDescriptor.equals(e.editCarparkDescriptor);
    }

    /**
     * Stores the details to edit the carpark with. Each non-empty field value will replace the
     * corresponding field value of the carpark.
     */
    public static class EditCarparkDescriptor {
        private CarparkNumber carparkNumber;
        private TotalLots totalLots;
        private LotsAvailable lotsAvailable;
        private Address address;
        private Set<Tag> tags;

        public EditCarparkDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCarparkDescriptor(EditCarparkDescriptor toCopy) {
            setCarparkNumber(toCopy.carparkNumber);
            setLotsAvailable(toCopy.lotsAvailable);
            setTotalLots(toCopy.totalLots);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(carparkNumber, lotsAvailable, totalLots, address, tags);
        }

        public void setCarparkNumber(CarparkNumber carparkNumber) {
            this.carparkNumber = carparkNumber;
        }

        public Optional<CarparkNumber> getCarparkNumber() {
            return Optional.of(carparkNumber);
        }

        public void setTotalLots(TotalLots totalLots) {
            this.totalLots = totalLots;
        }

        public Optional<TotalLots> getTotalLots() {
            return Optional.of(totalLots);
        }

        public void setLotsAvailable(LotsAvailable lotsAvailable) {
            this.lotsAvailable = lotsAvailable;
        }

        public Optional<LotsAvailable> getLotsAvailable() {
            return Optional.of(lotsAvailable);
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
            if (!(other instanceof EditCarparkDescriptor)) {
                return false;
            }

            // state check
            EditCarparkDescriptor e = (EditCarparkDescriptor) other;

            return getCarparkNumber().equals(e.getCarparkNumber())
                    && getLotsAvailable().equals(e.getLotsAvailable())
                    && getTotalLots().equals(e.getTotalLots())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
