package seedu.address.model.person;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.project.Project;

/**
 * User represents a currently logged in person
 */
public class User {
    public static final Username ADMIN_USERNAME = new Username("Admin");
    public static final Password ADMIN_PASSWORD = new Password("Pa55w0rd");
    public static final Person ADMIN = new Person(new Name("Admin"), new Phone("999"),
        new Email("admin@admin.com"), new Address("Admin Address"), new Salary("0"), ADMIN_USERNAME,
        ADMIN_PASSWORD, new HashSet<>(), new PermissionSet(PermissionSet.PresetPermission.ADMIN));
    private static User adminUser;

    private Person loggedInPerson;
    private boolean isAdminUser;

    public User(Person p) {
        loggedInPerson = p;
    }

    public static User getAdminUser() {
        if (adminUser == null) {
            adminUser = new User(ADMIN);
            adminUser.isAdminUser = true;
        }
        return adminUser;
    }

    public Person getPerson() {
        return loggedInPerson;
    }

    public Name getName() {
        return loggedInPerson.getName();
    }

    public Phone getPhone() {
        return loggedInPerson.getPhone();
    }

    public Email getEmail() {
        return loggedInPerson.getEmail();
    }

    public Address getAddress() {
        return loggedInPerson.getAddress();
    }

    public Salary getSalary() {
        return loggedInPerson.getSalary();
    }

    public Optional<ProfilePic> getProfilePic() {
        return loggedInPerson.getProfilePic();
    }

    /**
     * Returns an immutable project set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Project> getProjects() {
        return loggedInPerson.getProjects();
    }


    /**
     * Returns a PermissionSet, which contains all permissions possessed by this person.
     */
    public PermissionSet getPermissionSet() {
        return loggedInPerson.getPermissionSet();
    }

    public Username getUsername() {
        return loggedInPerson.getUsername();
    }

    public Password getPassword() {
        return loggedInPerson.getPassword();
    }

    /**
     * Returns an immutable leave applications list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<LeaveApplication> getLeaveApplications() {
        return loggedInPerson.getLeaveApplications();
    }

    /**
     * Gets whether this user is an admin user.
     * An admin user is not an actual user in the system, but is a fake user to allow for logins even
     * when there are no employees in the system.
     */
    public boolean isAdminUser() {
        return isAdminUser;
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

        if (!(other instanceof User)) {
            return false;
        }

        User otherUser = (User) other;
        return loggedInPerson.equals(otherUser.loggedInPerson);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(loggedInPerson);
    }

    @Override
    public String toString() {
        if (isAdminUser) {
            return "Logged in person: Admin";
        } else {
            return "Logged in person: " + loggedInPerson.toString();
        }
    }
}
