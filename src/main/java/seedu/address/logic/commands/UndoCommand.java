package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.DeselectRequestEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ContactType;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Reverts the {@code model}'s address book to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoAddressBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        List<Contact> listBeforeUndo = new ArrayList<>(model.getAddressBook().getContactList());
        model.undoAddressBook();
        List<Contact> listAfterUndo = model.getAddressBook().getContactList();

        ContactType toFilter = CollectionUtil.compareListOfContacts(listAfterUndo, listBeforeUndo);

        model.updateFilteredContactList(toFilter.getFilter());
        EventsCenter.getInstance().post(new DeselectRequestEvent());

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
