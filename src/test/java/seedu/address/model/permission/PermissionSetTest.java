package seedu.address.model.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PermissionSetTest {
    @Test
    public void addPermission() {
        Person newPerson = new PersonBuilder().build();
        PermissionSet personPSet = newPerson.getPermissionSet();
        //null -> false
        assertFalse(personPSet.addPermissions(null));

        //null mixed with value from permission enum -> false
        assertFalse(personPSet.addPermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                null
        ));

        //Value from Permission enum -> true
        assertTrue(personPSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        ));

        //Duplicate Permission value -> false
        assertFalse(personPSet.addPermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE
        ));
    }

    @Test
    public void removePermission() {
        Person newPerson = new PersonBuilder().build();
        PermissionSet personPSet = newPerson.getPermissionSet();
        personPSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        );

        //null -> false
        assertFalse(personPSet.removePermissions(null));

        //null mixed with value from permission enum -> false
        assertFalse(personPSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                null
        ));

        //allocated permission -> true
        assertTrue(personPSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE
        ));

        //Removed permission -> false
        assertFalse(personPSet.removePermissions(Permission.EDIT_EMPLOYEE));
        assertFalse(personPSet.removePermissions(Permission.APPROVE_LEAVE));
        assertFalse(personPSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE
        ));

        //not allocated permission -> false
        assertFalse(personPSet.removePermissions(Permission.VIEW_PROJECT));
    }

    @Test
    public void assignPresetPermission() {
        Person newPerson = new PersonBuilder().build();
        PermissionSet personPSet = newPerson.getPermissionSet();

        //null -> false
        assertFalse(personPSet.assignPresetPermission(null));

        //Values from enum PermissionSet.PresetPermission -> true
        //Admin preset
        assertTrue(personPSet.assignPresetPermission(PermissionSet.PresetPermission.ADMIN));
        //All permissions of admin in Permission Set
        assertTrue(personPSet.containsAll(
                Permission.ADD_EMPLOYEE,
                Permission.REMOVE_EMPLOYEE,
                Permission.EDIT_EMPLOYEE,
                Permission.VIEW_EMPLOYEE_LEAVE,
                Permission.APPROVE_LEAVE,
                Permission.CREATE_PROJECT,
                Permission.VIEW_PROJECT,
                Permission.ASSIGN_PROJECT,
                Permission.CREATE_DEPARTMENT,
                Permission.ASSIGN_DEPARTMENT,
                Permission.ASSIGN_PERMISSION
        ));

        //Manager Preset
        assertTrue(personPSet.assignPresetPermission(PermissionSet.PresetPermission.MANAGER));
        //All permissions of Manager in Permission Set
        assertTrue(personPSet.containsAll(
                Permission.ADD_EMPLOYEE,
                Permission.REMOVE_EMPLOYEE,
                Permission.EDIT_EMPLOYEE,
                Permission.VIEW_EMPLOYEE_LEAVE,
                Permission.APPROVE_LEAVE,
                Permission.CREATE_PROJECT,
                Permission.VIEW_PROJECT,
                Permission.ASSIGN_PROJECT,
                Permission.CREATE_DEPARTMENT,
                Permission.ASSIGN_DEPARTMENT
        ));

        //Employee Preset
        assertTrue(personPSet.assignPresetPermission(PermissionSet.PresetPermission.MANAGER));
        //All permissions of Employee in Permission Set
        assertTrue(personPSet.containsAll(Permission.VIEW_PROJECT));
    }

}
