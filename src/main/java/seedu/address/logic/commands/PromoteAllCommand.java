package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Promotes all students in TutorPal to their next educational grade. If a student has already graduated,
 * no changes will be made to the student. If a student has just graduated from his/her current educational
 * level, a "Graduated" Tag will be assigned to the student.
 */
public class PromoteAllCommand extends PromoteCommand {

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        List<Person> internalList = model.getInternalList();

        int numberOfStudentsPromoted = internalList.size() - getNumberOfGraduatedStudents(internalList);

        for (int i = 0; i < internalList.size(); i++) {
            Person personToPromote = internalList.get(i);
            Person promotedPerson = createPromotedPerson(personToPromote);

            model.updatePerson(personToPromote, promotedPerson);
        }

        StringBuilder graduatedStudentsList = new StringBuilder();
        for (String student : newGraduatedStudents) {
            graduatedStudentsList.append(student).append(" ");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS + MESSAGE_GRADUATED_STUDENTS,
                numberOfStudentsPromoted, graduatedStudentsList));
    }
}
