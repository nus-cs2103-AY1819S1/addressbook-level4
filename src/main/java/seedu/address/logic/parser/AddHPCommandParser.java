package seedu.address.logic.parser;

import seedu.address.logic.commands.AddHealthplanCommand;
import seedu.address.model.healthplan.*;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HPNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TWEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CWEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHEIGHT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEME;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Set;
import java.util.stream.Stream;

import java.util.stream.Stream;

public class AddHPCommandParser implements Parser<AddHealthplanCommand>  {

    public AddHealthplanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HPNAME, PREFIX_TWEIGHT, PREFIX_CWEIGHT, PREFIX_CHEIGHT, PREFIX_AGE,PREFIX_DURATION,PREFIX_SCHEME);

        if (!arePrefixesPresent(argMultimap, PREFIX_HPNAME, PREFIX_TWEIGHT, PREFIX_CWEIGHT, PREFIX_CHEIGHT, PREFIX_AGE,PREFIX_DURATION,PREFIX_SCHEME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHealthplanCommand.MESSAGE_USAGE));
        }

        HealthPlanName name =ParserUtil.parseHPName(argMultimap.getValue(PREFIX_HPNAME).get());
        TargetWeight tweight =ParserUtil.parseTWeight(argMultimap.getValue(PREFIX_TWEIGHT).get());
        CurrentWeight cweight =ParserUtil.parseCWeight(argMultimap.getValue(PREFIX_CWEIGHT).get());
        CurrentHeight cheight =ParserUtil.parseCHeight(argMultimap.getValue(PREFIX_CHEIGHT).get());
        Age age=ParserUtil.parseAge(argMultimap.getValue(PREFIX_AGE).get());
        Duration duration =ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());
        Scheme scheme  = ParserUtil.parseScheme(argMultimap.getValue(PREFIX_SCHEME).get());


       // Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        HealthPlan hp = new HealthPlan(name,tweight,cweight,cheight,age,duration,scheme);

        return new AddHealthplanCommand(hp);
    }


    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}
