package seedu.address.model.common;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.EmptyAddress;
import seedu.address.model.person.EmptyEmail;
import seedu.address.model.person.EmptyPhone;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePhoto;
import seedu.address.model.tag.Tag;

/**
 * Stores the details to edit the person with. Each non-empty field value will replace the
 * corresponding field value of the person.
 */
public class EditPersonDescriptor {
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Map<String, Mark> marks;
    private Map<String, Mark> attendance;
    private Note note;

    public EditPersonDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPersonDescriptor(EditPersonDescriptor toCopy) {
        setName(toCopy.name);
        setPhone(toCopy.phone);
        setEmail(toCopy.email);
        setAddress(toCopy.address);
        setTags(toCopy.tags);
        setMarks(toCopy.marks);
        setAttendance(toCopy.attendance);
        setNote(toCopy.note);
    }

    /**
     * Parse constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditPersonDescriptor(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(this::setTags);
    }

    /**
     * Creates and returns a {@code Person} with the details
     * edited with {@code editPersonDescriptor}.
     */
    public Person createNewPerson() {
        assert name != null;
        return new Person(name, getPhone().orElseGet(EmptyPhone::new), getEmail().orElseGet(EmptyEmail::new),
          getAddress().orElseGet(EmptyAddress::new), getTags().orElseGet(HashSet::new));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    public Person createEditedPerson(Person personToEdit) {
        assert personToEdit != null;

        Name updatedName = this.getName().orElse(personToEdit.getName());
        Phone updatedPhone = this.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = this.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = this.getAddress().orElse(personToEdit.getAddress());
        ProfilePhoto updatedPicture = personToEdit.getProfilePhoto();
        Set<Tag> updatedTags = this.getTags().orElse(personToEdit.getTags());
        Map<String, Mark> updatedMarks = this.getMarks().orElse(personToEdit.getMarks());
        Map<String, Mark> updatedAttendance = this.getAttendance().orElse(personToEdit.getAttendance());
        Note updatedNote = this.getNote().orElse(personToEdit.getNote());

        return new Person(
            updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPicture, updatedTags, updatedMarks,
            updatedAttendance, updatedNote);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
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

    public void setMarks(Map<String, Mark> marks) {
        this.marks = (marks != null) ? new HashMap<>(marks) : null;
    }

    public Optional<Map<String, Mark>> getMarks() {
        return (marks != null) ? Optional.of(Collections.unmodifiableMap(marks)) : Optional.empty();
    }

    public void setAttendance(Map<String, Mark> attendance) {
        this.attendance = (attendance != null) ? new HashMap<>(attendance) : null;
    }

    public Optional<Map<String, Mark>> getAttendance() {
        return (attendance != null) ? Optional.of(Collections.unmodifiableMap(attendance)) : Optional.empty();
    }

    public Optional<Note> getNote() {
        return Optional.ofNullable(note);
    }

    public void setNote(Note note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonDescriptor)) {
            return false;
        }

        // state check
        EditPersonDescriptor e = (EditPersonDescriptor) other;

        return getName().equals(e.getName())
            && getPhone().equals(e.getPhone())
            && getEmail().equals(e.getEmail())
            && getAddress().equals(e.getAddress())
            && getTags().equals(e.getTags())
            && getMarks().equals(e.getMarks())
            && getAttendance().equals(e.getAttendance())
            && getNote().equals(e.getNote());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
