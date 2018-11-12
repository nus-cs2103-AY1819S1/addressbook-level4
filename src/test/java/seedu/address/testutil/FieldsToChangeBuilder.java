package seedu.address.testutil;

import seedu.address.logic.commands.PrivacyCommand.FieldsToChange;

/**
 * A utility class to help with building FieldsToChange objects.
 */
public class FieldsToChangeBuilder {

    private FieldsToChange fieldsToChange;

    public FieldsToChangeBuilder() {
        fieldsToChange = new FieldsToChange();
    }

    /**
     * Sets the phone value of the builder to private.
     */
    public FieldsToChangeBuilder withPrivatePhone() {
        fieldsToChange.setPhonePrivacy("Y");
        return this;
    }

    /**
     * Sets the email value of the builder to private.
     */
    public FieldsToChangeBuilder withPrivateEmail() {
        fieldsToChange.setEmailPrivacy("Y");
        return this;
    }

    /**
     * Sets the address value of the builder to private.
     */
    public FieldsToChangeBuilder withPrivateAddress() {
        fieldsToChange.setAddressPrivacy("Y");
        return this;
    }

    /**
     * Sets the phone, email and address value of the builder to private.
     */
    public FieldsToChangeBuilder withAllPrivate() {
        fieldsToChange.setPhonePrivacy("Y");
        fieldsToChange.setEmailPrivacy("Y");
        fieldsToChange.setAddressPrivacy("Y");
        return this;
    }

    /**
     * Sets the phone, email and address value of the builder to non-private.
     */
    public FieldsToChangeBuilder withNotPrivate() {
        fieldsToChange.setPhonePrivacy("N");
        fieldsToChange.setEmailPrivacy("N");
        fieldsToChange.setAddressPrivacy("N");
        return this;
    }

    /**
     * Builds the FieldsToChange with the privacy values set.
     * @return FieldsToChange
     */
    public FieldsToChange build() {
        return fieldsToChange;
    }

}
