package seedu.address.model.person;

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

    private Person loggedInPerson;
    boolean isAdminUser;

    public User(Person p) {
        loggedInPerson = p;
    }

    public static User getAdminUser() {
        User user = new User(null);
        user.isAdminUser = true;
        return user;
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
        if(isAdminUser) {
            return "Logged in person: Admin";
        } else {
            return "Logged in person: " + loggedInPerson.toString();
        }
    }
}
