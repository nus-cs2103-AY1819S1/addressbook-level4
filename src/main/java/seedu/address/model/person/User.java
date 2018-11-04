package seedu.address.model.person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.project.Project;

/**
 * User represents a currently logged in person
 */
public class User {
    public static final Username ADMIN_DEFAULT_USERNAME = new Username("Admin");
    public static final Password ADMIN_DEFUALT_PASSWORD = new Password("Pa55w0rd");
    public static final Name ADMIN_NAME = new Name("Admin");
    public static final Phone ADMIN_PHONE = new Phone("999");
    public static final Email ADMIN_EMAIL = new Email("admin@admin.com");
    public static final Address ADMIN_ADDRESS = new Address("Admin Address");
    public static final Salary ADMIN_SALARY = new Salary("0");
    public static final Set<Project> ADMIN_PROJECTS = new HashSet<>();
    public static final PermissionSet ADMIN_PERMISSIONS = new PermissionSet(PermissionSet.PresetPermission.ADMIN);
    public static final Optional<ProfilePic> ADMIN_PROFILEPIC = Optional.empty();
    public static final List<LeaveApplication> ADMIN_LEAVEAPPLICATIONS = new ArrayList<>();

    private static User adminUser;

    private Person loggedInPerson;
    private boolean isAdminUser;

    public User(Person p) {
        loggedInPerson = p;
    }

    /**
     * Builds the admin user in the system. The admin user will have the specified username and password
     * For default username and passwords, use {@code ADMIN_DEFAULT_USERNAME} and {@code ADMIN_DEFAULT_PASSWORD}
     * @param newUsername the username of the admin
     * @param newPassword The password  of the admin
     */
    public static void buildAdmin(Username newUsername, Password newPassword) {
        Person adminPerson = new Person(ADMIN_NAME, ADMIN_PHONE, ADMIN_EMAIL, ADMIN_ADDRESS, ADMIN_SALARY,
            newUsername, newPassword, ADMIN_PROJECTS, ADMIN_PERMISSIONS, ADMIN_LEAVEAPPLICATIONS, ADMIN_PROFILEPIC);
        adminUser = new User(adminPerson);
        adminUser.isAdminUser = true;
    }

    /**
     * Gets the admin user in the system
     * {@link #buildAdmin(Username, Password)} should have been called beforehand.
     * @return The admin user
     */
    public static User getAdminUser() {
        if (adminUser == null) {
            LogsCenter.getLogger(User.class).warning("Admin User get called without building first");
            buildAdmin(ADMIN_DEFAULT_USERNAME, ADMIN_DEFUALT_PASSWORD);
        }
        return adminUser;
    }

    /**
     * Checks if the username and password combination is valid to allow a login as an admin
     * @param username The username input
     * @param password The password input
     * @return true if the username and password is valid to login as an admin, false otherwise.
     */
    public static boolean matchesAdminLogin(String username, String password) {
        User adminUser = getAdminUser();
        return adminUser.getUsername().username.equals(username)
            && adminUser.getPassword().matches(password);
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
