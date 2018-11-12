package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SALARY = "10000";
    public static final String DEFAULT_USERNAME = "Alice Pauline";
    public static final String DEFAULT_PASSWORD = "Pa55w0rd";

    private Name name;
    private Phone phone;
    private Email email;
    private Salary salary;
    private Address address;
    private Username username;
    private Password password;
    private Set<Project> projects;
    private PermissionSet pSet;
    private List<LeaveApplication> leaveApplications;

    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        salary = new Salary(DEFAULT_SALARY);
        address = new Address(DEFAULT_ADDRESS);
        username = new Username(DEFAULT_USERNAME);
        password = new Password(DEFAULT_PASSWORD);
        projects = new HashSet<>();
        pSet = new PermissionSet();
        leaveApplications = new ArrayList<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        salary = personToCopy.getSalary();
        address = personToCopy.getAddress();
        username = personToCopy.getUsername();
        password = personToCopy.getPassword();
        projects = new HashSet<>(personToCopy.getProjects());
        pSet = personToCopy.getPermissionSet();
        leaveApplications = personToCopy.getLeaveApplications();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code projects} into a {@code Set<Project>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withProjects(String ... projects) {
        this.projects = SampleDataUtil.getProjectSet(projects);
        return this;
    }

    /**
     * Parses the {@code projects} into a {@code Set<Project>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withProjects(Set<Project> projects) {
        this.projects = projects;
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code Person} that we are building.
     */
    public PersonBuilder withSalary(String salary) {
        this.salary = new Salary(salary);
        return this;

    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Username} of the {@code Person} that we are building.
     */
    public PersonBuilder withUsername(String username) {
        this.username = new Username(username);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Person} that we are building.
     */
    public PersonBuilder withPassword(String password) {
        this.password = new Password(password);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Person} that we are building, along with it's salt.
     */
    public PersonBuilder withPassword(String password, String salt) {
        this.password = new Password(password, salt);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Person} that we are building, using a specific
     * Password object
     */
    public PersonBuilder withPassword(Password p) {
        if (p.plaintext != null) {
            String salt = Base64.getEncoder().encodeToString(p.salt);
            this.password = new Password(p.plaintext, salt);
        } else {
            this.password = new Password(p.salt, p.hash);
        }
        return this;
    }

    /**
     * Sets the {@code PermissionSet} of the {@code Person} that we are building.
     */
    public PersonBuilder withPermissionSet(PermissionSet pSet) {
        this.pSet = pSet;
        return this;
    }

    /**
     * Sets the {@code List<LeaveApplication>} of the {@code Person} that we are building.
     */
    public PersonBuilder withLeaveApplications(List<LeaveApplication> leaveApplications) {
        this.leaveApplications = new ArrayList<>(leaveApplications);
        return this;
    }

    /**
     * Build the person object.
     */
    public Person build() {
        return new Person(name, phone, email, address, salary, username, password, projects, pSet, leaveApplications,
                Optional.empty());
    }

}
