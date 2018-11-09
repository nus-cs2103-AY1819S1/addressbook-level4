package seedu.address.model.exceptions;

public class NoTargetableModulesException extends RuntimeException {
    public NoTargetableModulesException() {
        super("No Modules Are Targetable!");
    }
}
