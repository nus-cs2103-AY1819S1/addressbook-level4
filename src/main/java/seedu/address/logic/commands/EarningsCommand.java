package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.text.DecimalFormat;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists the tuition fee of a person in address book
 */
public class EarningsCommand extends Command {

    public static final String COMMAND_WORD = "earnings";

    public static final String MESSAGE_SUCCESS = "Earning is successfully retrieved!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        ObservableList<Person> persons = model.getAddressBook().getPersonList();
        Double sumOfTuitionFees = persons.stream()
                .mapToDouble(person -> Double.valueOf(person.getFees().value))
                .sum();

        DecimalFormat df = new DecimalFormat("#.00");
        return new CommandResult(String.format("Total tuition fee: " + String.valueOf(df.format(sumOfTuitionFees))));
    }
}
