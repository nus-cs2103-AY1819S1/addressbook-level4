package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.permission.PermissionSet;
import seedu.address.model.project.Project;

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
    private final Salary salary;
    private final Address address;
    private final Set<Project> projects = new HashSet<>();
    private final Optional<ProfilePic> profilePic;
    private final PermissionSet permissionSet = new PermissionSet();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Salary salary, Set<Project> projects) {
        requireAllNonNull(name, phone, email, address, salary, projects);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.projects.addAll(projects);
        this.profilePic = Optional.empty();
    }

    /**
     * Overriden constructor that allows specification of a profile picture
     */
    public Person(Name name, Phone phone, Email email, Address address, Salary salary, Set<Project> projects,
                  Optional<ProfilePic> profilePic) {
        requireAllNonNull(name, phone, email, address, salary, projects);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.projects.addAll(projects);
        this.profilePic = profilePic;
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

    public Salary getSalary() {
        return salary;
    }

    public Optional<ProfilePic> getProfilePic() {
        return profilePic;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Project> getProjects() {
        return Collections.unmodifiableSet(projects);
    }


    /**
     * Returns a PermissionSet, which contains all permissions possessed by this person.
     * @see PermissionSet
     */
    public PermissionSet getPermissionSet() {
        return permissionSet;
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
                && otherPerson.getSalary().equals(getSalary())
                && otherPerson.getProjects().equals(getProjects())
                && otherPerson.getProfilePic().equals(getProfilePic());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, salary, projects, profilePic);
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
                .append(" Salary: ")
                .append(getSalary())
                .append(" Profile Pic: ")
                .append(getProfilePic().orElse(new ProfilePic("[no pic]")))
                .append(" Projects: ");
        getProjects().forEach(builder::append);
        return builder.toString();
    }

}
