package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_HOUR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECORD_REMARK;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ContextChangeEvent;
import seedu.address.commons.events.ui.RecordChangeEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.record.Record;
import seedu.address.model.record.RecordContainsEventIdPredicate;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a record to the application.
 */
public class AddRecordCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a volunteer record to the event that "
            + "is currently managed."
            + "Parameters: VOLUNTEER_INDEX (must be a positive integer) "
            + "[" + PREFIX_RECORD_HOUR + "HOURS] "
            + "[" + PREFIX_RECORD_REMARK + "REMARKS]\n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_RECORD_HOUR + "5 "
            + PREFIX_RECORD_REMARK + "Emcee";

    public static final String MESSAGE_SUCCESS = "Record added: %1$s";
    public static final String MESSAGE_DUPLICATE_RECORD = "This volunteer is already registered.";

    public final Index index;
    private final Record toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddRecordCommand(Index index, Record record) {
        requireNonNull(index);
        requireNonNull(record);
        this.index = index;
        this.toAdd = record;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Volunteer volunteerSelected = lastShownList.get(index.getZeroBased());
        Record record = new Record(model.getSelectedEvent().getEventId(), volunteerSelected.getVolunteerId(),
                toAdd.getHour(), toAdd.getRemark());
        record.setVolunteerName(volunteerSelected.getName().fullName);

        if (model.hasRecord(record)) {
            throw new CommandException(MESSAGE_DUPLICATE_RECORD);
        }

        model.addRecord(record);
        model.updateFilteredRecordList(new RecordContainsEventIdPredicate(model.getSelectedEvent().getEventId()));
        model.commitAddressBook();

        // Posting event
        EventsCenter.getInstance().post(new RecordChangeEvent(model.getSelectedEvent()));
        EventsCenter.getInstance().post(new ContextChangeEvent(model.getContextId()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, record));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRecordCommand // instanceof handles nulls
                && toAdd.equals(((AddRecordCommand) other).toAdd));
    }
}
