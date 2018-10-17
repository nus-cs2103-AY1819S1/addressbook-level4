package seedu.inventory.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;

/**
 * JAXB-friendly version of the Item.
 */
public class XmlAdaptedItem {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Item's %s field is missing!";

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String price;
    @XmlElement(required = true)
    private String quantity;
    @XmlElement(required = true)
    private String sku;
    @XmlElement(required = true)
    private String image;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedItem.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedItem() {}

    /**
     * Constructs an {@code XmlAdaptedItem} with the given item details.
     */
    public XmlAdaptedItem(String name, String price, String quantity, String sku,
            String image, List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sku = sku;
        this.image = image;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Item into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedItem
     */
    public XmlAdaptedItem(Item source) {
        name = source.getName().fullName;
        price = source.getPrice().value;
        quantity = source.getQuantity().value;
        sku = source.getSku().value;
        image = source.getImage().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted item object into the model's Item object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted item
     */
    public Item toModelType() throws IllegalValueException {
        final List<Tag> itemTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            itemTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (price == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName())
            );
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_PRICE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (quantity == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName())
            );
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_QUANTITY_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (sku == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName()));
        }
        if (!Sku.isValidSku(sku)) {
            throw new IllegalValueException(Sku.MESSAGE_SKU_CONSTRAINTS);
        }
        final Sku modelSku = new Sku(sku);

        if (image == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Image.class.getSimpleName()));
        }
        if (!Image.isValidImage(image)) {
            throw new IllegalValueException(Image.MESSAGE_IMAGE_CONSTRAINTS);
        }
        final Image modelImage = new Image(image);

        final Set<Tag> modelTags = new HashSet<>(itemTags);
        return new Item(modelName, modelPrice, modelQuantity, modelSku, modelImage, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedItem)) {
            return false;
        }

        XmlAdaptedItem otherItem = (XmlAdaptedItem) other;
        return Objects.equals(name, otherItem.name)
                && Objects.equals(price, otherItem.price)
                && Objects.equals(quantity, otherItem.quantity)
                && Objects.equals(sku, otherItem.sku)
                && Objects.equals(image, otherItem.image)
                && tagged.equals(otherItem.tagged);
    }
}
