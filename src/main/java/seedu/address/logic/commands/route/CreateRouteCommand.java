package seedu.address.logic.commands.route;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.route.Route;

/**
 * Create a route with the given destination.
 */
public class CreateRouteCommand extends RouteCommand {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a route with a destination. "
            + "Parameters: "
            + PREFIX_ADDRESS + "ADDRESS\n"
            + "Example: " + RouteCommand.COMMAND_WORD + " " + COMMAND_WORD + " "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 ";

    public static final String MESSAGE_SUCCESS = "Route created.";
    public static final String MESSAGE_DUPLICATE_ROUTE = "This route already exists in the address book";

    private final Route toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Route}
     */
    public CreateRouteCommand(Address destination) {
        Route route = new Route(destination);
        toAdd = route;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasRoute(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROUTE);
        }

        model.addRoute(toAdd);
        model.commitRouteList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateRouteCommand // instanceof handles nulls
                && toAdd.equals(((CreateRouteCommand) other).toAdd));
    }

}
