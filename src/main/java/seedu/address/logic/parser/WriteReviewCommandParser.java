package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.user.WriteReviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Rating;
import seedu.address.model.restaurant.WrittenReview;

/**
 * Parses input arguments and creates a new WriteReviewCommand object
 */
public class WriteReviewCommandParser implements Parser<WriteReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the WriteReviewCommand
     * and returns an WriteReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public WriteReviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_RATING, PREFIX_REVIEW);

        Index index;

        try {
            index = ParserRestaurantUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    WriteReviewCommand.MESSAGE_USAGE), pe);
        }

        Rating rating = ParserRestaurantUtil.parseRating(argMultimap.getValue(PREFIX_RATING).get());
        WrittenReview writtenReview = ParserRestaurantUtil.parseWrittenReview(
                argMultimap.getValue(PREFIX_REVIEW).get());

        return new WriteReviewCommand(index, rating, writtenReview);
    }

}
