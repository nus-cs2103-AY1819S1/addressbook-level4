package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.leaveapplication.LeaveApplication;
import seedu.address.model.permission.PermissionSet;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Password;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePic;
import seedu.address.model.person.Salary;
import seedu.address.model.person.Username;
import seedu.address.model.project.Project;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String salary;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String username;

    @XmlElement(required = true)
    private XmlAdaptedPassword password;

    @XmlElement
    private String profilePic;
    @XmlElement
    private List<XmlAdaptedProject> project = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedPermission> permission = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedLeaveApplication> leaveApplications = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedPerson.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedPerson() {
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
                            String salary, String username, XmlAdaptedPassword password,
                            List<XmlAdaptedProject> project) {
        this(name, phone, email, address, salary, username, password, project, new ArrayList<>());
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address, String salary, String username,
                            XmlAdaptedPassword password, List<XmlAdaptedProject> project,
                            List<XmlAdaptedPermission> permission) {
        this(name, phone, email, address, salary, username, password, project, null, permission, new ArrayList<>());
    }

    /**
     * Constructs an {@code XmlAdaptedPerson} with the given person details.
     */
    public XmlAdaptedPerson(String name, String phone, String email, String address,
                            String salary, String username, XmlAdaptedPassword password,
                            List<XmlAdaptedProject> project, String profilePic,
                            List<XmlAdaptedPermission> permission,
                            List<XmlAdaptedLeaveApplication> leaveApplications) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.salary = salary;
        this.address = address;
        this.profilePic = profilePic;
        this.username = username;
        this.password = password;
        if (project != null) {
            this.project = new ArrayList<>(project);
        }
        if (permission != null) {
            this.permission = new ArrayList<>(permission);
        }
        if (leaveApplications != null) {
            this.leaveApplications = new ArrayList<>(leaveApplications);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedPerson
     */
    public XmlAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        salary = source.getSalary().value;
        address = source.getAddress().value;
        username = source.getUsername().username;
        password = new XmlAdaptedPassword(source.getPassword().salt, source.getPassword().hash);
        project = source.getProjects().stream()
                .map(XmlAdaptedProject::new)
                .collect(Collectors.toList());
        permission = source.getPermissionSet().getGrantedPermission().stream()
                .map(XmlAdaptedPermission::new)
                .collect(Collectors.toList());
        leaveApplications = source.getLeaveApplications().stream()
                .map(XmlAdaptedLeaveApplication::new)
                .collect(Collectors.toList());
        profilePic = source.getProfilePic().isPresent() ? source.getProfilePic().get().value : null;
    }

    /**
     * Converts this jaxb-friendly adapted person object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);
        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);
        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Salary.class.getSimpleName()));
        }
        if (!Salary.isValidSalary(salary)) {
            throw new IllegalValueException(Salary.SALARY_CONSTRAINTS);
        }
        final Salary modelSalary = new Salary(salary);
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Username.class.getSimpleName()));
        }
        if (!Username.isValidUsername(username)) {
            throw new IllegalValueException(Username.MESSAGE_USERNAME_CONSTRAINTS);
        }
        final Username modelUsername = new Username(username);
        if (password == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Password.class.getSimpleName()));
        }

        final Password modelPassword = password.toModelType();

        Optional<ProfilePic> modelProfilePic = Optional.empty();
        if (profilePic != null) {
            if (!ProfilePic.isValidPath(profilePic)) {
                throw new IllegalValueException(ProfilePic.MESSAGE_PROFILEPIC_CONSTRAINTS);
            }
            modelProfilePic = Optional.of(new ProfilePic(profilePic));
        }

        final List<Project> personProjects = new ArrayList<>();
        for (XmlAdaptedProject pro : project) {
            personProjects.add(pro.toModelType());
        }
        final Set<Project> modelProjects = new HashSet<>(personProjects);

        final PermissionSet pSet = new PermissionSet();
        for (XmlAdaptedPermission p : permission) {
            pSet.addPermissions(p.toModelType());
        }

        final List<LeaveApplication> modelLeaveApplications = new ArrayList<>();
        for (XmlAdaptedLeaveApplication leaveApplication : leaveApplications) {
            modelLeaveApplications.add(leaveApplication.toModelType());
        }

        return new Person(modelName, modelPhone, modelEmail, modelAddress, modelSalary, modelUsername,
                modelPassword, modelProjects, pSet, modelLeaveApplications, modelProfilePic);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedPerson)) {
            return false;
        }

        XmlAdaptedPerson otherPerson = (XmlAdaptedPerson) other;
        return Objects.equals(name, otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && Objects.equals(salary, otherPerson.salary)
                && Objects.equals(address, otherPerson.address)
                && project.equals(otherPerson.project)
                && Objects.equals(profilePic, otherPerson.profilePic)
                && Objects.equals(username, otherPerson.username)
                && Objects.equals(password, otherPerson.password)
                && Objects.equals(leaveApplications, otherPerson.leaveApplications);
    }
}
