package seedu.address.logic.parser;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EarningsCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EarningsCommand object
 */
public class EarningsCommandParser implements Parser<EarningsCommand> {

	public EarningsCommand parse(String args) throws ParseException {

		//v1.1 args should contain name of student to retrieve his tuition fees
		String trimmedArgs = args.trim();
		if (trimmedArgs.isEmpty()) {
			throw new ParseException(
					String.format("Enter error message here"));
		}

		return new EarningsCommand(trimmedArgs);
	}
}
