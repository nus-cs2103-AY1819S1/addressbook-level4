package seedu.address.logic.commands;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.*;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;



public class ShowListCommand extends Command{

    public static final String COMMAND_WORD = "showList";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the list of people in address book.\n"
            + "Example: " + COMMAND_WORD;


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        ObservableList<Person> persons = model.getFilteredPersonList(); ;
        List<Person> personList = (List<Person>) persons;
        return new CommandResult(setList(personList));
    }


    /**
     * print the number of ppl.
     * @param personList The list of ppl in addressbook.
     * @return A string presenting the number of ppl.
     */
    public String setList(List<Person> personList) {
        int numPeople = personList.size();
        return String.format(
                "Current number of people in address book : %1$s!", numPeople) ;
    }
    public String createStatsMessage(List<Person> personList) {

        int numPeople = personList.size();
        return String.format(
                "Current number of people in address book : %1$s!", numPeople);
    }


}