package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddGroupCommand;
import seedu.address.logic.commands.DeleteGroupCommand;
import seedu.address.model.group.Group;

/**
 * A utility class for Group.
 */
public class GroupUtil {

    /**
     * Returns an add group command string for adding the {@code group}.
     */
    public static String getAddGroupCommand(Group group) {
        return AddGroupCommand.COMMAND_WORD + " " + getGroupAsName(group);
    }

    /**
     * Returns an delete group command string for adding the {@code group}.
     */
    public static String getDeleteGroupCommand(Group group) {
        return DeleteGroupCommand.COMMAND_WORD + " " + getDeleteGroupAsName(group);
    }

    /**
     * Returns the part of command string for the given {@code group}'s name details.
     */
    public static String getGroupAsName(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + group.getTitle().fullTitle + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code group}'s name details.
     */
    public static String getDeleteGroupAsName(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append(group.getTitle().fullTitle + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code group}'s title details.
     */
    public static String getGroupAsTitle(Group group) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_GROUP + group.getTitle().fullTitle + " ");
        return sb.toString();
    }
}
