package seedu.learnvocabulary.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.learnvocabulary.commons.exceptions.IllegalValueException;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.Address;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Phone;
import seedu.learnvocabulary.model.word.Word;

/**
 * JAXB-friendly version of the Word.
 */
public class XmlAdaptedWord {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Word's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String meaning;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedWord.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedWord() {}

    /**
     * Constructs an {@code XmlAdaptedWord} with the given word details.
     */
    public XmlAdaptedWord(String name, String meaning, String phone,
                          String address, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.meaning = meaning;
        this.phone = phone;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Word into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedWord
     */
    public XmlAdaptedWord(Word source) {
        name = source.getName().fullName;
        meaning = source.getMeaning().fullMeaning;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted word object into the model's Word object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted word
     */
    public Word toModelType() throws IllegalValueException {
        final List<Tag> wordTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            wordTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (meaning == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Meaning.class.getSimpleName()));
        }
        final Meaning modelMeaning = new Meaning(meaning);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_PHONE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(wordTags);
        return new Word(modelName, modelMeaning, modelPhone, modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedWord)) {
            return false;
        }

        XmlAdaptedWord otherWord = (XmlAdaptedWord) other;
        return Objects.equals(name, otherWord.name)
                && Objects.equals(meaning, otherWord.meaning)
                && Objects.equals(phone, otherWord.phone)
                && Objects.equals(address, otherWord.address)
                && tagged.equals(otherWord.tagged);
    }
}
