package seedu.address.testutil;

import seedu.address.model.Ssenisub;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building ssenisub objects.
 * Example usage: <br>
 *     {@code ssenisub ab = new SsenisubBuilder().withPerson("John", "Doe").build();}
 */
public class SsenisubBuilder {

    private Ssenisub ssenisub;

    public SsenisubBuilder() {
        ssenisub = new Ssenisub();
    }

    public SsenisubBuilder(Ssenisub ssenisub) {
        this.ssenisub = ssenisub;
    }

    /**
     * Adds a new {@code Person} to the {@code ssenisub} that we are building.
     */
    public SsenisubBuilder withPerson(Person person) {
        ssenisub.addPerson(person);
        return this;
    }

    public Ssenisub build() {
        return ssenisub;
    }
}
