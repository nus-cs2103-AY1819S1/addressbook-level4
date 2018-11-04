package seedu.address.logic.commands.canvas;

import seedu.address.logic.commands.Command;

//@@author j-lum

/**
 * Parent for all Canvas commands
 */
public abstract class CanvasCommand extends Command {

    public static final String COMMAND_WORD = "canvas";
    protected String args;

    CanvasCommand(String args) {
        this.args = args;
    }
}
