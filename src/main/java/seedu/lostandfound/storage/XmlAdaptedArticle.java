package seedu.lostandfound.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.lostandfound.commons.exceptions.IllegalValueException;
import seedu.lostandfound.model.article.Article;
import seedu.lostandfound.model.article.Description;
import seedu.lostandfound.model.article.Email;
import seedu.lostandfound.model.article.Name;
import seedu.lostandfound.model.article.Phone;
import seedu.lostandfound.model.image.Image;
import seedu.lostandfound.model.tag.Tag;

/**
 * JAXB-friendly version of the Article.
 */
public class XmlAdaptedArticle {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Article's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String email;
    @XmlElement(required = true)
    private String description;
    @XmlElement()
    private String image;
    @XmlElement(required = true)
    private String finder;
    @XmlElement(required = true)
    private String owner;
    @XmlElement(required = true)
    private boolean isResolved;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedArticle.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedArticle() {}

    /**
     * Constructs an {@code XmlAdaptedArticle} with the given article details.
     */
    public XmlAdaptedArticle(String name, String phone, String email, String description, String image, String finder,
                             String owner, boolean isResolved, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.description = description;
        this.image = image;
        this.finder = finder;
        this.owner = owner;
        this.isResolved = isResolved;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Article into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedArticle
     */
    public XmlAdaptedArticle(Article source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        description = source.getDescription().value;
        image = source.getImage().toString();
        finder = source.getFinder().fullName;
        owner = source.getOwner().fullName;
        isResolved = source.getIsResolved();
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted article object into the model's Article object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted article
     */
    public Article toModelType() throws IllegalValueException {
        final List<Tag> articleTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            articleTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValid(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValid(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValid(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (description == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName()));
        }
        if (!Description.isValid(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (image == null) {
            image = Image.getDefault().toString();
        }
        if (!Image.isValid(image)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Image modelImage = new Image(image);

        if (finder == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValid(finder)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelFinder = new Name(finder);

        if (owner == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValid(owner)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelOwner = new Name(owner);

        final boolean modelIsResolved = isResolved;

        final Set<Tag> modelTags = new HashSet<>(articleTags);

        return new Article(modelName, modelPhone, modelEmail, modelDescription, modelImage, modelFinder, modelOwner,
                modelIsResolved, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedArticle)) {
            return false;
        }

        XmlAdaptedArticle otherArticle = (XmlAdaptedArticle) other;
        return Objects.equals(name, otherArticle.name)
                && Objects.equals(phone, otherArticle.phone)
                && Objects.equals(email, otherArticle.email)
                && Objects.equals(description, otherArticle.description)
                && Objects.equals(image, otherArticle.image)
                && tagged.equals(otherArticle.tagged);
    }
}
