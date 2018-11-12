package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    private final Email email;


    // Data fields
    private final Address address;
    private final Education education;
    private final Fees tuitionFee;
    private final HashMap<String, Grades> grades;
    private final ArrayList<Time> timings;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Education education,
                  HashMap<String, Grades> grades, ArrayList<Time> timings, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, education, grades, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.education = education;
        this.grades = grades;
        this.timings = timings;
        this.tags.addAll(tags);
        this.tuitionFee = new Fees(education);
    }

    public Name getName() {
        return name;
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

    public Education getEducation() {
        return education;
    }

    public HashMap<String, Grades> getGrades() {
        return grades;
    }

    public Fees getFees() {
        return tuitionFee;
    }

    public ArrayList<Time> getTime() {
        return timings;
    }

    /**
     * Adds a time slot to a Person's time array list
     */
    public void addTime(Time time) {
        timings.add(time);
    }

    /**
     * Removes a time slot from a Person's time array list
     */
    public void deleteTime(Time time) {
        timings.remove(time);
    }

    /**
     * Adds a new tag to Person's tags
     */
    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }

    /**
     * Returns true if Person contains a "Graduated" Tag which indicates that student
     * has graduated from his/her educational Level.
     */
    public boolean hasGraduated() {
        return tags.contains(new Tag("Graduated"));
    }

    /**
     * Removes the "Graduated" Tag from the student.
     */
    public boolean removeGraduatedTag() {
        return tags.remove(new Tag("Graduated"));
    }

    /**
     * delete a grade record from the grades HashMap
     */
    public void deleteGrade(String examName) {
        grades.remove(examName);
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
                && otherPerson.getEducation().equals(getEducation())
                && otherPerson.getGrades().equals(getGrades())
                && otherPerson.getTags().equals(getTags())
                && otherPerson.getTime().equals(getTime());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, education, grades, tags, timings);
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
                .append(" Education: ")
                .append(getEducation());
        getGrades().forEach((key, value) -> builder.append(" " + key + " " + value + " "));
        builder.append(" Tuition Timings: ");
        getTime().forEach(time -> builder.append(time + " "));
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
