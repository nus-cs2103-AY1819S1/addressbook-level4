package seedu.address.model.person;

import java.util.function.Predicate;

public class EducationFilterPredicate implements Predicate<Person> {

    private String education;

    public EducationFilterPredicate(String education) {
        this.education = education;
    }


    @Override
    public boolean test(Person person) {
        String a = person.getEducation().educationalLevel.toString();
        return a.equalsIgnoreCase(education);

    }

}
