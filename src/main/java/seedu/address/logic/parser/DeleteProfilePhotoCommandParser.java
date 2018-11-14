
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteProfilePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new DeleteProfilePictureCommand object
 */
public class DeleteProfilePhotoCommandParser implements Parser<DeleteProfilePhotoCommand> {

    private String path;
    private Index index;
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteProfilePhotoCommand
     * and returns an DeleteProfilePhotoCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteProfilePhotoCommand parse(String args) throws ParseException {

        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteProfilePhotoCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteProfilePhotoCommand.MESSAGE_USAGE));
        }
    }

}
