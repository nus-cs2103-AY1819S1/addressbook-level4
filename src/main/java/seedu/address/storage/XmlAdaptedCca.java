package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.budget.Budget;
import seedu.address.model.budget.Transaction;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Room;
import seedu.address.model.person.School;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly adapted version of the CCA.
 */
public class XmlAdaptedCca {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "CCA's %s field is missing!";

    @XmlElement(required = true)
    private String ccaName;

    @XmlElement(required = true)
    private String headName;
    @XmlElement(required = true)
    private String headPhone;
    @XmlElement(required = true)
    private String headEmail;
    @XmlElement(required = true)
    private String headRoom;
    @XmlElement(required = true)
    private String headSchool;
    @XmlElement
    private List<XmlAdaptedTag> headTagged = new ArrayList<>();

    @XmlElement(required = true)
    private String viceHeadName;
    @XmlElement(required = true)
    private String viceHeadPhone;
    @XmlElement(required = true)
    private String viceHeadEmail;
    @XmlElement(required = true)
    private String viceHeadRoom;
    @XmlElement(required = true)
    private String viceHeadSchool;
    @XmlElement
    private List<XmlAdaptedTag> viceHeadTagged = new ArrayList<>();

    @XmlElement(required = true)
    private String givenBudget;
    @XmlElement(required = true)
    private String spent;
    @XmlElement(required = true)
    private String outstanding;
    @XmlElement(required = true)
    private String transaction;

    private Budget budget = new Budget(Integer.parseInt(givenBudget), Integer.parseInt(spent),
        Integer.parseInt(outstanding), new Transaction(transaction));


    /**
     * Constructs an XmlAdaptedTag.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCca() {
    }

    /**
     * Constructs a {@code XmlAdaptedCca} with the given {@code ccaName}.
     */
    public XmlAdaptedCca(String tagName) {
        this.ccaName = ccaName;
    }

    /**
     * Converts a given CCA into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created
     */
    public XmlAdaptedCca(Cca source) {
        ccaName = source.getCcaName();
    }

    /**
     * Converts this jaxb-friendly adapted CCA object into the model's CCA object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted CCA
     */
    public Cca toModelType() throws IllegalValueException {
        if (ccaName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Tag.class.getSimpleName()));
        }
        if (!Tag.isValidTagName(ccaName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Tag modelCcaName = new Tag(ccaName);

        final List<Tag> headTags = new ArrayList<>();
        for (XmlAdaptedTag tag : headTagged) {
            headTags.add(tag.toModelType());
        }

        if (headName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(headName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelHeadName = new Name(headName);

        if (headPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(headPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelHeadPhone = new Phone(headPhone);

        if (headEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(headEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelHeadEmail = new Email(headEmail);

        if (headRoom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Room.class.getSimpleName()));
        }
        if (!Room.isValidRoom(headRoom)) {
            throw new IllegalValueException(Room.MESSAGE_ROOM_CONSTRAINTS);
        }
        final Room modelHeadRoom = new Room(headRoom);

        if (headSchool == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
        }
        if (!School.isValidSchool(headSchool)) {
            throw new IllegalValueException(School.MESSAGE_SCHOOL_CONSTRAINTS);
        }
        final School modelHeadSchool = new School(headSchool);

        final Set<Tag> modelHeadTags = new HashSet<>(headTags);

        Person modelHead = new Person(modelHeadName, modelHeadPhone, modelHeadEmail, modelHeadRoom,
                modelHeadSchool, modelHeadTags);

        final List<Tag> viceHeadTags = new ArrayList<>();
        for (XmlAdaptedTag tag : viceHeadTagged) {
            viceHeadTags.add(tag.toModelType());
        }

        if (viceHeadName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(viceHeadName)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelViceHeadName = new Name(viceHeadName);

        if (viceHeadPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(viceHeadPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelViceHeadPhone = new Phone(viceHeadPhone);

        if (viceHeadEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(viceHeadEmail)) {
            throw new IllegalValueException(Email.MESSAGE_EMAIL_CONSTRAINTS);
        }
        final Email modelViceHeadEmail = new Email(viceHeadEmail);

        if (viceHeadRoom == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Room.class.getSimpleName()));
        }
        if (!Room.isValidRoom(viceHeadRoom)) {
            throw new IllegalValueException(Room.MESSAGE_ROOM_CONSTRAINTS);
        }
        final Room modelViceHeadRoom = new Room(viceHeadRoom);

        if (viceHeadSchool == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, School.class.getSimpleName()));
        }
        if (!School.isValidSchool(viceHeadSchool)) {
            throw new IllegalValueException(School.MESSAGE_SCHOOL_CONSTRAINTS);
        }
        final School modelViceHeadSchool = new School(viceHeadSchool);

        final Set<Tag> modelViceHeadTags = new HashSet<>(viceHeadTags);

        Person modelViceHead = new Person(modelViceHeadName, modelViceHeadPhone, modelViceHeadEmail,
            modelViceHeadRoom, modelViceHeadSchool, modelViceHeadTags);

        // TODO: BUDGET
        //Budget
        if (givenBudget == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Budget.class.getSimpleName()));
        }

        int modelGivenBudget = Integer.parseInt(givenBudget);
        int modelSpent = Integer.parseInt(spent);
        int modelOutstanding = Integer.parseInt(outstanding);
        Transaction modelTransaction = new Transaction(transaction);

        Budget modelBudget = new Budget(modelGivenBudget, modelSpent, modelOutstanding, modelTransaction);

        return new Cca(modelCcaName, modelHead, modelViceHead, modelBudget);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCca)) {
            return false;
        }

        XmlAdaptedCca otherPerson = (XmlAdaptedCca) other;
        return Objects.equals(ccaName, otherPerson.ccaName)
            && Objects.equals(headName, otherPerson.headName)
            && Objects.equals(headPhone, otherPerson.headPhone)
            && Objects.equals(headEmail, otherPerson.headEmail)
            && Objects.equals(headRoom, otherPerson.headRoom)
            && Objects.equals(headSchool, otherPerson.headSchool)
            && headTagged.equals(otherPerson.headTagged)
            && Objects.equals(viceHeadName, otherPerson.viceHeadName)
            && Objects.equals(viceHeadPhone, otherPerson.viceHeadPhone)
            && Objects.equals(viceHeadEmail, otherPerson.viceHeadEmail)
            && Objects.equals(viceHeadRoom, otherPerson.viceHeadRoom)
            && Objects.equals(viceHeadSchool, otherPerson.viceHeadSchool)
            && viceHeadTagged.equals(otherPerson.viceHeadTagged)
            && budget.equals(((XmlAdaptedCca) other).budget);
    }
}
