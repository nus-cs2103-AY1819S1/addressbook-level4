package seedu.superta.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP_NAME;

import java.util.Optional;

import seedu.superta.commons.util.CollectionUtil;
import seedu.superta.logic.CommandHistory;
import seedu.superta.logic.commands.exceptions.CommandException;
import seedu.superta.model.Model;
import seedu.superta.model.tutorialgroup.TutorialGroup;

/**
 * Updates the details of an existing tutorial group in the SuperTA client.
 */
public class UpdateTutorialGroupCommand extends Command {
    public static final String COMMAND_WORD = "update-tutorial-group";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates a tutorial group.\n"
        + "Parameters: "
        + PREFIX_TUTORIAL_GROUP_ID + "TUTORIAL-GROUP-ID "
        + PREFIX_TUTORIAL_GROUP_NAME + "TUTORIAL-GROUP-NAME\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_TUTORIAL_GROUP_ID + "04a "
        + PREFIX_TUTORIAL_GROUP_NAME + "CS2100 Tutorial Group 04A";

    public static final String MESSAGE_SUCCESS = "Tutorial group updated.";
    public static final String MESSAGE_NO_SUCH_TUTORIAL_GROUP = "No tutorial group with the ID $1s exists.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final String id;
    private final UpdateTutorialGroupDescriptor descriptor;

    public UpdateTutorialGroupCommand(String id, UpdateTutorialGroupDescriptor descriptor) {
        requireNonNull(id);
        requireNonNull(descriptor);

        this.id = id;
        this.descriptor = descriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.hasTutorialGroup(id)) {
            throw new CommandException(String.format(MESSAGE_NO_SUCH_TUTORIAL_GROUP, id));
        }

        TutorialGroup original = model.getTutorialGroup(id).get();
        model.updateTutorialGroup(createEditedTutorialGroup(original));
        model.commitSuperTaClient();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Creates an edited tutorial group with the given descriptor supplied in the constructor.
     */
    private TutorialGroup createEditedTutorialGroup(TutorialGroup toEdit) {
        requireNonNull(toEdit);

        String updatedName = descriptor.getName().orElse(toEdit.getName());
        return new TutorialGroup(id, updatedName, toEdit.getStudents(), toEdit.getAssignments(),
                                 toEdit.getSessions());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UpdateTutorialGroupCommand)) {
            return false;
        }
        UpdateTutorialGroupCommand o = (UpdateTutorialGroupCommand) other;
        return id.equals(o.id) && descriptor.equals(o.descriptor);

    }

    /**
     * Stores the details to edit the tutorial group with. Eeach non-empty field value will replace the corresponding
     * field value of the tutorial group.
     */
    public static class UpdateTutorialGroupDescriptor implements Descriptor {
        private String name;

        public UpdateTutorialGroupDescriptor() { }

        @Override
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name);
        }

        public void setName(String name) {
            this.name = name;
        }

        public Optional<String> getName() {
            return Optional.ofNullable(name);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            UpdateTutorialGroupDescriptor u = (UpdateTutorialGroupDescriptor) other;
            return getName().equals(u.getName());
        }
    }
}
