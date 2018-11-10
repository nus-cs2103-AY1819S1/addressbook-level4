package seedu.address.model.person;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.occasion.UniqueOccasionList;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class PersonDescriptor {
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private UniqueOccasionList occasionList;
    private UniqueModuleList moduleList;
    private Set<Tag> tags;

    public PersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public PersonDescriptor(PersonDescriptor toCopy) {
        setName(toCopy.name);
        setPhone(toCopy.phone);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setTags(toCopy.tags);
        setUniqueModuleList(toCopy.getModuleList().orElse(new UniqueModuleList()));
        setUniqueOccasionList(toCopy.getOccasionList().orElse(new UniqueOccasionList()));
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldNotEmpty() {
        return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Optional<Address> getAddress() {
        return Optional.ofNullable(address);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    public Optional<UniqueModuleList> getModuleList() {
        return (moduleList != null) ? Optional.ofNullable(moduleList) : Optional.empty();
    }

    public void setUniqueModuleList(UniqueModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public Optional<UniqueOccasionList> getOccasionList() {
        return (occasionList != null) ? Optional.ofNullable(occasionList) : Optional.empty();
    }

    public void setUniqueOccasionList(UniqueOccasionList occasionList) {
        this.occasionList = occasionList;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDescriptor)) {
            return false;
        }

        // state check
        PersonDescriptor e = (PersonDescriptor) other;

        return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getTags().equals(e.getTags());
    }
}
