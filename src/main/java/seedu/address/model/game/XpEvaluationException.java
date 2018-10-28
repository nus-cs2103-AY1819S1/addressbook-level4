package seedu.address.model.game;

/**
 * XpEvaluationException is a RuntimeException thrown when the tasks to be evaluated
 * for experience points do not make sense.
 */
public class XpEvaluationException extends RuntimeException {

    public XpEvaluationException(String message) {
        super(message);
    }
}
