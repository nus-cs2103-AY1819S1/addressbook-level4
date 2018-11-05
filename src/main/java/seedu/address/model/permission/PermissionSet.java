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
        ADMIN {
            /**
             * @return a set of permission that represent the permission an admin should have.
             */
            public Set<Permission> getPreset() {
                Set<Permission> preset = new HashSet<>();
                preset.add(Permission.ADD_EMPLOYEE);
                preset.add(Permission.REMOVE_EMPLOYEE);
                preset.add(Permission.EDIT_EMPLOYEE);
                preset.add(Permission.VIEW_EMPLOYEE_LEAVE);
                preset.add(Permission.APPROVE_LEAVE);
                preset.add(Permission.CREATE_PROJECT);
                preset.add(Permission.VIEW_PROJECT);
                preset.add(Permission.ASSIGN_PROJECT);
                preset.add(Permission.ASSIGN_PERMISSION);
                return preset;
            }
        },
        MANAGER {
            /**
             * @return a set of permission that represent the permission a manager should have.
             */
            public Set<Permission> getPreset() {
                Set<Permission> preset = new HashSet<>();
                preset.add(Permission.ADD_EMPLOYEE);
                preset.add(Permission.REMOVE_EMPLOYEE);
                preset.add(Permission.EDIT_EMPLOYEE);
                preset.add(Permission.VIEW_EMPLOYEE_LEAVE);
                preset.add(Permission.APPROVE_LEAVE);
                preset.add(Permission.CREATE_PROJECT);
                preset.add(Permission.VIEW_PROJECT);
                preset.add(Permission.ASSIGN_PROJECT);
                return preset;
            }
        },
        EMPLOYEE {
            /**
             * @return a set of permission that represent the permission an employee should have.
             */
            public Set<Permission> getPreset() {
                Set<Permission> preset = new HashSet<>();
                preset.add(Permission.VIEW_PROJECT);
                return preset;
            }
        };

        abstract Set<Permission> getPreset();
    }

    public PermissionSet() {
        permissionSet = new HashSet<>();
    }

    public PermissionSet(Permission... pList) {
        permissionSet = new HashSet<>();
        addPermissions(pList);
    }

    public PermissionSet(PermissionSet pSet) {
        permissionSet = new HashSet<>();
        addPermissions(pSet);
    }

    public PermissionSet(Set<Permission> pList) {
        permissionSet = new HashSet<>();
        addPermissions(pList);
    }

    /**
     * Constructor to build a permission set with a preset permission list.
     *
     * @param p the preset to use.
     */
    public PermissionSet(PresetPermission p) {
        assignPresetPermission(p);
    }

    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     *
     * @param pList list of permission to add
     * @return true if permissionSet modified, otherwise false.
     */
    public boolean addPermissions(Permission... pList) {
        if (pList == null) {
            return false;
        }
        boolean isEdited = false;
        for (Permission p : pList) {
            if (p == null) {
                continue;
            }

            if (permissionSet.add(p)) {
                isEdited = true;
            }
        }
        return isEdited;
    }

    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     *
     * @param pList set of permission to add
     * @return true if permissionSet modified, otherwise false.
     */
    public boolean addPermissions(Set<Permission> pList) {
        if (pList == null) {
            return false;
        }
        boolean isEdited = false;
        for (Permission p : pList) {
            if (p == null) {
                continue;
            }

            if (permissionSet.add(p)) {
                isEdited = true;
            }
        }
        return isEdited;
    }

    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     * Add all elements in the specified PermissionSet to this PermissionSet.
     *
     * @param pSet the specified PermissionSet
     * @return true if successful, otherwise false.
     */
    public boolean addPermissions(PermissionSet pSet) {
        return this.permissionSet.addAll(pSet.permissionSet);
    }

    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     * No permissions modified if operation fails.
     *
     * @param pList list of permission to remove
     * @return true if success, otherwise false.
     */
    public boolean removePermissions(Permission... pList) {
        if (pList == null) {
            return false;
        }
        boolean isEdited = false;
        for (Permission p : pList) {
            if (permissionSet.remove(p)) {
                isEdited = true;
            }
        }
        return isEdited;
    }


    /**
     * This action should only be performable by user with "ASSIGN_PERMISSION" permission.
     * No permissions modified if operation fails.
     *
     * @param pList set of permission to remove
     * @return true if success, otherwise false.
     */
    public boolean removePermissions(Set<Permission> pList) {
        if (pList == null) {
            return false;
        }
        boolean isEdited = false;
        for (Permission p : pList) {
            if (permissionSet.remove(p)) {
                isEdited = true;
            }
        }
        return isEdited;
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

        permissionSet = p.getPreset();
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
     * @param pList the list of permission to check
     * @return true if permission found, otherwise false.
     */
    public boolean containsAll(Permission... pList) {
        if (pList == null) {
            return false;
        }

        for (Permission p : pList) {
            if (!permissionSet.contains(p)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if permission set contains all permission in a {@code pSet}
     *
     * @param pSet the PermissionSet to check
     * @return true if permission found, otherwise false.
     */
    public boolean containsAll(PermissionSet pSet) {
        return containsAll(pSet.permissionSet.toArray(new Permission[0]));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        getGrantedPermission().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof PermissionSet)) {
            return false;
        }

        Set<Permission> otherPermissionSet = ((PermissionSet) obj).permissionSet;
        return permissionSet.equals(otherPermissionSet);
    }

}
