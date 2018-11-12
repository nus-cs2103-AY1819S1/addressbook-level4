package seedu.modsuni.model.module;

import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to encapsulate all data fields relating to a Module.
 */
public class Module {

    // Information fields
    private final Code code;
    private final String department;
    private final String title;
    private final String description;
    private final int credit;
    private final boolean isAvailableInSem1;
    private final boolean isAvailableInSem2;
    private final boolean isAvailableInSpecialTerm1;
    private final boolean isAvailableInSpecialTerm2;
    private final List<Code> lockedModules = new ArrayList<>();
    private final Prereq prereq;

    /**
     * Every field must be present and not null.
     */
    public Module(Code code, String department, String title, String description, int credit,
                  boolean isAvailableInSem1, boolean isAvailableInSem2, boolean isAvailableInSpecialTerm1,
                  boolean isAvailableInSpecialTerm2, List<Code> lockedModules, Prereq prereq) {
        requireAllNonNull(code, department, title, description, credit, isAvailableInSem1,
                isAvailableInSem2, isAvailableInSpecialTerm1, isAvailableInSpecialTerm2);
        this.code = code;
        this.department = department;
        this.title = title;
        this.description = description;
        this.credit = credit;
        this.isAvailableInSem1 = isAvailableInSem1;
        this.isAvailableInSem2 = isAvailableInSem2;
        this.isAvailableInSpecialTerm1 = isAvailableInSpecialTerm1;
        this.isAvailableInSpecialTerm2 = isAvailableInSpecialTerm2;
        this.lockedModules.addAll(lockedModules);
        this.prereq = prereq;
    }

    public Code getCode() {
        return code;
    }

    public String getDepartment() {
        return department;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCredit() {
        return credit;
    }

    public boolean isAvailableInSem1() {
        return isAvailableInSem1;
    }

    public boolean isAvailableInSem2() {
        return isAvailableInSem2;
    }

    public boolean isAvailableInSpecialTerm1() {
        return isAvailableInSpecialTerm1;
    }

    public boolean isAvailableInSpecialTerm2() {
        return isAvailableInSpecialTerm2;
    }

    public List<Code> getLockedModules() {
        return lockedModules;
    }

    public Prereq getPrereq() {
        return prereq;
    }

    public boolean[] getSems() {
        boolean[] sems = new boolean[4];
        for (int i = 0; i < 4; i++) {
            sems[i] = false;
        }
        if (isAvailableInSem1) {
            sems[0] = true;
        }
        if (isAvailableInSem2) {
            sems[1] = true;
        }
        if (isAvailableInSpecialTerm1) {
            sems[2] = true;
        }
        if (isAvailableInSpecialTerm2) {
            sems[3] = true;
        }
        return sems;
    }

    /**
     * Checks if this module's prerequisite can be satisfied using the list of codes.
     * @param codeChecklist List of codes to be checked against.
     * @return true if this prerequisite can be satisfied, false otherwise.
     */
    public boolean checkPrereq(List<Code> codeChecklist) {
        return prereq.checkPrereq(codeChecklist);
    }

    /**
     * Returns true if both modules of the same code.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getCode().equals(getCode());
    }

    /**
     * Returns true if this module's code is the prefix of the other module's code.
     */
    public boolean isPrefixModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }
        return otherModule != null
                && otherModule.getCode().code.startsWith(getCode().code);
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getCode().equals(getCode())
                && otherModule.getDepartment().equals(getDepartment())
                && otherModule.getTitle().equals(getTitle())
                && otherModule.getDescription().equals(getDescription())
                && otherModule.getCredit() == getCredit()
                && otherModule.isAvailableInSem1 == isAvailableInSem1
                && otherModule.isAvailableInSem2 == isAvailableInSem2
                && otherModule.isAvailableInSpecialTerm1 == isAvailableInSpecialTerm1
                && otherModule.isAvailableInSpecialTerm2 == isAvailableInSpecialTerm2;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, department, title, description, credit, isAvailableInSem1,
                isAvailableInSem2, isAvailableInSpecialTerm1, isAvailableInSpecialTerm2);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getCode())
                .append(" Department: ")
                .append(getDepartment())
                .append(" Title: ")
                .append(getTitle())
                .append(" Description: ")
                .append(getDescription())
                .append(" Credit: ")
                .append(getCredit())
                .append(" Is Available in Sem 1: ")
                .append(isAvailableInSem1)
                .append(" Is Available in Sem 2: ")
                .append(isAvailableInSem1)
                .append(" Is Available in Special Term 1: ")
                .append(isAvailableInSpecialTerm1)
                .append(" Is Available in Special Term 2: ")
                .append(isAvailableInSpecialTerm2)
                .append(" Prereq: ")
                .append(prereq.toString());
        return builder.toString();
    }
}
