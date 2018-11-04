package seedu.restaurant.logic.parser.account;

import static java.util.Objects.requireNonNull;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.account.Password;
import seedu.restaurant.model.account.Username;

//@@author AZhiKai
/**
 * Contains utility methods used for parsing strings {@code Account}-related classes.
 */
public class AccountParserUtil {

    // This class should not be instantiated.
    private AccountParserUtil() {
        throw new AssertionError("AccountParserUtil should not be instantiated.");
    }

    /**
     * Parses a {@code username} into a {@code Username}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code username} is invalid.
     */
    public static Username parseUsername(String username) throws ParseException {
        requireNonNull(username);
        String trimmedUsername = username.trim();
        if (!Username.isValidUsername(trimmedUsername)) {
            throw new ParseException(Username.MESSAGE_USERNAME_CONSTRAINT);
        }
        return new Username(trimmedUsername);
    }

    /**
     * Parses a {@code password} into a {@code Password}. Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code password} is invalid.
     */
    public static Password parsePassword(String password) throws ParseException {
        requireNonNull(password);
        String trimmedPassword = password.trim();
        if (!Password.isValidPassword(trimmedPassword)) {
            throw new ParseException(Password.MESSAGE_PASSWORD_CONSTRAINT);
        }
        return new Password(trimmedPassword);
    }
}
