package seedu.restaurant.testutil.account;

import seedu.restaurant.logic.commands.account.ChangePasswordCommand.EditAccountDescriptor;
import seedu.restaurant.model.account.Account;
import seedu.restaurant.model.account.Password;

//@@author AZhiKai
/**
 * A utility class to help with building {@code EditAccountDescriptor} objects.
 */
public class EditAccountDescriptorBuilder {

    private EditAccountDescriptor descriptor;

    public EditAccountDescriptorBuilder() {
        descriptor = new EditAccountDescriptor();
    }

    public EditAccountDescriptorBuilder(EditAccountDescriptor descriptor) {
        this.descriptor = new EditAccountDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAccountDescriptor} with fields containing {@code account}'s details
     */
    public EditAccountDescriptorBuilder(Account account) {
        descriptor = new EditAccountDescriptor();
        descriptor.setPassword(account.getPassword());
    }

    /**
     * Sets the {@code Password} of the {@code EditAccountDescriptor} that we are building.
     */
    public EditAccountDescriptorBuilder withPassword(String password) {
        descriptor.setPassword(new Password(password));
        return this;
    }

    public EditAccountDescriptor build() {
        return descriptor;
    }
}
