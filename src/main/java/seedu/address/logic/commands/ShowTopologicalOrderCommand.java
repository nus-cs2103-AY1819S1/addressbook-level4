package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Lists tasks in topological order in CLI
 */
public class ShowTopologicalOrderCommand extends Command {
    public static final String COMMAND_WORD = "topoorder";

    public static final String RESULT_HEADER = "Here are the uncompleted tasks listed in topological order:\n";

    public ShowTopologicalOrderCommand() {
    }


    @Override
    public CommandResult executePrimitive(Model model, CommandHistory history) {
        requireNonNull(model);

        StringBuilder sb = new StringBuilder();
        sb.append(RESULT_HEADER);

        List<Task> order = model.getTopologicalOrder();
        for (Task task:order) {
            sb.append(task.getName());
            sb.append("\n");
        }
        return new CommandResult(sb.toString());
    }


    @Override
    public boolean equals(Object other) {
        return other == this;
    }
}
