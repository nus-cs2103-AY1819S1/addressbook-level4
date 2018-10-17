package seedu.address.model.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PermissionSetTest {
    @Test
    public void addPermission() {
        PermissionSet permissionSet = new PermissionSet();
        //null -> false
        assertFalse(permissionSet.addPermissions((Permission[]) null));

        //null mixed with value from permission enum -> false
        assertFalse(permissionSet.addPermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                null
        ));

        //Value from Permission enum -> true
        assertTrue(permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        ));

        //Duplicate Permission value -> false
        assertFalse(permissionSet.addPermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE
        ));
    }

    @Test
    public void removePermission() {
        PermissionSet permissionSet = new PermissionSet();
        permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        );

        //null -> false
        assertFalse(permissionSet.removePermissions((Permission[]) null));

        //null mixed with value from permission enum -> false
        assertFalse(permissionSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                null
        ));

        //allocated permission -> true
        assertTrue(permissionSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE
        ));

        //Removed permission -> false
        assertFalse(permissionSet.removePermissions(Permission.EDIT_EMPLOYEE));
        assertFalse(permissionSet.removePermissions(Permission.APPROVE_LEAVE));
        assertFalse(permissionSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE
        ));

        //not allocated permission -> false
        assertFalse(permissionSet.removePermissions(Permission.VIEW_PROJECT));
    }

    @Test
    public void assignPresetPermission() {
        PermissionSet permissionSet = new PermissionSet();

        //null -> false
        assertFalse(permissionSet.assignPresetPermission(null));

        //Values from enum PermissionSet.PresetPermission -> true
        //Admin preset
        assertTrue(permissionSet.assignPresetPermission(PermissionSet.PresetPermission.ADMIN));
        //All permissions of admin in Permission Set
        assertTrue(permissionSet.containsAll(
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
        assertTrue(permissionSet.assignPresetPermission(PermissionSet.PresetPermission.MANAGER));
        //All permissions of Manager in Permission Set
        assertTrue(permissionSet.containsAll(
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
        assertTrue(permissionSet.assignPresetPermission(PermissionSet.PresetPermission.EMPLOYEE));
        //All permissions of Employee in Permission Set
        assertTrue(permissionSet.containsAll(Permission.VIEW_PROJECT));
    }

    @Test
    public void getGrantedPermission() {
        PermissionSet permissionSet = new PermissionSet();
        permissionSet.addPermissions(Permission.ADD_EMPLOYEE, Permission.REMOVE_EMPLOYEE);

        Set<Permission> readOnlyPermissionSet = permissionSet.getGrantedPermission();
        assertTrue(readOnlyPermissionSet.contains(Permission.ADD_EMPLOYEE));
        assertTrue(readOnlyPermissionSet.contains(Permission.REMOVE_EMPLOYEE));

        Assert.assertThrows(UnsupportedOperationException.class, () ->
            readOnlyPermissionSet.add(Permission.EDIT_EMPLOYEE));
    }

    @Test
    public void containsAll() {
        PermissionSet testPermissionSet = new PermissionSet();
        testPermissionSet.addPermissions(Permission.ADD_EMPLOYEE, Permission.REMOVE_EMPLOYEE);

        //null -> false
        assertFalse(testPermissionSet.containsAll((Permission[]) null));
        //null with an existing permission -> false
        assertFalse(testPermissionSet.containsAll(null, Permission.ADD_EMPLOYEE));
        //list of not allocated permission -> false
        assertFalse(testPermissionSet.containsAll(Permission.EDIT_EMPLOYEE, Permission.ASSIGN_DEPARTMENT));
        //Existing permission -> true
        assertTrue(testPermissionSet.containsAll(Permission.REMOVE_EMPLOYEE));
        assertTrue(testPermissionSet.containsAll(Permission.REMOVE_EMPLOYEE, Permission.ADD_EMPLOYEE));
    }
}
