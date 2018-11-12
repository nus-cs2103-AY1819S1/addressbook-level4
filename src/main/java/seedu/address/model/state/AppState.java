package seedu.address.model.state;

/**
 * Defines the state of the application with their respective constraints on the type of command it can run.
 */
public class AppState {
    private State currentState;

    public AppState() {
        currentState = State.LEARN;
    }

    public void setAppState(State state) {
        currentState = state;
    }

    public State getState() {
        return currentState;
    }
}
