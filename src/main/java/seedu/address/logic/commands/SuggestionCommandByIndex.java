package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.person.Grades;
import seedu.address.model.person.Person;


/**
 * Given suggestions to students.
 */
public class SuggestionCommandByIndex extends Command {
    public static final String COMMAND_WORD = "suggestionByIndex";

    public static final String MESSAGE_SUCCESS = "Suggestion provided as following";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Student index(base 1)";
    private int indexNum;

    /**
     * filter by grade command
     *
     * @param args
     */
    public SuggestionCommandByIndex(String args) {
        indexNum = Integer.parseInt(args);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        if (model.getFilteredPersonList().size() < indexNum) {
            return new CommandResult("The index is out of the boundary");
        }

        Person targetPerson = model.getFilteredPersonList().get(indexNum - 1);

        double averageGrade;
        double sumGrade = 0;

        if (targetPerson.getGrades().isEmpty()) {
            return new CommandResult("This student have not taken any exams yet");
        }
        for (Grades grade:targetPerson.getGrades().values()) {
            int i = (int) Integer.parseInt(grade.value);
            sumGrade += i;
        }
        averageGrade = sumGrade / targetPerson.getGrades().size();
        if (averageGrade <= 60) {
            return new CommandResult("Suggestions: " + targetPerson.getName().fullName
                    + " should take more classes "
                    + "or study harder !!");
        }
        if (averageGrade >= 90) {
            return new CommandResult("Suggestions: " + targetPerson.getName().fullName
                    + " should take less classes "
                    + "to save money !!");
        }

        return new CommandResult("Suggestions: " + targetPerson.getName().fullName
                + " is really normal"
                + " so keep fighting !!");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SuggestionCommandByIndex
                && ((SuggestionCommandByIndex) other).indexNum == indexNum);
    }
}
