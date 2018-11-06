package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.learnvocabulary.logic.commands.AddCommand;
import seedu.learnvocabulary.logic.parser.exceptions.ParseException;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_MEANING, PREFIX_TAG);


        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_MEANING)
                || !argMultimap.getPreamble().isEmpty() || argMultimap.hasDuplicatePrefix(PREFIX_NAME)
                || argMultimap.hasDuplicatePrefix(PREFIX_MEANING)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Meaning meaning = ParserUtil.parseMeaning(argMultimap.getValue(PREFIX_MEANING).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (tagList.isEmpty()) {
            tagList.add(new Tag(
                    "toLearn"));
        }

        Word word = new Word(name, meaning, tagList);

        return new AddCommand(word);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
