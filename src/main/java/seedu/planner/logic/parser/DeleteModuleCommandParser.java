package seedu.planner.logic.parser;

import static seedu.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_CODE;

import java.util.List;
import java.util.stream.Stream;

import seedu.planner.logic.commands.DeleteModuleCommand;
import seedu.planner.logic.parser.exceptions.ParseException;
import seedu.planner.model.module.Module;

//@@author GabrielYik

/**
 * A parser that parse an input argument and creates a DeleteModuleCommand.
 */
public class DeleteModuleCommandParser implements Parser<DeleteModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModuleCommand
     * and returns a DeleteModuleCommand object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format
     */
    @Override
    public DeleteModuleCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_CODE) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));
        }

        List<Module> modules = ParserUtil.parseModuleCodes(argMultimap.getAllValues(PREFIX_CODE));

        return new DeleteModuleCommand(modules);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
