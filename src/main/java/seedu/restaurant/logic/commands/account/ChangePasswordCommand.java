package seedu.restaurant.logic.commands.account;

import static java.util.Objects.requireNonNull;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NEW_PASSWORD;
import static seedu.restaurant.model.Model.PREDICATE_SHOW_ALL_ACCOUNTS;

import java.util.Optional;

import seedu.restaurant.commons.core.EventsCenter;
import seedu.restaurant.commons.core.session.UserSession;
import seedu.restaurant.commons.events.storage.UpdateAccountEvent;
import seedu.restaurant.commons.util.CollectionUtil;
import seedu.restaurant.logic.CommandHistory;
import seedu.restaurant.logic.commands.Command;
import seedu.restaurant.logic.commands.CommandResult;
import seedu.restaurant.logic.commands.exceptions.CommandException;
import seedu.restaurant.model.Model;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;

//@@author AZhiKai
/**
 * Change the password of an existing {@code Account}.
 */
public class ChangePasswordCommand extends Command {

    public static final String COMMAND_WORD = "change-password";
    public static final String COMMAND_ALIAS = "cp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Change the password of an existing account. "
            + "Parameters: "
            + PREFIX_NEW_PASSWORD + "NEW_PASSWORD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NEW_PASSWORD + "1122qq";

    public static final String MESSAGE_SUCCESS = "Successfully updated the account %s";

    private final EditAccountDescriptor editAccountDescriptor;

    /**
     * @param editAccountDescriptor details to edit the account with.
     */
    public ChangePasswordCommand(EditAccountDescriptor editAccountDescriptor) {
        requireNonNull(editAccountDescriptor);

        this.editAccountDescriptor = new EditAccountDescriptor(editAccountDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        // Session guarantees to have been set, thus an account exists in the session
        Account accountToEdit = UserSession.getAccount();
        Account editedAccount = createEditedAccount(accountToEdit, editAccountDescriptor);

        model.updateAccount(accountToEdit, editedAccount);
        model.updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        model.commitRestaurantBook();

        EventsCenter.getInstance().post(new UpdateAccountEvent(editedAccount));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedAccount));
    }

    /**
     * Creates and returns a {@code Account} with the details of {@code accountToEdit} edited with {@code
     * editAccountDescriptor}.
     */
    private static Account createEditedAccount(Account accountToEdit, EditAccountDescriptor editAccountDescriptor) {
        assert accountToEdit != null;

        Password updatedPassword = editAccountDescriptor.getPassword().orElse(accountToEdit.getPassword());

        return new Account(accountToEdit.getUsername(), updatedPassword, accountToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ChangePasswordCommand
                    && editAccountDescriptor.equals(((ChangePasswordCommand) other).editAccountDescriptor));
    }

    /**
     * Stores the details to edit the account with. Each non-empty field value will replace the corresponding field
     * value of the account.
     */
    public static class EditAccountDescriptor {

        private Password password;

        public EditAccountDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAccountDescriptor(EditAccountDescriptor toCopy) {
            setPassword(toCopy.password);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(password);
        }

        public void setPassword(Password password) {
            this.password = password;
        }

        public Optional<Password> getPassword() {
            return Optional.ofNullable(password);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAccountDescriptor)) {
                return false;
            }

            // state check
            EditAccountDescriptor e = (EditAccountDescriptor) other;

            return password.equals(e.password);
        }
    }
}
