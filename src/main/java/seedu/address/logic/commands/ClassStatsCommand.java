package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.calculation.Quartiles;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.assignment.Assignment;
import seedu.address.model.person.Person;

/**
 * Display the statistics for the overall grades of students.
 */
public class ClassStatsCommand extends Command {

    public static final String COMMAND_WORD = "classStats";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Display the statistics for the overall grades of students.";

    public static final String MESSAGE_SUCCESS = "Class Statistics\nStudents: %d, Maximum Grade: %.1f\n"
            + "Highest: %.1f, Lowest: %.1f\n"
            + "25th: %.1f, 75th: %.1f\n"
            + "Average: %.1f, Median: %.1f";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Assignment> filteredAssignmentList = model.getFilteredAssignmentList();
        List<Person> filteredPersonList = model.getFilteredPersonList();

        double totalWeight = filteredAssignmentList.stream()
            .mapToDouble(assignment -> assignment.getWeight().getValue()).sum();
        Map<String, Assignment> assignmentMap = filteredAssignmentList.stream()
                                                    .collect(Collectors.toMap(
                                                        Assignment::getUniqueId,
                                                        assignment -> assignment));


        int studentsCount = filteredPersonList.size();
        double[] marks = new double[studentsCount];
        double overallMark;
        DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();
        Person person;
        //Calculate overallMark for each person
        for (int i = 0; i < studentsCount; i++) {
            person = filteredPersonList.get(i);
            overallMark = person.getMarks().entrySet().stream()
                                            .filter(entry -> assignmentMap.containsKey(entry.getKey()))
                                            .mapToDouble(entry -> {
                                                Assignment assignment = assignmentMap.get(entry.getKey());
                                                double mark = entry.getValue().getValue();
                                                mark /= assignment.getMaxMark().getValue();
                                                mark *= assignment.getWeight().getValue();
                                                return mark;
                                            })
                                            .sum();
            summaryStatistics.accept(overallMark);
            marks[i] = overallMark;
        }

        double[] quartiles = new double[3];
        if (studentsCount > 0) {

            Arrays.sort(marks);

            quartiles[0] = Quartiles.FIRST_QUARTILE;
            quartiles[1] = Quartiles.SECOND_QUARTILE;
            quartiles[2] = Quartiles.THIRD_QUARTILE;
            Quartiles.calculateQuartiles(quartiles, marks);
        } else {
            summaryStatistics.accept(0);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, studentsCount, totalWeight,
            summaryStatistics.getMax(), summaryStatistics.getMin(),
            quartiles[0], quartiles[2], summaryStatistics.getAverage(), quartiles[1]));
    }
}
