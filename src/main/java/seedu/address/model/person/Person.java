package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, but details other than name are optional, field values are
 * validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Optional<Phone> phone;
    private final Optional<Email> email;

    // Data fields
    private final Optional<Address> address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Optional<Phone> phone, Optional<Email> email, Optional<Address> address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return phone;
    }

    public Optional<Email> getEmail() {
        return email;
    }

    public Optional<Address> getAddress() {
        return address;
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

        // The other person must exist and have the same name to be the same person
        if (otherPerson == null || !otherPerson.getName().equals(getName())) {
            return false;
        }

        boolean bothHavePhone = getPhone().isPresent() && otherPerson.getPhone().isPresent();
        boolean bothHaveEmail = getEmail().isPresent() && otherPerson.getEmail().isPresent();

        // Do not compare fields unless they are present
        return ((bothHavePhone && otherPerson.getPhone().equals(getPhone()))
                || (bothHaveEmail && otherPerson.getEmail().equals(getEmail())));
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
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ");
        getPhone().ifPresentOrElse(builder::append, () -> builder.append("None"));
        builder.append(" Email: ");
        getEmail().ifPresentOrElse(builder::append, () -> builder.append("None"));
        builder.append(" Address: ");
        getAddress().ifPresentOrElse(builder::append, () -> builder.append("None"));
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
