package seedu.address.logic.commands.layer;

import seedu.address.logic.commands.Command;

//@@author j-lum

/**
 * Parent for all Layer commands
 */
public abstract class LayerCommand extends Command {

    public static final String COMMAND_WORD = "layer";
    protected String args;

    LayerCommand(String args) {
        this.args = args;
    }
}
