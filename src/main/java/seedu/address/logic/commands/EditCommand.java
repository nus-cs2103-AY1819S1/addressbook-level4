package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
<<<<<<< HEAD
=======
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
>>>>>>> master
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
<<<<<<< HEAD
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.CarparkType;
import seedu.address.model.carpark.Coordinate;
import seedu.address.model.carpark.FreeParking;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.NightParking;
import seedu.address.model.carpark.ShortTerm;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.carpark.TypeOfParking;
=======
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.LotType;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.person.Address;
>>>>>>> master
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

<<<<<<< HEAD
    public static final String MESSAGE_USAGE = "We are not doing edits. Due for deletion.";

    public static final String MESSAGE_EDIT_CARPARK_SUCCESS = "Edited Carpark: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CARPARK = "This carpark already exists in the address book.";
=======
    public static final String MESSAGE_USAGE = "I AM TOO BUSY TO MAKE THESE EDITS";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This carpark already exists in the address book.";
>>>>>>> master

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
            throw new CommandException(Messages.MESSAGE_INVALID_CARPARK_DISPLAYED_INDEX);
        }

        Carpark carparkToEdit = lastShownList.get(index.getZeroBased());
        Carpark editedCarpark = createEditedCarpark(carparkToEdit, editCarparkDescriptor);

        if (!carparkToEdit.isSameCarpark(editedCarpark ) && model.hasCarpark(editedCarpark)) {
<<<<<<< HEAD
            throw new CommandException(MESSAGE_DUPLICATE_CARPARK);
=======
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
>>>>>>> master
        }

        model.updateCarpark(carparkToEdit, editedCarpark );
        model.updateFilteredCarparkList(PREDICATE_SHOW_ALL_CARPARK);
        model.commitAddressBook();
