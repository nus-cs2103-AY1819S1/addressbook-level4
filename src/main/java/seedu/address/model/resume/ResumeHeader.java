package seedu.address.model.resume;

import seedu.address.model.UserParticulars;

/**
 * Represents information that is only printed once in the resume such as the user's personal particulars.
 */
public class ResumeHeader {
    private final UserParticulars userParticulars;

    public ResumeHeader(UserParticulars userParticulars) {
        this.userParticulars = userParticulars;
    }

    public String getName() {
        return userParticulars.getName();
    }

    public String getPhone() {
        return userParticulars.getMobile();
    }

    public String getEmail() {
        return userParticulars.getEmail();
    }

    public String getAddress() {
        return userParticulars.getAddress();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ResumeHeader)) {
            return false;
        }

        // state check
        ResumeHeader other = (ResumeHeader) obj;
        return this.getName().equals(other.getName())
                && this.getPhone().equals(other.getPhone())
                && this.getEmail().equals(other.getEmail())
                && this.getAddress().equals(other.getAddress());
    }
}
