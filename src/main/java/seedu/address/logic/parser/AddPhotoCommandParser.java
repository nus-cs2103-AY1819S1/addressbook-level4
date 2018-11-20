package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddProfilePhotoCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ProfilePhoto;

/**
 * Parses input arguments and creates a new AddPictureCommand object
 */
public class AddPhotoCommandParser implements Parser<AddProfilePhotoCommand> {

    private String path;
    private Index index;
    /**
     * Parses the given {@code String} of arguments in the context of the AddPictureCommand
     * and returns an AddPictureCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddProfilePhotoCommand parse(String args) throws ParseException {

        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_FILEPATH);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILEPATH)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProfilePhotoCommand.MESSAGE_USAGE));
        }

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (Exception e) {
            throw new ParseException(ProfilePhoto.MESSAGE_PHOTO_CONSTRAINTS);
        }

        try {
            path = ParserUtil.parseImageFilename(argMultimap.getValue(PREFIX_FILEPATH).get());
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            e.printStackTrace();
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddProfilePhotoCommand.MESSAGE_USAGE));
        }


        return new AddProfilePhotoCommand(index, path);
    }


    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());

    }

}