<<<<<<< HEAD
        return new CommandResult(String.format(MESSAGE_EDIT_CARPARK_SUCCESS, editedCarpark));
    }

    /**
     * Creates and returns a {@code Carpark} with the details of {@code carparkToEdit}
     * edited with {@code editCarparkDescriptor}.
     */
    private static Carpark createEditedCarpark(Carpark carparkToEdit,
                                               EditCarparkDescriptor editCarparkDescriptor) {
        assert carparkToEdit != null;

        Address updatedAddress = editCarparkDescriptor.getAddress()
                .orElse(carparkToEdit.getAddress());
        CarparkNumber updatedCarparkNumber = editCarparkDescriptor.getCarparkNumber()
                .orElse(carparkToEdit.getCarparkNumber());
        CarparkType updatedCarparkType = editCarparkDescriptor.getCarparkType()
                .orElse(carparkToEdit.getCarparkType());
        Coordinate updatedCoordinate = editCarparkDescriptor.getCoordinate()
                .orElse(carparkToEdit.getCoordinate());
        FreeParking updatedFreeParking = editCarparkDescriptor.getFreeParking()
                .orElse(carparkToEdit.getFreeParking());
        LotsAvailable updatedLotsAvailable = editCarparkDescriptor.getLotsAvailable()
                .orElse(carparkToEdit.getLotsAvailable());
        NightParking updatedNightParking = editCarparkDescriptor.getNightParking()
                .orElse(carparkToEdit.getNightParking());
        ShortTerm updatedShortTerm = editCarparkDescriptor.getShortTerm()
                .orElse(carparkToEdit.getShortTerm());
        TotalLots updatedTotalLots = editCarparkDescriptor.getTotalLots()
                .orElse(carparkToEdit.getTotalLots());
        TypeOfParking updatedTypeOfParking = editCarparkDescriptor.getTypeOfParking()
                .orElse(carparkToEdit.getTypeOfParking());

        Set<Tag> updatedTags = editCarparkDescriptor.getTags()
                .orElse(carparkToEdit.getTags());

        return new Carpark(updatedAddress, updatedCarparkNumber, updatedCarparkType, updatedCoordinate,
                updatedFreeParking, updatedLotsAvailable, updatedNightParking, updatedShortTerm,
                updatedTotalLots, updatedTypeOfParking, updatedTags);
=======
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedCarpark ));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editCarparkDescriptor}.
     */
    private static Carpark createEditedCarpark(Carpark carparkToEdit, EditCarparkDescriptor editCarparkDescriptor) {
        assert carparkToEdit != null;

        CarparkNumber updatedCarparkNumber = editCarparkDescriptor.getCarparkNumber().orElse(carparkToEdit.getCarparkNumber());
        TotalLots updatedTotalLots = editCarparkDescriptor.getTotalLots().orElse(carparkToEdit.getTotalLots());
        LotsAvailable updatedLotsAvailable = editCarparkDescriptor.getLotsAvailable().orElse(carparkToEdit.getLotsAvailable());
        LotType updatedLotType = editCarparkDescriptor.getLotType().orElse(carparkToEdit.getLotType());
        Address updatedAddress = editCarparkDescriptor.getAddress().orElse(carparkToEdit.getAddress());
        Set<Tag> updatedTags = editCarparkDescriptor.getTags().orElse(carparkToEdit.getTags());

//        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
        return new Carpark(updatedCarparkNumber, updatedTotalLots, updatedLotsAvailable, updatedLotType, updatedAddress, updatedTags);
>>>>>>> master
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
<<<<<<< HEAD
=======
        public Optional<CarparkNumber> getCarparkNumber() {
            return Optional.of(carparkNumber);
        }

        public Optional<TotalLots> getTotalLots() {
            return Optional.of(totalLots);
        }

        public Optional<LotsAvailable> getLotsAvailable() {
            return Optional.of(lotsAvailable);
        }

        public Optional<LotType> getLotType() {
            return Optional.of(lotType);
        }

        private CarparkNumber carparkNumber;
        private TotalLots totalLots;

        public void setCarparkNumber(CarparkNumber carparkNumber) {
            this.carparkNumber = carparkNumber;
        }

        public void setTotalLots(TotalLots totalLots) {
            this.totalLots = totalLots;
        }

        public void setLotsAvailable(LotsAvailable lotsAvailable) {
            this.lotsAvailable = lotsAvailable;
        }

        public void setLotType(LotType lotType) {
            this.lotType = lotType;
        }

        private LotsAvailable lotsAvailable;
        private LotType lotType;
>>>>>>> master
        private Address address;
        private CarparkNumber carparkNumber;
        private CarparkType carparkType;
        private Coordinate coordinate;
        private FreeParking freeParking;
        private LotsAvailable lotsAvailable;
        private NightParking nightParking;
        private ShortTerm shortTerm;
        private TotalLots totalLots;
        private TypeOfParking typeOfParking;
        private Set<Tag> tags;

        public EditCarparkDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCarparkDescriptor(EditCarparkDescriptor toCopy) {
<<<<<<< HEAD
=======
//            setName(toCopy.name);
//            setPhone(toCopy.phone);
//            setEmail(toCopy.email);
            setCarparkNumber(toCopy.carparkNumber);
            setLotsAvailable(toCopy.lotsAvailable);
            setTotalLots(toCopy.totalLots);
            setLotType(toCopy.lotType);
>>>>>>> master
            setAddress(toCopy.address);
            setCarparkNumber(toCopy.carparkNumber);
            setCarparkType(toCopy.carparkType);
            setCoordinate(toCopy.coordinate);
            setFreeParking(toCopy.freeParking);
            setLotsAvailable(toCopy.lotsAvailable);
            setNightParking(toCopy.nightParking);
            setShortTerm(toCopy.shortTerm);
            setTotalLots(toCopy.totalLots);
            setTypeOfParking(toCopy.typeOfParking);

            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
<<<<<<< HEAD
            return CollectionUtil.isAnyNonNull(address, carparkNumber, carparkType, coordinate, freeParking,
                    lotsAvailable, nightParking, shortTerm, totalLots, typeOfParking, tags);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setCarparkNumber(CarparkNumber carparkNumber) {
            this.carparkNumber = carparkNumber;
        }

        public Optional<CarparkNumber> getCarparkNumber() {
            return Optional.ofNullable(carparkNumber);
        }

        public void setCarparkType(CarparkType carparkType) {
            this.carparkType = carparkType;
        }

        public Optional<CarparkType> getCarparkType() {
            return Optional.ofNullable(carparkType);
        }
=======
            return CollectionUtil.isAnyNonNull(carparkNumber, lotsAvailable, totalLots, lotType, address, tags);
        }

//        public void setName(Name name) {
//            this.name = name;
//        }
//
//        public Optional<Name> getName() {
//            return Optional.ofNullable(name);
//        }
//
//        public void setPhone(Phone phone) {
//            this.phone = phone;
//        }
//
//        public Optional<Phone> getPhone() {
//            return Optional.ofNullable(phone);
//        }
//
//        public void setEmail(Email email) {
//            this.email = email;
//        }
//
//        public Optional<Email> getEmail() {
//            return Optional.ofNullable(email);
//        }
>>>>>>> master

        public void setCoordinate(Coordinate carparkType) {
            this.coordinate = coordinate;
        }

        public Optional<Coordinate> getCoordinate() {
            return Optional.ofNullable(coordinate);
        }

        public void setFreeParking(FreeParking freeParking) {
            this.freeParking = freeParking;
        }

        public Optional<FreeParking> getFreeParking() {
            return Optional.ofNullable(freeParking);
        }

        public void setLotsAvailable(LotsAvailable lotsAvailable) {
            this.lotsAvailable = lotsAvailable;
        }

        public Optional<LotsAvailable> getLotsAvailable() {
            return Optional.ofNullable(lotsAvailable);
        }

        public void setNightParking(NightParking nightParking) {
            this.nightParking = nightParking;
        }

        public Optional<NightParking> getNightParking() {
            return Optional.ofNullable(nightParking);
        }

        public void setShortTerm(ShortTerm shortTerm) {
            this.shortTerm = shortTerm;
        }

        public Optional<ShortTerm> getShortTerm() {
            return Optional.ofNullable(shortTerm);
        }

        public void setTotalLots(TotalLots totalLots) {
            this.totalLots = totalLots;
        }

        public Optional<TotalLots> getTotalLots() {
            return Optional.of(totalLots);
        }

        public void setTypeOfParking(TypeOfParking typeOfParking) {
            this.typeOfParking = typeOfParking;
        }

        public Optional<TypeOfParking> getTypeOfParking() {
            return Optional.of(typeOfParking);
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

<<<<<<< HEAD
            return getAddress().equals(e.getAddress())
                    && getCarparkNumber().equals(e.getCarparkNumber())
                    && getCarparkType().equals(e.getCarparkType())
                    && getCoordinate().equals(e.getCoordinate())
                    && getFreeParking().equals(e.getFreeParking())
                    && getLotsAvailable().equals(e.getLotsAvailable())
                    && getNightParking().equals(e.getNightParking())
                    && getShortTerm().equals(e.getShortTerm())
                    && getTotalLots().equals(e.getTotalLots())
                    && getTypeOfParking().equals(e.getTypeOfParking())
=======
            return getCarparkNumber().equals(e.getCarparkNumber())
                    && getLotsAvailable().equals(e.getLotsAvailable())
                    && getLotType().equals(e.getLotType())
                    && getTotalLots().equals(e.getTotalLots())
                    && getAddress().equals(e.getAddress())
>>>>>>> master
                    && getTags().equals(e.getTags());
        }
    }
}
