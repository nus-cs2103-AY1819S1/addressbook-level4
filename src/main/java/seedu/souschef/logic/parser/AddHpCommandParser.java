package seedu.souschef.logic.parser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.souschef.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_SCHEME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TWEIGHT;

import java.util.stream.Stream;

import seedu.souschef.logic.commands.AddHealthplanCommand;

import seedu.souschef.logic.parser.exceptions.ParseException;

import seedu.souschef.model.healthplan.Age;
import seedu.souschef.model.healthplan.CurrentHeight;
import seedu.souschef.model.healthplan.CurrentWeight;
import seedu.souschef.model.healthplan.Duration;
import seedu.souschef.model.healthplan.HealthPlan;
import seedu.souschef.model.healthplan.HealthPlanName;
import seedu.souschef.model.healthplan.Scheme;
import seedu.souschef.model.healthplan.TargetWeight;

/**
 * class to handle the parsing of the health plan commands
 */
public class AddHpCommandParser implements Parser<AddHealthplanCommand> {
    /**
     * main Parser function for this command parser
     */
    public AddHealthplanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HPNAME, PREFIX_TWEIGHT, PREFIX_CWEIGHT, PREFIX_CHEIGHT,
                        PREFIX_AGE, PREFIX_DURATION, PREFIX_SCHEME);

        if (!arePrefixesPresent(argMultimap, PREFIX_HPNAME, PREFIX_TWEIGHT, PREFIX_CWEIGHT, PREFIX_CHEIGHT, PREFIX_AGE,
                PREFIX_DURATION, PREFIX_SCHEME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHealthplanCommand.MESSAGE_USAGE));
        }

        HealthPlanName name = ParserUtil.parseHpName(argMultimap.getValue(PREFIX_HPNAME).get());
        TargetWeight tweight = ParserUtil.parseTWeight(argMultimap.getValue(PREFIX_TWEIGHT).get());
        CurrentWeight cweight = ParserUtil.parseCWeight(argMultimap.getValue(PREFIX_CWEIGHT).get());
        CurrentHeight cheight = ParserUtil.parseCHeight(argMultimap.getValue(PREFIX_CHEIGHT).get());
        Age age = ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Scheme scheme = ParserUtil.parseScheme(argMultimap.getValue(PREFIX_SCHEME).get());

        HealthPlan hp = new HealthPlan(name, tweight, cweight, cheight, age, duration, scheme);

        return new AddHealthplanCommand(hp);
    }


    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}
