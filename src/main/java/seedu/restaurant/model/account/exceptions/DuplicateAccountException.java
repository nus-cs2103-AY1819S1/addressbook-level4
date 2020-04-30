package seedu.restaurant.model.account.exceptions;

//@@author AZhiKai
/**
 * Signals that the operation will result in duplicate {@code Account}. {@code Account}s are considered duplicate if
 * they have the same username).
 */
public class DuplicateAccountException extends AccountException {}
