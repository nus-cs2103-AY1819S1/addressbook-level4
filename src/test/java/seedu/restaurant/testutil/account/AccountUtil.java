package seedu.restaurant.testutil.account;

import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_NEW_PASSWORD;

import seedu.restaurant.logic.commands.account.ChangePasswordCommand.EditAccountDescriptor;

//@@author AZhiKai
/**
 * A utility class for {@code Account}.
 */
public class AccountUtil {

    /**
     * Returns the part of command string for the given {@code EditAccountDescriptor}'s details.
     */
    public static String getEditAccountDescriptorDetails(EditAccountDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        //descriptor.getUsername().ifPresent(username -> sb.append(PREFIX_ID).append(username.toString()).append(" "));
        descriptor.getPassword().ifPresent(password -> sb.append(PREFIX_NEW_PASSWORD).append(password.toString())
                .append(" "));
        return sb.toString();
    }
}
