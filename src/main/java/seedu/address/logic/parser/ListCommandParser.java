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

    public static Prefix PREFIX_DUE_BEFORE = new Prefix("b/");

    private Optional<ListCommand.ListFilter> parseListFilter(Optional<String> s)throws ParseException {
        if (s.isPresent()) {
            String val = s.get();
            switch (val) {
                case "today":
                    return Optional.of(ListCommand.ListFilter.DUE_TODAY);
                default:
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
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

        Optional<ListCommand.ListFilter> listFilter = parseListFilter(argMultimap.getValue(PREFIX_DUE_BEFORE));

        return listFilter
                .flatMap(filter -> Optional.of(new ListCommand(filter)))
                .orElseGet(() -> new ListCommand());
    }
}
