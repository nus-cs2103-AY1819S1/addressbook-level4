package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Session;
import seedu.address.model.attendance.SessionDate;


/**
     * Edits the details of an existing lesson in the list of lessons.
     */
public class EditLessonCommand extends Command {

    public static final String COMMAND_WORD = "editLesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the session identified "
            + "by the index number used in the displayed attendance list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_ID + "SESSION_INDEX "
            + PREFIX_NAME + "UPDATED_NAME "
            + PREFIX_DATE + "UPDATED_DATE\n "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Tutorial 1 "
            + PREFIX_DATE + "11/11/2018";

    public static final String MESSAGE_EDIT_LESSON_SUCCESS = "Edited Session: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_SESSION = "This session already exists.";

    protected final Index index;
    protected final EditLessonCommand.EditLessonDescriptor editLessonDescriptor;

    /**
     * @param index of the assignment in the filtered assignment list to edit
     * @param editLessonDescriptor details to edit the assignment with
     */
    public EditLessonCommand(Index index, EditLessonDescriptor editLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(editLessonDescriptor);

        this.index = index;
        this.editLessonDescriptor = new EditLessonDescriptor(editLessonDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Attendance> lastShownList = model.getFilteredAttendanceList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_LESSON_DISPLAYED_INDEX);
        }

        Attendance lessonToEdit = lastShownList.get(index.getZeroBased());
        Attendance editedLesson = createEditedLesson(lessonToEdit, editLessonDescriptor);

        if (!lessonToEdit.isSameAttendance(editedLesson) && model.hasAttendance(editedLesson)) {
            throw new CommandException(MESSAGE_DUPLICATE_SESSION);
        }

        model.updateAttendance(lessonToEdit, editedLesson);
        return new CommandResult(String.format(MESSAGE_EDIT_LESSON_SUCCESS, editedLesson));
    }

    /**
     * Creates and returns a {@code Attendance} with the details of {@code lessonToEdit}
     * edited with {@code editLessonDescriptor}.
     */
    protected Attendance createEditedLesson(Attendance lessonToEdit,
                                            EditLessonDescriptor editLessonDescriptor) {
        assert lessonToEdit != null;

        Session updatedName = editLessonDescriptor.getName().orElse(lessonToEdit.getSession());
        SessionDate updatedDate = editLessonDescriptor.getDate().orElse(lessonToEdit.getDate());
        String id = lessonToEdit.getUniqueId();

        return new Attendance(
                updatedName, updatedDate, id
        );
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditLessonCommand)) {
            return false;
        }

        // state check
        EditLessonCommand e = (EditLessonCommand) other;
        return index.equals(e.index)
                && editLessonDescriptor.equals(e.editLessonDescriptor);
    }

    /**
     * Stores the details to edit the assignment with. Each non-empty field value will replace the
     * corresponding field value of the assignment.
     */
    public static class EditLessonDescriptor {
        private Session name;
        private SessionDate date;

        public EditLessonDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditLessonDescriptor(EditLessonDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date);
        }

        public void setName(Session name) {
            this.name = name;
        }

        public Optional<Session> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(SessionDate date) {
            this.date = date;
        }

        public Optional<SessionDate> getDate() {
            return Optional.ofNullable(date);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditLessonDescriptor)) {
                return false;
            }

            // state check
            EditLessonDescriptor e = (EditLessonDescriptor) other;

            return getName().equals(e.getName())
                    && getDate().equals(e.getDate());
        }
    }
}
