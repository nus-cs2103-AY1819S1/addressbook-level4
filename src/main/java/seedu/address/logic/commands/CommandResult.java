package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.ProcessCommand;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private String feedbackToUser;

    private List<ProcessCommand<String, CommandResult, CommandException>> interceptors;

    public CommandResult(String feedbackToUser) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        interceptors = new ArrayList<>();
    }

    /**
     * Adds an intercepter to the be processed later after this user command. If any intercepts are in the list,
     * they will "intercept" a user input and will prevent it from being interpreted as a command.
     * The next user input will always be given to the lambda.
     * @param interceptor The method for the user input to pass into. Returned command result will be processed as if
     *                    it was a normal command.
     */
    public void addIntercepter(ProcessCommand<String, CommandResult, CommandException> interceptor) {
        interceptors.add(interceptor);
    }

    /**
     * Gets a list of intercepts as required by this command
     * @return A list of interceptors for the next user input.
     */
    public List<ProcessCommand<String, CommandResult, CommandException>> getIntercepters() {
        return interceptors;
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    /**
     * Absorbs another command result into this. Interceptors list will be merged and the text of the other command
     * will be appended after a newline.
     * @param other
     */
    public void absorb(CommandResult other) {
        this.interceptors.addAll(other.getIntercepters());
        this.feedbackToUser += "\r\n" + other.getFeedbackToUser();
    }

}
