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
        assertFalse(personPSet.addPermission(null));

        //Value from Permission enum -> true
        assertTrue(personPSet.addPermission(Permission.ASSIGN_PERMISSION));
        assertTrue(personPSet.addPermission(Permission.ADD_EMPLOYEE));
        assertTrue(personPSet.addPermission(Permission.APPROVE_LEAVE));
        assertTrue(personPSet.addPermission(Permission.EDIT_EMPLOYEE));

        //Duplicate Permission value -> false
        assertFalse(personPSet.addPermission(Permission.EDIT_EMPLOYEE));
        assertFalse(personPSet.addPermission(Permission.APPROVE_LEAVE));
    }

    @Test
    public void removePermission() {
        Person newPerson = new PersonBuilder().build();
        PermissionSet personPSet = newPerson.getPermissionSet();
        assertTrue(personPSet.addPermission(Permission.ASSIGN_PERMISSION));
        assertTrue(personPSet.addPermission(Permission.ADD_EMPLOYEE));
        assertTrue(personPSet.addPermission(Permission.APPROVE_LEAVE));
        assertTrue(personPSet.addPermission(Permission.EDIT_EMPLOYEE));

        //null -> false
        assertFalse(personPSet.removePermission(null));

        //allocated permission -> true
        assertTrue(personPSet.removePermission(Permission.EDIT_EMPLOYEE));
        assertTrue(personPSet.removePermission(Permission.APPROVE_LEAVE));

        //Removed permission -> false
        assertFalse(personPSet.removePermission(Permission.EDIT_EMPLOYEE));
        assertFalse(personPSet.removePermission(Permission.APPROVE_LEAVE));

        //not allocated permission -> false
        assertFalse(personPSet.removePermission(Permission.VIEW_PROJECT));
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
        assertTrue(personPSet.contains(Permission.ADD_EMPLOYEE));
        assertTrue(personPSet.contains(Permission.REMOVE_EMPLOYEE));
        assertTrue(personPSet.contains(Permission.EDIT_EMPLOYEE));
        assertTrue(personPSet.contains(Permission.VIEW_EMPLOYEE_LEAVE));
        assertTrue(personPSet.contains(Permission.APPROVE_LEAVE));
        assertTrue(personPSet.contains(Permission.CREATE_PROJECT));
        assertTrue(personPSet.contains(Permission.VIEW_PROJECT));
        assertTrue(personPSet.contains(Permission.ASSIGN_PROJECT));
        assertTrue(personPSet.contains(Permission.CREATE_DEPARTMENT));
        assertTrue(personPSet.contains(Permission.ASSIGN_DEPARTMENT));
        assertTrue(personPSet.contains(Permission.ASSIGN_PERMISSION));

        //Manager Preset
        assertTrue(personPSet.assignPresetPermission(PermissionSet.PresetPermission.MANAGER));
        //All permissions of Manager in Permission Set
        assertTrue(personPSet.contains(Permission.ADD_EMPLOYEE));
        assertTrue(personPSet.contains(Permission.REMOVE_EMPLOYEE));
        assertTrue(personPSet.contains(Permission.EDIT_EMPLOYEE));
        assertTrue(personPSet.contains(Permission.VIEW_EMPLOYEE_LEAVE));
        assertTrue(personPSet.contains(Permission.APPROVE_LEAVE));
        assertTrue(personPSet.contains(Permission.CREATE_PROJECT));
        assertTrue(personPSet.contains(Permission.VIEW_PROJECT));
        assertTrue(personPSet.contains(Permission.ASSIGN_PROJECT));
        assertTrue(personPSet.contains(Permission.CREATE_DEPARTMENT));
        assertTrue(personPSet.contains(Permission.ASSIGN_DEPARTMENT));

        //Employee Preset
        assertTrue(personPSet.assignPresetPermission(PermissionSet.PresetPermission.MANAGER));
        //All permissions of Employee in Permission Set
        assertTrue(personPSet.contains(Permission.VIEW_PROJECT));
    }

}
