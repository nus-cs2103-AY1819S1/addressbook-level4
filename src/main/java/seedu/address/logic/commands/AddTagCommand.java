package seedu.address.logic.commands;

/*


public class AddTagCommand extends Command {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the scheduler. "
            + "Parameters: "
            + PREFIX_TAG + "TAG"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TAG + "exam ";

    public static final String MESSAGE_SUCCESS = "New tag added to scheduler: %1$s";

    private final List<Tag> toAdd = new ArrayList<>();

    public AddTagCommand(Tag tag) {
        requireNonNull(tag);
        //toAdd.addAll(Tag.generateTag(tag));
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        for (Tag t :toAdd) {
            model.addTag(t);
        }
        model.commitTagBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTagCommand // instanceof handles nulls
                && toAdd.equals(((AddTagCommand) other).toAdd));
    }
}
*/