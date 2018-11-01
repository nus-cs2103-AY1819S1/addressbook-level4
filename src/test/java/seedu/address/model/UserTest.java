package seedu.address.model;

import org.junit.Before;
import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.TypicalPersons;

public class UserTest {

    @Before
    public void setUp() {
        User.buildAdmin(User.ADMIN_DEFAULT_USERNAME, User.ADMIN_DEFUALT_PASSWORD);
    }

    @Test
    public void assert_person_parameters() {
        Person p = TypicalPersons.AMY;
        User u = new User(p);
        assert u.getPerson() == p;
        assert u.getName().equals(p.getName());
        assert u.getPhone().equals(p.getPhone());
        assert u.getEmail().equals(p.getEmail());
        assert u.getAddress().equals(p.getAddress());
        assert u.getSalary().equals(p.getSalary());
        assert u.getProfilePic().equals(p.getProfilePic());
        assert u.getProjects().equals(p.getProjects());
        assert u.getLeaveApplications().equals(p.getLeaveApplications());
        assert u.getPermissionSet().equals(p.getPermissionSet());
        assert u.getUsername().equals(p.getUsername());
        assert u.getPassword().equals(p.getPassword());

        assert !u.isAdminUser();

        assert !u.equals(User.getAdminUser());
        assert u.toString().contains(p.toString());
    }

    @Test
    public void assert_admin_parameters() {
        User u = User.getAdminUser();
        assert u.getName().equals(User.ADMIN_NAME);
        assert u.getPhone().equals(User.ADMIN_PHONE);
        assert u.getEmail().equals(User.ADMIN_EMAIL);
        assert u.getAddress().equals(User.ADMIN_ADDRESS);
        assert u.getSalary().equals(User.ADMIN_SALARY);
        assert u.getProfilePic().equals(User.ADMIN_PROFILEPIC);
        assert u.getProjects().equals(User.ADMIN_PROJECTS);
        assert u.getLeaveApplications().equals(User.ADMIN_LEAVEAPPLICATIONS);
        assert u.getPermissionSet().toString().equals(User.ADMIN_PERMISSIONS.toString());
        assert u.getUsername().equals(User.ADMIN_DEFAULT_USERNAME);
        assert u.getPassword().equals(User.ADMIN_DEFUALT_PASSWORD);

        assert u.isAdminUser();

        assert u.equals(User.getAdminUser());
    }
}
