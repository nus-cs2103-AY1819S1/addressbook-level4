package seedu.scheduler.logic.parser;

/**
 * A flag that indicates additional options in an arguments string.
 * E.g. '-a' in 'delete 1 -a'.
 */
public class Flag extends Identity {

    public Flag(String flag) {
        super(flag);
    }

    public String getFlag() {
        return getIdentity();
    }

    public String toString() {
        return getFlag();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Flag)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Flag otherFlag = (Flag) obj;
        return otherFlag.getFlag().equals(getFlag());
    }
}
