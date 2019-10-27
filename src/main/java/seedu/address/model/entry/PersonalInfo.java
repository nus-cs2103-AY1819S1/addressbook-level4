package seedu.address.model.entry;

/**
 * representing the unique personal information stored by the user
 */
public class PersonalInfo {
    private Name name;
    private Address address;
    private Phone phone;
    private Email email;
    private String github;

    public PersonalInfo(Name name, Address address, Phone phone, Email email, String github) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.github = github;
    }

    public Name getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public String getGithub() {
        return github;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonalInfo)) {
            return false;
        }

        PersonalInfo info = (PersonalInfo) other;
        return info.getName().equals(getName())
                && info.getPhone().equals(getPhone())
                && info.getEmail().equals(getEmail())
                && info.getAddress().equals(getAddress())
                && info.getGithub().equals(getGithub());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Github")
                .append(getGithub());
        return builder.toString();
    }
}
