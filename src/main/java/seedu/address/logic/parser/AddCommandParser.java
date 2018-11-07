package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_DOUBLE_DATE_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.address.model.wish.Url.DEFAULT_URL;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Date;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected FORMAT
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_DATE, PREFIX_AGE, PREFIX_URL,
                        PREFIX_REMARK, PREFIX_TAG);

        if (!(arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_DATE)
                || arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_AGE))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_AGE)) {
            throw new ParseException(MESSAGE_DOUBLE_DATE_FORMAT);
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Date date;
        if (arePrefixesPresent(argMultimap, PREFIX_AGE)) {
            Period period = ParserUtil.parsePeriod(argMultimap.getValue(PREFIX_AGE).get());
            java.util.Date now = new java.util.Date();
            LocalDate localNow = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate finalDate = localNow.plus(period);
            date = ParserUtil.parseDate(String.format("%02d/%02d/%d", finalDate.getDayOfMonth(),
                    finalDate.getMonth().getValue(),
                    finalDate.getYear()));
        } else {
            date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        }

        Url url = ParserUtil.parseUrl(argMultimap.getValue(PREFIX_URL).orElse(DEFAULT_URL));

        SavedAmount savedAmount = new SavedAmount("0.0");
        //Remark remark = new Remark(""); // remark cannot be added manually by add command

        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).orElse(""));

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Wish wish = Wish.createWish(name, price, date, url, savedAmount, remark, tagList);

        return new AddCommand(wish);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
