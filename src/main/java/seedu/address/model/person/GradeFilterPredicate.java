package seedu.address.model.person;

import java.util.Map;
import java.util.function.Predicate;

/**
 * GradeFilterPredicate
 */
public class GradeFilterPredicate implements Predicate<Person> {

    private double minLimit;
    private double maxLimit;

    public GradeFilterPredicate(double minLimit, double maxLimit) {
        if (minLimit > maxLimit) {
            Double temp = minLimit;
            minLimit = maxLimit;
            maxLimit = temp;
        }
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }


    @Override
    public boolean test(Person person) {
        boolean result = false;
        for (Map.Entry<String, Grades> grade : person.getGrades().entrySet()) {
            int a = Integer.valueOf(grade.getValue().value);
            if (a >= minLimit && a <= maxLimit) {
                result = true;
                break;
            }
        }
        return result;
    }


}
