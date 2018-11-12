package seedu.address.model;

import static java.util.Objects.hash;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SWITCH;

/**
 * Represents a Context in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidContextId(String)}
 */
public class Context {
    public static final String EVENT_CONTEXT_ID = "e";
    public static final String VOLUNTEER_CONTEXT_ID = "v";
    public static final String RECORD_CONTEXT_ID = "r";

    public static final String EVENT_CONTEXT_NAME = "events";
    public static final String VOLUNTEER_CONTEXT_NAME = "volunteers";
    public static final String RECORD_CONTEXT_NAME = "volunteer records";

    public static final String MESSAGE_CONTEXT_CONSTRAINTS =
            "Context can only be " + PREFIX_SWITCH + EVENT_CONTEXT_ID + " or " + PREFIX_SWITCH + VOLUNTEER_CONTEXT_ID;

    private String contextId;
    private String contextName;

    /**
     * Constructs an {@code Context}.
     *
     * @param contextId A valid context string.
     */
    public Context(String contextId, String contextName) {
        requireNonNull(contextId);
        checkArgument(isValidContextId(contextId), MESSAGE_CONTEXT_CONSTRAINTS);
        this.contextId = contextId;
        this.contextName = contextName;
    }

    /**
     * Sets a context
     */
    public void setContextValue(String contextId) {
        requireNonNull(contextId);
        checkArgument(isValidContextId(contextId), MESSAGE_CONTEXT_CONSTRAINTS);
        this.contextId = contextId;
        if (contextId.equals(EVENT_CONTEXT_ID)) {
            contextName = EVENT_CONTEXT_NAME;
        }
        if (contextId.equals(VOLUNTEER_CONTEXT_ID)) {
            contextName = VOLUNTEER_CONTEXT_NAME;
        }
    }

    /**
     * Returns the context id
     */
    public String getContextId() {
        return contextId;
    }

    /**
     * Returns the context name
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * External command to switch to manage context
     */
    public void switchToRecordContext() {
        this.contextId = RECORD_CONTEXT_ID;
        this.contextName = RECORD_CONTEXT_NAME;
    }

    /**
     * Returns true if a given contextId is a valid contextId.
     */
    public static boolean isValidContextId(String test) {
        if (test.equals(EVENT_CONTEXT_ID) || test.equals(VOLUNTEER_CONTEXT_ID)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Context Id: " + contextId + " "
                + "Context Name: " + contextName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Context // instanceof handles nulls
                && contextId.equals(((Context) other).contextId)
                && contextName.equals(((Context) other).contextName)); // state check
    }

    @Override
    public int hashCode() {
        return hash(contextId, contextName);
    }

}
