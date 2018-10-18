package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISITOR;

import java.util.stream.Stream;

import seedu.address.logic.commands.VisitorInCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.visitor.Visitor;


//@@ gao jiaxin
/**
 * Parses input arguments and creates a new VisitorInCommand object
 */
public class VisitorInCommandParser implements Parser<VisitorInCommand> {


    @Override
    public VisitorInCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_VISITOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_VISITOR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException((String.format(MESSAGE_INVALID_COMMAND_FORMAT, VisitorInCommand.MESSAGE_USAGE)));
        }

        Name patientName = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Visitor visitor = ParserUtil.parseVisitor(argMultimap.getValue(PREFIX_VISITOR).get());

        //TODO need to find the right person stored somewhere, then use the person in AddmhCommand
        return new VisitorInCommand(patientName, visitor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
