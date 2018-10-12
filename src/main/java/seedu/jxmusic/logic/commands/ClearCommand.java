//package seedu.jxmusic.logic.commands;
//
//import static java.util.Objects.requireNonNull;
//
//import seedu.jxmusic.model.AddressBook;
//import seedu.jxmusic.model.Model;
//
///**
// * Clears the jxmusic book.
// */
//public class ClearCommand extends Command {
//
//    public static final String COMMAND_WORD = "clear";
//    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
//
//
//    @Override
//    public CommandResult execute(Model model, CommandHistory history) {
//        requireNonNull(model);
//        model.resetData(new AddressBook());
//        model.commitAddressBook();
//        return new CommandResult(MESSAGE_SUCCESS);
//    }
//}
