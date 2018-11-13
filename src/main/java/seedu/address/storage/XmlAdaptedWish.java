package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.wish.Date;
import seedu.address.model.wish.Name;
import seedu.address.model.wish.Price;
import seedu.address.model.wish.Remark;
import seedu.address.model.wish.SavedAmount;
import seedu.address.model.wish.Url;
import seedu.address.model.wish.Wish;

/**
 * JAXB-friendly version of the Wish.
 */
public class XmlAdaptedWish {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Wish's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String savedAmount;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String url;
    @XmlElement(required = true)
    private String remark;
    @XmlElement(required = true)
    private String id;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedWish.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedWish() {}

    /**
     * Constructs an {@code XmlAdaptedWish} with the given wish details.
     */
    public XmlAdaptedWish(String name, String price, String date, String url, List<XmlAdaptedTag> tagged, String id) {
        this.name = name;
        this.price = price;
        this.date = date;
        this.url = url;
        this.savedAmount = "0.00";
        this.remark = "";
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.id = id;
    }

    /**
     * Converts a given Wish into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedWish
     */
    public XmlAdaptedWish(Wish source) {
        name = source.getName().fullName;
        price = source.getPrice().toString();
        date = source.getDate().date;
        url = source.getUrl().value;
        savedAmount = source.getSavedAmount().toString();
        remark = source.getRemark().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        id = source.getId().toString();
    }

    /**
     * Converts this jaxb-friendly adapted wish object into the model's Wish object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted wish
     */
    public Wish toModelType() throws IllegalValueException {
        final List<Tag> wishTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            wishTags.add(tag.toModelType());
        }

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, UUID.class.getSimpleName()));
        }
        final UUID modelId = UUID.fromString(id);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        if (url == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Url.class.getSimpleName()));
        }
        if (!Url.isValidUrl(url)) {
            throw new IllegalValueException(Url.MESSAGE_URL_CONSTRAINTS);
        }
        final Url modelUrl = new Url(url);

        if (this.remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(this.remark);

        if (this.savedAmount == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    SavedAmount.class.getSimpleName()));
        }
        final SavedAmount modelSavedAmount = new SavedAmount(this.savedAmount);
        final Set<Tag> modelTags = new HashSet<>(wishTags);
        return new Wish(modelName, modelPrice, modelDate, modelUrl, modelSavedAmount, modelRemark, modelTags, modelId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedWish)) {
            return false;
        }

        XmlAdaptedWish otherWish = (XmlAdaptedWish) other;
        return Objects.equals(name, otherWish.name)
                && Objects.equals(price, otherWish.price)
                && Objects.equals(date, otherWish.date)
                && Objects.equals(url, otherWish.url)
                && Objects.equals(remark, otherWish.remark)
                && tagged.equals(otherWish.tagged)
                && id.equals(otherWish.id);
    }
}
