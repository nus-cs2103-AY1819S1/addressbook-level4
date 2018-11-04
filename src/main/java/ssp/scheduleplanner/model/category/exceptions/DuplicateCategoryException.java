package ssp.scheduleplanner.model.category.exceptions;

/**
 * Signals that the operation will result in duplicate Tasks (Tasks are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCategoryException extends RuntimeException {
    public DuplicateCategoryException() {
        super("Operation would result in duplicate categories");
    }
}
