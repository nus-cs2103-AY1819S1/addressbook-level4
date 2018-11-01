package seedu.address.logic.commands.canvas;

//@author j-lum

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Handles the toggling of auto-resize.
 */

public class CanvasAutoResizeCommand extends CanvasCommand {
    private static final String TYPE = COMMAND_WORD + " auto";
    public static final String MESSAGE_USAGE = "Usage of canvas auto-resize: "
            + "\n- " + TYPE + " [ON/OFF]: " + "Turns auto-resize on or off."
            + "\n\tExample: " + TYPE + " off, turns auto-resize off.";

    private static final String OUTPUT_SUCCESS = "Auto-resize has been turned %s.";
    private static final String OUTPUT_FAILURE = "Invalid operation %s!";

    private boolean newProperty;

    public CanvasAutoResizeCommand(String args) {
        super(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        if (args.toLowerCase().equals("on")) {
            model.getCanvas().setCanvasAuto(true);
            return new CommandResult(String.format(OUTPUT_SUCCESS, args.toLowerCase()));
        }
        if (args.toLowerCase().equals("off")) {
            model.getCanvas().setCanvasAuto(false);
            return new CommandResult(String.format(OUTPUT_SUCCESS, args.toLowerCase()));
        }
        return new CommandResult(String.format(OUTPUT_FAILURE, args.toLowerCase())+ MESSAGE_USAGE);
    }
}
