package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_REMARK;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventId;
import seedu.address.model.record.Hour;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordContainsEventIdPredicate;
import seedu.address.model.record.Remark;
import seedu.address.model.volunteer.VolunteerId;

/**
 * Edits the details of an existing record in the application.
 */
public class EditRecordCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the record identified "
            + "by the index number used in the displayed record list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: VOLUNTEER_INDEX (must be a positive integer) "
            + "[" + PREFIX_RECORD_HOUR + "HOURS] "
            + "[" + PREFIX_RECORD_REMARK + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RECORD_HOUR + "2 "
            + PREFIX_RECORD_REMARK + "Driver";

    public static final String MESSAGE_EDIT_RECORD_SUCCESS = "Edited Record: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RECORD = "This record already exists in the application.";

    private final Index index;
    private final EditRecordDescriptor editRecordDescriptor;

    /**
     * @param index                of the person in the filtered person list to edit
     * @param editRecordDescriptor details to edit the person with
     */
    public EditRecordCommand(Index index, EditRecordDescriptor editRecordDescriptor) {
        requireNonNull(index);
        requireNonNull(editRecordDescriptor);

        this.index = index;
        this.editRecordDescriptor = new EditRecordDescriptor(editRecordDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Record> lastShownList = model.getFilteredRecordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
        }

        Record recordToEdit = lastShownList.get(index.getZeroBased());
        Record editedRecord = createEditedRecord(recordToEdit, editRecordDescriptor);

        if (!recordToEdit.isSameRecord(editedRecord) && model.hasRecord(editedRecord)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.updateRecord(recordToEdit, editedRecord);
        model.updateFilteredRecordList(new RecordContainsEventIdPredicate(model.getSelectedEvent().getEventId()));
        model.commitAddressBook();

        EventsCenter.getInstance().post(new RecordChangeEvent(model.getSelectedEvent()));
        return new CommandResult(String.format(MESSAGE_EDIT_RECORD_SUCCESS, editedRecord));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Record createEditedRecord(Record recordToEdit, EditRecordDescriptor editRecordDescriptor) {
        assert recordToEdit != null;

        VolunteerId volunteerId = recordToEdit.getVolunteerId();
        EventId eventId = recordToEdit.getEventId();
        Hour updatedHour = editRecordDescriptor.getHour().orElse(recordToEdit.getHour());
        Remark updatedRemark = editRecordDescriptor.getRemark().orElse(recordToEdit.getRemark());

        return new Record(eventId, volunteerId, updatedHour, updatedRemark);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditRecordCommand)) {
            return false;
        }

        // state check
        EditRecordCommand e = (EditRecordCommand) other;
        return index.equals(e.index)
                && editRecordDescriptor.equals(e.editRecordDescriptor);
    }

    /**
     * Stores the details to edit the record with. Each non-empty field value will replace the
     * corresponding field value of the record.
     */
    public static class EditRecordDescriptor {
        private EventId eventId;
        private VolunteerId volunteerId;
        private Hour hour;
        private Remark remark;

        private int localIndex;
        private String volunteerName;
        private String phoneNo;

        public EditRecordDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditRecordDescriptor(EditRecordDescriptor toCopy) {
            setEventId(toCopy.eventId);
            setVolunteerId(toCopy.volunteerId);
            setHour(toCopy.hour);
            setRemark(toCopy.remark);
            setLocalIndex(toCopy.localIndex);
            setVolunteerName(toCopy.volunteerName);
            setPhoneNo(toCopy.phoneNo);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(hour, remark);
        }

        public void setEventId(EventId eventId) {
            this.eventId = eventId;
        }

        public void setVolunteerId(VolunteerId volunteerId) {
            this.volunteerId = volunteerId;
        }

        public void setHour(Hour hour) {
            this.hour = hour;
        }

        public Optional<Hour> getHour() {
            return Optional.ofNullable(hour);
        }

        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setLocalIndex(int localIndex) {
            this.localIndex = localIndex;
        }

        public void setVolunteerName(String volunteerName) {
            this.volunteerName = volunteerName;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRecordDescriptor)) {
                return false;
            }

            // state check
            EditRecordDescriptor e = (EditRecordDescriptor) other;

            return getHour().equals(e.getHour())
                    && getRemark().equals(e.getRemark());
        }
    }
}
