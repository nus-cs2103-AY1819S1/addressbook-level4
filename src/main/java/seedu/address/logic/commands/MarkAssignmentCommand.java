package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.common.Mark;
import seedu.address.model.person.Person;

/**
 * Mark a person grade for an assignment in the address book.
 */
public class MarkAssignmentCommand extends Command {

    public static final String COMMAND_WORD = "markAssignment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Input assignment mark for person whose index specified for assignment whose index specified\n"
            + "Parameters: INDEX " + PREFIX_ID + "ASSIGNMENT_INDEX " + PREFIX_MARK + "MARK\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ID + "1 " + PREFIX_MARK + "53";

    public static final String MESSAGE_MARK_PERSON_SUCCESS = "Marked Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Index assignmentIndex;
    private final Mark assignmentMark;

    /**
     * @param index of the person in the filtered person list to edit
     * @param assignmentIndex of the assignment shown
     * @param assignmentMark of the assignment the person get
     */
    public MarkAssignmentCommand(Index index, Index assignmentIndex, Mark assignmentMark) {
        requireNonNull(index);
        requireNonNull(assignmentIndex);
        requireNonNull(assignmentMark);

        this.index = index;
        this.assignmentIndex = assignmentIndex;
        this.assignmentMark = assignmentMark;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToMarkAssignment = lastShownList.get(index.getZeroBased());

        List<Assignment> lastShownAssignmentList = model.getFilteredAssignmentList();
        if (assignmentIndex.getZeroBased() >= lastShownAssignmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }
        String assignmentUid = lastShownAssignmentList.get(assignmentIndex.getZeroBased()).getUniqueId();

        EditPersonDescriptor descriptor = new EditPersonDescriptor();
        Map<String, Mark> updatedMarks = new HashMap<>(personToMarkAssignment.getMarks());
        updatedMarks.put(assignmentUid, assignmentMark);
        descriptor.setMarks(updatedMarks);

        Person markedPerson = descriptor.createEditedPerson(personToMarkAssignment);

        if (!personToMarkAssignment.isSamePerson(markedPerson) && model.hasPerson(markedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.updatePerson(personToMarkAssignment, markedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_MARK_PERSON_SUCCESS, markedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAssignmentCommand)) {
            return false;
        }

        // state check
        MarkAssignmentCommand e = (MarkAssignmentCommand) other;
        return index.equals(e.index)
                && assignmentIndex.equals(e.assignmentIndex)
                && assignmentMark.equals(assignmentMark);
    }
}
