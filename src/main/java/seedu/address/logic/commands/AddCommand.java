package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_URL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wish.Wish;

/**
 * Adds a wish to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a wish to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PRICE + "PRICE "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_URL + "URL] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "iPad 10 "
            + PREFIX_PRICE + "6080.50 "
            + PREFIX_DATE + "20/11/2021 "
            + PREFIX_URL + "https://www.amazon.com/gp/product/B07D998212 "
            + PREFIX_REMARK + "For dad. "
            + PREFIX_TAG + "electronics "
            + PREFIX_TAG + "personal ";

    public static final String MESSAGE_SUCCESS = "New wish added: %1$s";
    public static final String MESSAGE_DUPLICATE_WISH = "This wish already exists in the address book";

    private final Wish toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Wish}
     */
    public AddCommand(Wish wish) {
        requireNonNull(wish);
        toAdd = wish;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasWish(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_WISH);
        }

        model.addWish(toAdd);
        model.commitWishBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    /**
     * A weaker notion of equality between {@code AddCommand}.
     */
    public boolean isSameAs(AddCommand other) {
        return other == this
                || toAdd.getName().equals(other.toAdd.getName())
                && toAdd.getPrice().equals(other.toAdd.getPrice())
                && toAdd.getDate().equals(other.toAdd.getDate());
    }
}
