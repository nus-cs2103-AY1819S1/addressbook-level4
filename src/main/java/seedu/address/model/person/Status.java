package seedu.address.model.person;

/**
 * Represents a Task's status in the address book.
 * There are two possible status: IN_PROGRESS and FINISHED.
 */
enum Status {
    IN_PROGRESS, FINISHED;
    
    @Override
    public String toString() {
        switch (this) {
            case IN_PROGRESS:
                return "IN PROGRESS";
            case FINISHED:
                return "FINISHED";
            default:
                throw new IllegalArgumentException();
        }
    }
}