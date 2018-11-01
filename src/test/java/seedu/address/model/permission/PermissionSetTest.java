package seedu.address.model.permission;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class PermissionSetTest {
    @Test
    public void addPermissions() {
        PermissionSet permissionSet = new PermissionSet();
        //null -> false
        assertFalse(permissionSet.addPermissions((Permission[]) null));

        //null mixed with value from permission enum -> true
        //ignore null and add all that is valid.
        assertTrue(permissionSet.addPermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                null
        ));

        //Value from Permission enum & permissions not already in set -> true
        assertTrue(permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        ));

        //permissions already in set -> false
        assertFalse(permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        ));


        permissionSet = new PermissionSet();
        Set<Permission> permissionsToAdd = null;
        //null set -> false
        assertFalse(permissionSet.addPermissions((permissionsToAdd)));

        permissionsToAdd = new HashSet<>();
        //null -> false
        permissionsToAdd.add(null);
        assertFalse(permissionSet.addPermissions((permissionsToAdd)));

        //null mixed with value from permission enum -> true
        //ignore null and add all that is valid.
        permissionsToAdd.add(Permission.EDIT_EMPLOYEE);
        permissionsToAdd.add(Permission.APPROVE_LEAVE);
        assertTrue(permissionSet.addPermissions((permissionsToAdd)));

        //Value from Permission enum & permissions not already in set -> true
        permissionsToAdd = new HashSet<>();
        permissionsToAdd.add(Permission.ASSIGN_PERMISSION);
        permissionsToAdd.add(Permission.ADD_EMPLOYEE);
        permissionsToAdd.add(Permission.APPROVE_LEAVE);
        permissionsToAdd.add(Permission.EDIT_EMPLOYEE);
        assertTrue(permissionSet.addPermissions((permissionsToAdd)));

        //permissions already in set -> false
        permissionSet = new PermissionSet(permissionsToAdd);
        assertFalse(permissionSet.addPermissions((permissionsToAdd)));
    }

    @Test
    public void removePermissions() {
        PermissionSet permissionSet = new PermissionSet();
        permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        );

        //null -> false
        assertFalse(permissionSet.removePermissions((Permission[]) null));

        //null mixed with value from permission enum -> true
        //ignore null and remove found permissions.
        assertTrue(permissionSet.removePermissions(
                Permission.EDIT_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                null
        ));

        //allocated permission -> true
        assertTrue(permissionSet.removePermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE
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



        permissionSet = new PermissionSet();
        permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        );

        Set<Permission> permissionsToRemove = null;
        //null set -> false
        assertFalse(permissionSet.removePermissions((permissionsToRemove)));

        permissionsToRemove = new HashSet<>();
        //null -> false
        permissionsToRemove.add(null);
        assertFalse(permissionSet.removePermissions((permissionsToRemove)));

        //null mixed with value from permission enum -> true
        //ignore null and remove found permissions.
        permissionsToRemove.add(Permission.EDIT_EMPLOYEE);
        permissionsToRemove.add(Permission.APPROVE_LEAVE);
        assertTrue(permissionSet.removePermissions((permissionsToRemove)));

        //Value from Permission enum & all permission in set-> true
        permissionsToRemove = new HashSet<>();
        permissionsToRemove.add(Permission.ASSIGN_PERMISSION);
        permissionsToRemove.add(Permission.ADD_EMPLOYEE);
        assertTrue(permissionSet.removePermissions((permissionsToRemove)));

        //permission not in set -> false
        assertFalse(permissionSet.removePermissions((permissionsToRemove)));


        permissionSet = new PermissionSet();
        permissionSet.addPermissions(
                Permission.ASSIGN_PERMISSION,
                Permission.ADD_EMPLOYEE,
                Permission.APPROVE_LEAVE,
                Permission.EDIT_EMPLOYEE
        );
        //at least 1 permission in set -> true
        permissionsToRemove = new HashSet<>();
        permissionsToRemove.add(Permission.VIEW_PROJECT);
        permissionsToRemove.add(Permission.ADD_EMPLOYEE);
        assertTrue(permissionSet.removePermissions((permissionsToRemove)));
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
                Permission.ASSIGN_PROJECT
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
        assertFalse(testPermissionSet.containsAll(Permission.EDIT_EMPLOYEE, Permission.ASSIGN_PERMISSION));
        //Existing permission -> true
        assertTrue(testPermissionSet.containsAll(Permission.REMOVE_EMPLOYEE));
        assertTrue(testPermissionSet.containsAll(Permission.REMOVE_EMPLOYEE, Permission.ADD_EMPLOYEE));

        PermissionSet duplicatePermissionSet = new PermissionSet();
        duplicatePermissionSet.addPermissions(testPermissionSet);

        PermissionSet morePermissionSet = new PermissionSet();
        morePermissionSet.addPermissions(testPermissionSet);
        morePermissionSet.addPermissions(Permission.ASSIGN_PERMISSION);

        //Same permissionSet --> true
        assertTrue(testPermissionSet.containsAll(testPermissionSet));
        //duplicate permissionSet -> true
        assertTrue(testPermissionSet.containsAll(duplicatePermissionSet));
        //have permission not included --> false
        assertFalse(testPermissionSet.containsAll(morePermissionSet));
    }

    @Test
    public void equals() {
        PermissionSet pSet = new PermissionSet(PermissionSet.PresetPermission.ADMIN);

        // same values -> returns true
        PermissionSet pSetCopy = new PermissionSet(pSet);
        assertTrue(pSet.equals(pSetCopy));

        // same object -> returns true
        assertTrue(pSet.equals(pSet));

        // null -> returns false
        assertFalse(pSet.equals(null));

        // different type -> returns false
        assertFalse(pSet.equals(5));

        // different permissions -> returns false
        PermissionSet pSetDiff = new PermissionSet(PermissionSet.PresetPermission.MANAGER);
        assertFalse(pSet.equals(pSetDiff));
    }

}
