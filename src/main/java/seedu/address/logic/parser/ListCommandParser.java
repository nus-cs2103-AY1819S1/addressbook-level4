package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DUE_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY_VALUE;
import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import javax.swing.text.html.Option;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Label;
import seedu.address.model.task.Description;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Name;
import seedu.address.model.task.PriorityValue;
import seedu.address.model.task.Task;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {

    private static Prefix PREFIX_DUE_BEFORE = new Prefix("b/");

    private Optional<ListCommand.ListFilter> parseListFilter(Optional<String> s) {
        if (s.isPresent()) {
            String val = s.get();
            switch (val) {
                case "today":
                    return Optional.of(ListCommand.ListFilter.DUE_TODAY);
                default:
                    return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DUE_BEFORE);

        if (!arePrefixesPresent(argMultimap, PREFIX_DUE_BEFORE)
                || !argMultimap.getPreamble().isEmpty()) {
          // TODO
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, "Walk it like I talk it"));
        }

        Optional<ListCommand.ListFilter> listFilter = parseListFilter(argMultimap.getValue(PREFIX_DUE_BEFORE));

        return listFilter
                .flatMap(filter -> Optional.of(new ListCommand(filter)))
                .orElseGet(() -> new ListCommand());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
