package seedu.address.model.person;

import java.util.function.Predicate;

public class GradeFilterPredicate implements Predicate<Person> {

    private double minLimit;
    private double maxLimit;

    public GradeFilterPredicate(double minLimit,double maxLimit) {
        this.minLimit = minLimit;
        this.maxLimit = maxLimit;
    }


    @Override
    public boolean test(Person person) {
        int a = Integer.valueOf(person.getGrades().value);
        return a >= minLimit && a<= maxLimit;

    }


}
