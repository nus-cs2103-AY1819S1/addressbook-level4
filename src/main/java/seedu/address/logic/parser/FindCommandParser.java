package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.FindCommand.MESSAGE_INVALID_COST_KEYWORDS_FORMAT;
import static seedu.address.logic.commands.FindCommand.MESSAGE_INVALID_DATE_KEYWORDS_FORMAT;
import static seedu.address.logic.commands.FindCommand.MESSAGE_INVALID_RANGE;
import static seedu.address.logic.commands.FindCommand.MESSAGE_MULTIPLE_KEYWORDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.expense.Category;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.ExpenseContainsKeywordsPredicate;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
//@@Author Jiang Chen
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] splitTrimmedArgs = trimmedArgs.split("/");
        if (splitTrimmedArgs.length == 1 || splitTrimmedArgs[0].equals("")) {
            //Ensure args contains at least one prefix or keyword
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap keywordsMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CATEGORY, PREFIX_COST, PREFIX_TAG, PREFIX_DATE);

        ensureKeywordsAreValid(keywordsMap);

        return new FindCommand(new ExpenseContainsKeywordsPredicate(keywordsMap));
    }

    /**
     *  Check whether all the keywords are valid.
     * @throws ParseException if any keyword entered by user does not conform the expected format, or user enters
     * multiple keywords for Name/Category/Cost/Date.
     * */
    public void ensureKeywordsAreValid(ArgumentMultimap keywordsMap) throws ParseException {
        List<String> nameKeywords = keywordsMap.getAllValues(PREFIX_NAME);
        List<String> categoryKeywords = keywordsMap.getAllValues(PREFIX_CATEGORY);
        List<String> tagKeywords = keywordsMap.getAllValues(PREFIX_TAG);
        List<String> dateKeywords = keywordsMap.getAllValues(PREFIX_DATE);
        List<String> costKeywords = keywordsMap.getAllValues(PREFIX_COST);

        //If the user enters multiple name keywords
        if (nameKeywords.size() > 1){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_MULTIPLE_KEYWORDS));
        }

        //If the name keyword is invalid
        if (!nameKeywords.isEmpty() && !Name.isValidName(nameKeywords.get(0))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_NAME_CONSTRAINTS));
        }

        //If the user enters multiple category keywords
        if (categoryKeywords.size() > 1){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_MULTIPLE_KEYWORDS));
        }

        //If the category keyword is invalid
        if (!categoryKeywords.isEmpty() && !Category.isValidCategory(categoryKeywords.get(0))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    Category.MESSAGE_CATEGORY_CONSTRAINTS));
        }

        //If any of the tag keywords is invalid
        if (!tagKeywords.isEmpty()) {
            for (String tag : tagKeywords) {
                if (!Tag.isValidTagName(tag)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            Tag.MESSAGE_TAG_CONSTRAINTS));
                }
            }
        }

        //If the user enters multiple date keywords
        if (dateKeywords.size() > 1){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_MULTIPLE_KEYWORDS));
        }

        if (!dateKeywords.isEmpty()) {
            String[] dates = dateKeywords.get(0).split(":");
            //If the user enters one date keyword and it is invalid
            if (dates.length == 1 && !Date.isValidDate(dates[0])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Date.DATE_FORMAT_CONSTRAINTS));
            }

            //If the user enters a range of date keywords and any of the two dates is invalid
            if (dates.length == 2 && (!Date.isValidDate(dates[0]) || !Date.isValidDate(dates[1]))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Date.DATE_FORMAT_CONSTRAINTS));
            }

            //If the ending date is earlier than the starting date
            if (dates.length == 2 && Date.compare(new Date(dates[0]) ,new Date(dates[1])) == -1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_RANGE));
            }

            //If the user enters more than one colon
            if (dates.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_DATE_KEYWORDS_FORMAT));
            }

        }

        //If the user enters multiple cost keywords
        if (costKeywords.size() > 1){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_MULTIPLE_KEYWORDS));
        }

        if (!costKeywords.isEmpty()) {
            String[] costs = costKeywords.get(0).split(":");
            //If the user enters one keyword and it is invalid
            if (costs.length == 1 && !Cost.isValidCost(costs[0])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Cost.MESSAGE_COST_CONSTRAINTS));
            }

            //If the user enters two keywords and any of them is invalid
            if (costs.length == 2 && (!Cost.isValidCost(costs[0]) || !Cost.isValidCost(costs[1]))) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        Cost.MESSAGE_COST_CONSTRAINTS));
            }

            //If the higher bound is smaller than the lower bound
            if (costs.length == 2 && Double.parseDouble(costs[1]) < Double.parseDouble(costs[0])) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_RANGE));
            }

            //If the user enters more than one colon
            if (costs.length > 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        MESSAGE_INVALID_COST_KEYWORDS_FORMAT));

            }
        }
    }

}
