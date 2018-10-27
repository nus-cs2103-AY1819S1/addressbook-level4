package seedu.address.logic;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

//@@author zioul123

/**
 * Contains a utility method used for parsing a {@code String} for an Index, without throwing ParseExceptions.
 */
public class IndexParserUtil {

    /**
     * Attempts to parse an index from the provided string.
     * @param string The preamble of the command typed.
     * @return An Optional of the index if the specified index is valid, an empty optional otherwise
     */
    public static Optional<Index> getIndex(String string) {
        Index index;
        try {
            index = ParserUtil.parseIndex(string);
        } catch (ParseException pe) {
            return Optional.empty();
        }
        return Optional.of(index);
    }
}
