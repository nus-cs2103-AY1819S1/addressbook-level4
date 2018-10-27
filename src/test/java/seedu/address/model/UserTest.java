package seedu.address.model;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.testutil.TypicalPersons;

public class UserTest {

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
        Person p = User.ADMIN;
        User u = User.getAdminUser();
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

        assert u.isAdminUser();

        assert u.equals(User.getAdminUser());
    }
}
