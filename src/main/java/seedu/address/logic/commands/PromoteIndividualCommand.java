package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Promotes an individual student to the next educational grade.
 */
public class PromoteIndividualCommand extends PromoteCommand {

    private ArrayList<Index> indexesToPromote = new ArrayList<>();

    public PromoteIndividualCommand(String userInput) {
        for (String index : userInput.split("\\s+")) {
            indexesToPromote.add(Index.fromOneBased(Integer.valueOf(index)));
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        int numberOfStudentsPromoted = indexesToPromote.size();

        for (Index i : indexesToPromote) {
            if (i.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person personToPromote = lastShownList.get(i.getZeroBased());
            Person promotedPerson = createPromotedPerson(personToPromote);

            if (personToPromote.hasGraduated()) {
                numberOfStudentsPromoted--;
            }

            model.updatePerson(personToPromote, promotedPerson);
        }

        StringBuilder graduatedStudentsList = new StringBuilder();
        for (String student : newGraduatedStudents) {
            graduatedStudentsList.append(student).append(" ");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS + MESSAGE_GRADUATED_STUDENTS,
                numberOfStudentsPromoted, graduatedStudentsList.toString()));
    }
}
