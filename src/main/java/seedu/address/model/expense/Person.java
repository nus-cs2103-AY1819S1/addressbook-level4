package seedu.address.model.expense;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Date date;

    // Data fields
    private final Cost cost;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Cost cost, Date date, Set<Tag> tags) {
        requireAllNonNull(name, phone, cost, date, tags);
        this.name = name;
        this.phone = phone;
        this.cost = cost;
        this.date = date;
        this.tags.addAll(tags);
    }

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Cost cost, Set<Tag> tags) {
        this(name, phone, cost, new Date(), tags);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Cost getCost() {
        return cost;
    }

    public Date getDate() {
        return date;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {

        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(this.getName())
                && (otherPerson.getPhone().equals(this.getPhone()) || otherPerson.getCost().equals(this.getCost()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getCost().equals(getCost())
                && otherPerson.getDate().equals(getDate())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, cost, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Cost: ")
                .append(getCost())
                .append(" Date: ")
                .append(getDate())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
