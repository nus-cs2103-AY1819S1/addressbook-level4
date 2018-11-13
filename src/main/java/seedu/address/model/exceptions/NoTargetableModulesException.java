package seedu.address.model.exceptions;

/**
 * Flags that there are no targetable modules.
 */
public class NoTargetableModulesException extends RuntimeException {
    public NoTargetableModulesException() {
        super("No Modules Are Targetable!");
    }
}
