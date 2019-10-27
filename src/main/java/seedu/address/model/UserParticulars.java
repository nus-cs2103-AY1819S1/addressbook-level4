package seedu.address.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the user's particulars to be included in the resume.
 */
public class UserParticulars implements Serializable {

    private static final String DEFAULT_NAME = "John Doe";
    private static final String DEFAULT_MOBILE = "+65 91234567";
    private static final String DEFAULT_EMAIL = "johndoe@example.com";
    private static final String DEFAULT_ADDRESS = "412 Kent Ridge Road, #05-03";

    private String name;
    private String mobile;
    private String email;
    private String address;

    public UserParticulars() {
        name = DEFAULT_NAME;
        mobile = DEFAULT_MOBILE;
        email = DEFAULT_EMAIL;
        address = DEFAULT_ADDRESS;
    }

    public UserParticulars(String name, String mobile, String email, String address) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof UserParticulars)) { //this handles null as well.
            return false;
        }

        UserParticulars other = (UserParticulars) obj;

        return Objects.equals(name, other.name)
                && Objects.equals(mobile, other.mobile)
                && Objects.equals(email, other.email)
                && Objects.equals(address, other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mobile, email, address);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name : " + name + "\n");
        sb.append("Mobile : " + mobile + "\n");
        sb.append("Email : " + email + "\n");
        sb.append("Address : " + address);
        return sb.toString();
    }
}
