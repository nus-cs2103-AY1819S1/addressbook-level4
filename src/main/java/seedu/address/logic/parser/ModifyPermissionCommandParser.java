package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_PERMISSION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_PERMISSION;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ModifyPermissionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.permission.Permission;

public class ModifyPermissionCommandParser implements Parser<ModifyPermissionCommand> {

    @Override
    public ModifyPermissionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADD_PERMISSION, PREFIX_REMOVE_PERMISSION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ModifyPermissionCommand.MESSAGE_USAGE), pe);
        }
        Set<Permission> permissionToAdd = new HashSet<>();
        parsePermission(argMultimap.getAllValues(PREFIX_ADD_PERMISSION)).ifPresent(permissionToAdd::addAll);

        Set<Permission> permissionToRemove = new HashSet<>();
        parsePermission(argMultimap.getAllValues(PREFIX_REMOVE_PERMISSION)).ifPresent(permissionToRemove::addAll);

        return new ModifyPermissionCommand(index, permissionToAdd, permissionToRemove);
    }

    private Optional<Set<Permission>> parsePermission(Collection<String> permission) throws ParseException {
        assert permission != null;

        if (permission.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> permissionSet = permission.size() == 1 && permission.contains("") ? Collections.emptySet() : permission;
        return Optional.of(ParserUtil.parsePermissions(permissionSet));

    }
}
