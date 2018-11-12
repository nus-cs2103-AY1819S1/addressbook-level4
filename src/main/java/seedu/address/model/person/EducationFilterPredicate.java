package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Predicate for a Person's education filter in the address book.
 */
public class EducationFilterPredicate implements Predicate<Person> {

    private String education;

    public EducationFilterPredicate(String education) {
        this.education = education;
    }


    @Override
    public boolean test(Person person) {
        String a = person.getEducation().getEducationalLevel().toString();
        return a.equalsIgnoreCase(education);
    }

}
