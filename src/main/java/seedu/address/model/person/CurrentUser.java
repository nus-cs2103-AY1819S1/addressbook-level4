package seedu.address.model.person;

public class CurrentUser extends Person {
    private Person person;


    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param phone
     * @param email
     */
    public CurrentUser(Name name, Phone phone, Email email) {
        super(name, phone, email, null, null);
    }
}
