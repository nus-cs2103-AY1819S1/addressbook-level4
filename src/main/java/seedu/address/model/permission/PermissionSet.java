package seedu.address.model.permission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import seedu.address.model.person.Person;

/**
 * Represents the set of permission(s) a user.
 * Modification of set of permission in this class can only be performed by a user
 * with "ASSIGN_PERMISSION" permission.
 */
public class PermissionSet {
    private Set<Permission> permissionSet;

    /**
     * Enum class to represent the preset permission available.
     */
    public enum PresetPermission {
        ADMIN,
        MANAGER,
        EMPLOYEE
    }

    public PermissionSet() {
        permissionSet = new HashSet<>();
    }

    /**
     * This action is only performable by user with "ASSIGN_PERMISSION" permission.
     * Throws {@code UnsupportedOperationException} if person does not have required permission.
     *
     * @param personLoggedIn the person that is logged into the system.
     * @param p              permission to add
     */
    public void addPermission(Person personLoggedIn, Permission p) {
        if (!personLoggedIn.havePermission(Permission.ASSIGN_PERMISSION)) {
            throw new UnsupportedOperationException();
        }
        permissionSet.add(p);
    }

    /**
     * This action is only performable by user with "ASSIGN_PERMISSION" permission.
     * Throws {@code UnsupportedOperationException} if person does not have required permission.
     *
     * @param personLoggedIn the person that is logged into the system.
     * @param p              permission to remove
     */
    public void removePermission(Person personLoggedIn, Permission p) {
        if (!personLoggedIn.havePermission(Permission.ASSIGN_PERMISSION)) {
            throw new UnsupportedOperationException();
        }

        permissionSet.remove(p);
    }

    /**
     * This action is only performable by user with "ASSIGN_PERMISSION" permission.
     * Throws {@code UnsupportedOperationException} if person does not have required permission.
     * Throws (@code IllegalArgumentException} if preset is invalid.
     * Set permission to one of the preset.
     *
     * @param personLoggedIn the person that is logged into the system.
     * @param p              preset to use
     */
    public void assignPresetPermission(Person personLoggedIn, PresetPermission p) {
        if (!personLoggedIn.havePermission(Permission.ASSIGN_PERMISSION)) {
            throw new UnsupportedOperationException();
        }

        switch (p) {

        case ADMIN:
            permissionSet = getAdminPreset();
            break;

        case MANAGER:
            permissionSet = getManagerPreset();
            break;

        case EMPLOYEE:
            permissionSet = getEmployeePreset();
            break;

        default:
            //Should not be able to get here.
            throw new IllegalArgumentException();
        }
    }

    /**
     * Returns an immutable permission set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Set<Permission> getGrantedPermission() {
        return Collections.unmodifiableSet(permissionSet);
    }

    /**
     * Check if permission set contains a specific permission
     *
     * @param p the permission to check
     * @return true if permission found, otherwise false.
     */
    public boolean contain(Permission p) {
        return permissionSet.contains(p);
    }

    /**
     * @return a set of permission that represent the permission an admin should have.
     */
    private Set<Permission> getAdminPreset() {
        Set<Permission> preset = new HashSet<>();
        preset.add(Permission.ADD_EMPLOYEE);
        preset.add(Permission.REMOVE_EMPLOYEE);
        preset.add(Permission.EDIT_EMPLOYEE);
        preset.add(Permission.VIEW_EMPLOYEE_LEAVE);
        preset.add(Permission.APPROVE_LEAVE);
        preset.add(Permission.CREATE_PROJECT);
        preset.add(Permission.VIEW_PROJECT);
        preset.add(Permission.CREATE_DEPARTMENT);
        preset.add(Permission.ASSIGN_DEPARTMENT);
        preset.add(Permission.ASSIGN_PERMISSION);
        return preset;
    }

    /**
     * @return a set of permission that represent the permission a manager should have.
     */
    private Set<Permission> getManagerPreset() {
        Set<Permission> preset = new HashSet<>();
        preset.add(Permission.ADD_EMPLOYEE);
        preset.add(Permission.REMOVE_EMPLOYEE);
        preset.add(Permission.EDIT_EMPLOYEE);
        preset.add(Permission.VIEW_EMPLOYEE_LEAVE);
        preset.add(Permission.APPROVE_LEAVE);
        preset.add(Permission.CREATE_PROJECT);
        preset.add(Permission.VIEW_PROJECT);
        preset.add(Permission.CREATE_DEPARTMENT);
        preset.add(Permission.ASSIGN_DEPARTMENT);
        return preset;
    }

    /**
     * @return a set of permission that represent the permission an employee should have.
     */
    private Set<Permission> getEmployeePreset() {
        Set<Permission> preset = new HashSet<>();
        return preset;
    }

}
