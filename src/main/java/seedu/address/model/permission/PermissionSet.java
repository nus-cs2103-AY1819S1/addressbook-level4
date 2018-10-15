package seedu.address.model.permission;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the set of permission(s) a user.
 * Null is not permitted in PermissionSet.
 * Modification of this class is to be restricted to users with "ASSIGN_PERMISSION" permission.
 * Only exception to this is only when the application is getting opened and initializing.
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
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     *
     * @param p permission to add
     * @return true if success, otherwise false.
     */
    public boolean addPermission(Permission p) {
        if (p == null) {
            return false;
        }

        return permissionSet.add(p);
    }

    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     *
     * @param p permission to remove
     * @return true if success, otherwise false.
     */
    public boolean removePermission(Permission p) {
        return permissionSet.remove(p);
    }

    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     * Set permission to one of the preset.
     *
     * @param p preset to use
     * @return true if success, otherwise false.
     */
    public boolean assignPresetPermission(PresetPermission p) {
        if (p == null) {
            return false;
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
            return false;
        }
        return true;
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
    public boolean contains(Permission p) {
        return permissionSet.contains(p);
    }

    /**
     * Add all elements in the specified PermissionSet to this PermissionSet.
     *
     * @param pSet the specified PermissionSet
     * @return true if successful, otherwise false.
     */
    public boolean addAll(PermissionSet pSet) {
        return this.permissionSet.addAll(pSet.permissionSet);
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
        preset.add(Permission.ASSIGN_PROJECT);
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
        preset.add(Permission.ASSIGN_PROJECT);
        preset.add(Permission.CREATE_DEPARTMENT);
        preset.add(Permission.ASSIGN_DEPARTMENT);
        return preset;
    }

    /**
     * @return a set of permission that represent the permission an employee should have.
     */
    private Set<Permission> getEmployeePreset() {
        Set<Permission> preset = new HashSet<>();
        preset.add(Permission.VIEW_PROJECT);
        return preset;
    }

}
