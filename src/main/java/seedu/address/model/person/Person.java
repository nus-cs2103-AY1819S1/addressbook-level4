package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private UniqueOccasionList occasionList;
    private UniqueModuleList moduleList;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        occasionList  = new  UniqueOccasionList();
        moduleList = new UniqueModuleList();
    }

    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  List<Occasion> occasionList, List<Module> moduleList) {
        requireAllNonNull(name, phone, email, address, tags, occasionList, moduleList);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.occasionList = new UniqueOccasionList(occasionList);
        this.moduleList = new UniqueModuleList(moduleList);
    }

    /**
     * Create a person from a personDescriptor
     * @param personDescriptor
     */
    public Person(PersonDescriptor personDescriptor) {
        requireNonNull(personDescriptor);
        this.name = personDescriptor.getName().orElse(new Name());
        this.phone = personDescriptor.getPhone().orElse(new Phone());
        this.email = personDescriptor.getEmail().orElse(new Email());
        this.address = personDescriptor.getAddress().orElse(new Address());
        this.tags.addAll(personDescriptor.getTags().orElse(new HashSet<Tag>()));
    }


    public Name getName() {
        return name;
    }

    public Property fullNameProperty() {
        return new SimpleStringProperty(name.fullName);
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
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
     * Returns the occasions this person is involved in.
     */
    public UniqueOccasionList getOccasionList() {
        return occasionList;
    }

    /**
     * Returns the modules this person is involved in.
     */
    public UniqueModuleList getModuleList() {
        return moduleList;
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
                && otherPerson.getName().equals(getName())
                && (otherPerson.getPhone().equals(getPhone()) || otherPerson.getEmail().equals(getEmail()));
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
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
