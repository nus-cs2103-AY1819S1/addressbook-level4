package seedu.scheduler.logic.parser;

/**
 * An abstract class that indicates option in argument strings.
 */
abstract class Identity {

    private final String identity;

    public Identity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return this.identity;
    }

    @Override
    public int hashCode() {
        return identity == null ? 0 : identity.hashCode();
    }

    @Override
    public abstract boolean equals(Object obj);
}
