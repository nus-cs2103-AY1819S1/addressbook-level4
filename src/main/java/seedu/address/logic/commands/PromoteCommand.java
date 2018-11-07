package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Education;
import seedu.address.model.person.Person;
import seedu.address.model.person.Time;
import seedu.address.model.tag.Tag;

/**
 * Promotes the students in TutorPal to the next educational grade and/or level.
 */
public abstract class PromoteCommand extends Command {

    public static final String COMMAND_WORD = "promote";

    public static final String MESSAGE_SUCCESS = "%d student(s) have been promoted! \n";

    public static final String MESSAGE_GRADUATED_STUDENTS = "The following student(s) have newly graduated from their "
            + "educational level and have been assigned the \"Graduated\" tag :\n"
            + "%s\n"
            + "Please delete the graduated students using command: delete INDEX,\n"
            + " Or use command: edit to update their educational level (Primary/Secondary/JC).";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Promotes the students in TutorPal "
            + "to the next educational level and/or grade.\n"
            + "Enter \"promote all\" to promote every student in the TutorPal, or\n"
            + "Manually by the student's index shown in the list separated by spaces for multiple selections.\n"
            + "Example: promote 1 2 7 12 ";

    protected ArrayList<String> newGraduatedStudents = new ArrayList<>();

    /**
     * Creates a new Person object to copy with the updated educational level and grade.
     */
    public Person createPromotedPerson(Person toCopy) {
        requireNonNull(toCopy);
        Education currentEducation = toCopy.getEducation();
        Education newEducation = new Education(currentEducation.getPromotedEducation());
        Person promotedPerson = new Person(toCopy.getName(), toCopy.getPhone(), toCopy.getEmail(),
                toCopy.getAddress(), newEducation, toCopy.getGrades(), toCopy.getTags());

        for (Time time : toCopy.getTime()) {
            promotedPerson.addTime(time);
        }

        if (!toCopy.hasGraduated() && toCopy.equals(promotedPerson)) {
            newGraduatedStudents.add(promotedPerson.getName().toString());
            promotedPerson.addTag(new Tag("Graduated"));
        }

        return promotedPerson;
    }

    public int getNumberOfGraduatedStudents(List<Person> personsList) {
        int numberOfGraduatedStudents = 0;
        for (Person student : personsList) {
            if (student.hasGraduated()) {
                numberOfGraduatedStudents++;
            }
        }
        return numberOfGraduatedStudents;
    }

    @Override
    public abstract CommandResult execute(Model model, CommandHistory history) throws CommandException;
}
