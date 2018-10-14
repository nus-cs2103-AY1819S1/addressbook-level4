package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

        if (trimmedArgs.split("/").length == 1){ //Ensure args contains at least one prefix or keyword
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap keywordsMap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_CATEGORY, PREFIX_COST, PREFIX_TAG, PREFIX_DATE);
        ensureKeywordsAreValid(keywordsMap);

        return new FindCommand(new ExpenseContainsKeywordsPredicate(keywordsMap));
    }

    public void ensureKeywordsAreValid(ArgumentMultimap keywordsMap) throws ParseException{
        String nameKeywords = keywordsMap.getValue(PREFIX_NAME).orElse(null);
        String categoryKeywords = keywordsMap.getValue(PREFIX_CATEGORY).orElse(null);
        List<String> tagKeywords = keywordsMap.getAllValues(PREFIX_TAG);
        String dateKeywords = keywordsMap.getValue(PREFIX_DATE).orElse(null);
        String costKeywords = keywordsMap.getValue(PREFIX_COST).orElse(null);

        if (nameKeywords != null && !Name.isValidName(nameKeywords)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Name.MESSAGE_NAME_CONSTRAINTS));
        }

        if (categoryKeywords != null && !Category.isValidCategory(categoryKeywords)){
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Category.MESSAGE_CATEGORY_CONSTRAINTS));
        }

        if (!tagKeywords.isEmpty()) {
            for (String tag : tagKeywords){
                if (!Tag.isValidTagName(tag)){
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Tag.MESSAGE_TAG_CONSTRAINTS));
                }
            }
        }

        if (dateKeywords != null) {
            String[] dates = dateKeywords.split(":");
            for (String date : dates) {
                if (!Date.isValidDate(date)) {
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Date.DATE_FORMAT_CONSTRAINTS));
                }
            }
        }

        if (costKeywords != null) {
            String[] costs = costKeywords.split(":");
            for (String cost : costs) {
                if (!Cost.isValidCost(cost)){
                    throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, Cost.MESSAGE_ADDRESS_CONSTRAINTS));
                }
            }
        }
    }

}
