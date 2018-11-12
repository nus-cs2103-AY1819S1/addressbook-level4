package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.ExchangeTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * ChangeTimeCommandParser.
 */
public class ExchangeTimeCommandParser implements Parser<ExchangeTimeCommand> {
    /**
     * ChangeTimeCommand
     *
     * @param args
     * @return
     * @throws ParseException
     */
    public ExchangeTimeCommand parse(String args) throws ParseException {


        try {
            String[] stringCommand = args.trim().split(" ");
            if (stringCommand.length != 4) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
            }
            int numA = Integer.parseInt(stringCommand[0]);
            int numB = Integer.parseInt(stringCommand[1]);

            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME);

            if (!argMultimap.getValue(PREFIX_NAME).isPresent()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
            }

            String nameA = argMultimap.getAllValues(PREFIX_NAME).get(0);
            String nameB = argMultimap.getAllValues(PREFIX_NAME).get(1);

            if (nameA.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
            }
            if (nameB.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
            }
            if (numA < 0) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
            }
            if (numB < 0) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
            }
            return new ExchangeTimeCommand(args);

        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExchangeTimeCommand.MESSAGE_USAGE));
        }
    }
}
