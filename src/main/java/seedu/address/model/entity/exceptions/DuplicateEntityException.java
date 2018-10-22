package seedu.address.model.entity.exceptions;

/**
 * Signals that the operation would result in duplicated entities within the AddressBook.
 * Entities are considered identical if their identities are equal.
 *
 * @@author waytan
 */
public class DuplicateEntityException extends RuntimeException {
}
