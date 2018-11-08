package seedu.address.logic.commands.layer;

//@author j-lum
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;

/**
 * Handles the repositioning of Layers.
 * Commands are in the form : layer add [index].
 * Invalid indexes and missing canvas objects are handled.
 */

public class LayerSelectCommand extends LayerCommand {
    public static final String TYPE = COMMAND_WORD + " select";
    public static final String MESSAGE_USAGE = "Usage of layer position: "
            + "\n- " + TYPE + " [INDEX]: " + "Selects a layer to work on."
            + "\n\tExample: " + TYPE + " 2, selects the layer with index 2 to work on.";

    public static final String OUTPUT_SUCCESS = "Now working on layer %d.";
    public static final String OUTPUT_FAILURE = "Invalid layer index provided!";
    public static final String OUTPUT_MISSING_CANVAS = "You must open an image file for editing before proceeding!";

    public LayerSelectCommand(String args) {
        super(args);
    }

    @Override

    public CommandResult execute(Model model, CommandHistory history) {
        if (args == null) {
            return new CommandResult(String.format(OUTPUT_FAILURE));
        }
        int i;
        Index index;
        try {
            i = Integer.parseInt(args);
            if (i < 0 | i > model.getCanvas().getLayers().size()) {
                throw new NumberFormatException();
            }
            index = Index.fromOneBased(i);
            model.setCurrentLayer(index);
            model.refreshHistoryList();
        } catch (NumberFormatException e) {
            return new CommandResult(OUTPUT_FAILURE);
        } catch (NullPointerException e) {
            return new CommandResult(OUTPUT_MISSING_CANVAS);
        }

        return new CommandResult(String.format(OUTPUT_SUCCESS, index.getOneBased()));
    }
}
