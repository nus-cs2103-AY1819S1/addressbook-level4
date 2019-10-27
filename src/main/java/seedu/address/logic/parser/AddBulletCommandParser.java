package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddBulletCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddBulletCommand object
 */
public class AddBulletCommandParser implements Parser<AddBulletCommand> {

    public static final String SPACE_DELIMITER = "\\s+"; // split by one or more spaces

    /**
     * Parses the given {@code String} of arguments in the context of the AddBulletCommand
     * and returns an AddBulletCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBulletCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index index;

        AddBulletCommandTokenizer t = new AddBulletCommandTokenizer(args.trim());
        t.tokenize();

        try {
            index = ParserUtil.parseIndex(t.getIndex());
            return new AddBulletCommand(index, t.getDescription());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBulletCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * a helper class to tokenize the args of addBullet command in the form INDEX + DESCRIPTION
     */
    public static class AddBulletCommandTokenizer {
        private String args;
        private String[] argsArr;

        public AddBulletCommandTokenizer(String args) {
            this.args = args;
        }

        public void tokenize() {
            argsArr = args.split(SPACE_DELIMITER, 2);
        }

        public String getIndex() {
            return argsArr[0];
        }

        public String getDescription() throws ParseException {
            if (argsArr.length == 1) {
                throw new ParseException("");
            }
            return argsArr[1];
        }
    }

}


