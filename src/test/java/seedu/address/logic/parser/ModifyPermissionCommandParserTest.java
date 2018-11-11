package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_PERMISSION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModifyPermissionCommand;
import seedu.address.model.permission.Permission;

class ModifyPermissionCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyPermissionCommand.MESSAGE_USAGE);

    private ModifyPermissionCommandParser parser = new ModifyPermissionCommandParser();

    private String invalidAddPermissionArg = " " + PREFIX_ADD_PERMISSION + "INVALID_PERMISSION ";
    private String invalidRemovePermissionArg = " " + PREFIX_REMOVE_PERMISSION + "INVALID_PERMISSION ";
    private String validAddPermissionArg = " " + PREFIX_ADD_PERMISSION + Permission.ADD_EMPLOYEE.name();
    private String validRemovePermissionArg = " " + PREFIX_REMOVE_PERMISSION + Permission.DELETE_EMPLOYEE.name();

    @Test
    void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", ModifyPermissionCommand.MESSAGE_NO_MODIFICATION);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid add permission
        assertParseFailure(parser, "1" + invalidAddPermissionArg, Permission.MESSAGE_INVALID_PERMISSION);
        // invalid remove permission
        assertParseFailure(parser, "1" + invalidRemovePermissionArg, Permission.MESSAGE_INVALID_PERMISSION);
        // invalid add and remove permission
        assertParseFailure(parser, "1" + invalidAddPermissionArg + invalidRemovePermissionArg,
                Permission.MESSAGE_INVALID_PERMISSION);
    }

    @Test
    public void parse_addPermission_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = INDEX_FIRST_PERSON.getOneBased() + validAddPermissionArg;
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toAdd.add(Permission.ADD_EMPLOYEE);

        ModifyPermissionCommand expectedCommand = new ModifyPermissionCommand(targetIndex, toAdd, toRemove);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_removePermission_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = INDEX_FIRST_PERSON.getOneBased() + validRemovePermissionArg;
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toRemove.add(Permission.DELETE_EMPLOYEE);

        ModifyPermissionCommand expectedCommand = new ModifyPermissionCommand(targetIndex, toAdd, toRemove);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_addAndRemovePermission_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = INDEX_FIRST_PERSON.getOneBased() + validAddPermissionArg + validRemovePermissionArg;
        Set<Permission> toAdd = new HashSet<>();
        Set<Permission> toRemove = new HashSet<>();
        toAdd.add(Permission.ADD_EMPLOYEE);
        toRemove.add(Permission.DELETE_EMPLOYEE);


        ModifyPermissionCommand expectedCommand = new ModifyPermissionCommand(targetIndex, toAdd, toRemove);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
