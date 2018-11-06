package seedu.restaurant.logic.commands.reservation;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_DATE;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NAME;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_PAX;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TIME;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_RESERVATIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.commons.util.CollectionUtil;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Remark;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.model.tag.Tag;

/**
 * Edits the details of an existing reservation in the menu.
 */
public class EditReservationCommand extends Command {

    public static final String COMMAND_WORD = "edit-reservation";

    public static final String COMMAND_ALIAS = "er";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the reservation identified "
            + "by the index number used in the displayed reservation list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PAX + "PAX] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PAX + "4";

    public static final String MESSAGE_EDIT_RESERVATION_SUCCESS = "Edited Reservation: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESERVATION = "This reservation already exists.";

    private final Index index;
    private final EditReservationDescriptor editReservationDescriptor;

    /**
     * @param index of the reservation in the filtered reservation list to edit
     * @param editReservationDescriptor details to edit the reservation with
     */
    public EditReservationCommand(Index index, EditReservationDescriptor editReservationDescriptor) {
        requireNonNull(index);
        requireNonNull(editReservationDescriptor);

        this.index = index;
        this.editReservationDescriptor = new EditReservationDescriptor(editReservationDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Reservation> lastShownList = model.getFilteredReservationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESERVATION_DISPLAYED_INDEX);
        }

        Reservation reservationToEdit = lastShownList.get(index.getZeroBased());
        Reservation editedReservation = createEditedReservation(reservationToEdit, editReservationDescriptor);

        if (!reservationToEdit.isSameReservation(editedReservation) && model.hasReservation(editedReservation)) {
            throw new CommandException(MESSAGE_DUPLICATE_RESERVATION);
        }

        model.updateReservation(reservationToEdit, editedReservation);
        model.updateFilteredReservationList(PREDICATE_SHOW_ALL_RESERVATIONS);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_EDIT_RESERVATION_SUCCESS, editedReservation));
    }

    /**
     * Creates and returns a {@code Reservation} with the details of {@code reservationToEdit}
     * edited with {@code editReservationDescriptor}.
     */
    private static Reservation createEditedReservation(Reservation reservationToEdit,
            EditReservationDescriptor editReservationDescriptor) {
        assert reservationToEdit != null;

        Name updatedName = editReservationDescriptor.getName().orElse(reservationToEdit.getName());
        Pax updatedPax = editReservationDescriptor.getPax().orElse(reservationToEdit.getPax());
        Date updatedDate = editReservationDescriptor.getDate().orElse(reservationToEdit.getDate());
        Time updatedTime = editReservationDescriptor.getTime().orElse(reservationToEdit.getTime());
        Remark updatedRemark = reservationToEdit.getRemark(); // edit command does not allow editing remarks
        Set<Tag> updatedTags = editReservationDescriptor.getTags().orElse(reservationToEdit.getTags());

        return new Reservation(updatedName, updatedPax, updatedDate, updatedTime, updatedRemark, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditReservationCommand)) {
            return false;
        }

        // state check
        EditReservationCommand e = (EditReservationCommand) other;
        return index.equals(e.index)
                && editReservationDescriptor.equals(e.editReservationDescriptor);
    }

    /**
     * Stores the details to edit the reservation with. Each non-empty field value will replace the
     * corresponding field value of the reservation.
     */
    public static class EditReservationDescriptor {
        private Name name;
        private Pax pax;
        private Date date;
        private Time time;
        private Set<Tag> tags;

        public EditReservationDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditReservationDescriptor(EditReservationDescriptor toCopy) {
            setName(toCopy.name);
            setPax(toCopy.pax);
            setDate(toCopy.date);
            setTime(toCopy.time);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, pax, date, time, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPax(Pax pax) {
            this.pax = pax;
        }

        public Optional<Pax> getPax() {
            return Optional.ofNullable(pax);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
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
            if (!(other instanceof EditReservationDescriptor)) {
                return false;
            }

            // state check
            EditReservationDescriptor e = (EditReservationDescriptor) other;

            return getName().equals(e.getName())
                    && getPax().equals(e.getPax())
                    && getDate().equals(e.getDate())
                    && getTime().equals(e.getTime())
                    && getTags().equals(e.getTags());
        }
    }
}
