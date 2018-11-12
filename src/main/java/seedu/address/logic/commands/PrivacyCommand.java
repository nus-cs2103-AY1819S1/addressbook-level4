package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Department;
import seedu.address.model.person.Email;
import seedu.address.model.person.Feedback;
import seedu.address.model.person.Manager;
import seedu.address.model.person.Name;
import seedu.address.model.person.OtHour;
import seedu.address.model.person.OtRate;
import seedu.address.model.person.PayDeductibles;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rating;
import seedu.address.model.person.Salary;
import seedu.address.model.tag.Tag;

/**
 * Togggles the privacy value of an existing person's information in SSENISUB.
 */
public class PrivacyCommand extends Command {

    public static final String COMMAND_WORD = "privacy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the privacy value of information of the"
            + "person identified by the index number used in the displayed person list. "
            + "Only Phone, Email and Address can be Private.\n"
            + "Input value as y to make private and n to make non-private.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_PHONE + "y/n] "
            + "[" + PREFIX_EMAIL + "y/n] "
            + "[" + PREFIX_ADDRESS + "y/n]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "y "
            + PREFIX_ADDRESS + "n";

    public static final String MESSAGE_EDIT_PRIVACY_SUCCESS = "Privacy modified: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least 1 field must be entered!";

    private final Index index;
    private final FieldsToChange fieldsToChange;

    public PrivacyCommand(Index index, FieldsToChange fieldsToChange) {
        requireNonNull(index);
        requireNonNull(fieldsToChange);

        this.index = index;
        this.fieldsToChange = fieldsToChange;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = changePrivacy(personToEdit, fieldsToChange);
        model.updatePerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.commitSsenisub();

        Index indexEdited = Index.fromZeroBased(model.getFilteredPersonList().indexOf(editedPerson));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(indexEdited));

        return new CommandResult(String.format(MESSAGE_EDIT_PRIVACY_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceof handle nulls
        if (!(other instanceof PrivacyCommand)) {
            return false;
        }

        //state check
        PrivacyCommand p = (PrivacyCommand) other;
        return index.equals(p.index)
                && fieldsToChange.equals(p.fieldsToChange);
    }

    /**
     * Changes the privacy values of a {@code Person} with the options stated in {@code fieldsToChange}
     */
    private static Person changePrivacy(Person personToEdit, FieldsToChange fieldsToChange) {
        assert personToEdit != null;

        Name name = personToEdit.getName();
        Phone phone = personToEdit.getPhone();
        Email email = personToEdit.getEmail();
        Address address = personToEdit.getAddress();
        Rating rating = personToEdit.getRating();
        Department department = personToEdit.getDepartment();
        Manager manager = personToEdit.getManager();
        Salary salary = personToEdit.getSalary();
        OtHour hours = personToEdit.getOtHours();
        OtRate rate = personToEdit.getOtRate();
        PayDeductibles deductibles = personToEdit.getDeductibles();
        Feedback feedback = personToEdit.getFeedback();
        Set<Tag> tags = personToEdit.getTags();
        boolean favourite = personToEdit.getFavourite();

        if (fieldsToChange.getPhonePrivacy().isPresent()) {
            phone = new Phone(personToEdit.getPhone().value, fieldsToChange.getPhonePrivacy().get());
        }
        if (fieldsToChange.getEmailPrivacy().isPresent()) {
            email = new Email(personToEdit.getEmail().value, fieldsToChange.getEmailPrivacy().get());
        }
        if (fieldsToChange.getAddressPrivacy().isPresent()) {
            address = new Address(personToEdit.getAddress().value, fieldsToChange.getAddressPrivacy().get());
        }

        return new Person(name, phone, email, address, rating, department, manager,
                salary, hours, rate, deductibles, feedback, tags, favourite);
    }

    /**
     * Stores the fields to change privacy of the person with.
     */
    public static class FieldsToChange {
        private String phonePrivacy;
        private String emailPrivacy;
        private String addressPrivacy;

        public FieldsToChange() {}

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(phonePrivacy, emailPrivacy, addressPrivacy);
        }

        public void setPhonePrivacy(String phonePrivacy) {
            this.phonePrivacy = phonePrivacy;
        }

        public Optional<String> getPhonePrivacy() {
            return Optional.ofNullable(phonePrivacy);
        }

        public void setEmailPrivacy(String emailPrivacy) {
            this.emailPrivacy = emailPrivacy;
        }

        public Optional<String> getEmailPrivacy() {
            return Optional.ofNullable(emailPrivacy);
        }

        public void setAddressPrivacy(String addressPrivacy) {
            this.addressPrivacy = addressPrivacy;
        }

        public Optional<String> getAddressPrivacy() {
            return Optional.ofNullable(addressPrivacy);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FieldsToChange)) {
                return false;
            }

            // state check
            FieldsToChange e = (FieldsToChange) other;

            return getPhonePrivacy().equals(e.getPhonePrivacy())
                    && getAddressPrivacy().equals(e.getAddressPrivacy())
                    && getEmailPrivacy().equals(e.getEmailPrivacy());
        }


    }

}
