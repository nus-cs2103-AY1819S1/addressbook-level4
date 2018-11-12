package seedu.address.storage;

import static seedu.address.model.TaggedObject.MESSAGE_INVALID_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.common.Phone;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderDate;
import seedu.address.model.order.OrderStatus;

/**
 * JAXB-friendly version of the Order.
 */
public class XmlAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    @XmlAttribute (required = true)
    @XmlID
    private String tag;

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String phone;
    @XmlElement(required = true)
    private String address;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String status;
    @XmlElement(required = true)
    private List<XmlAdaptedFood> food = new ArrayList<>();
    @XmlElement
    private String deliveryman;

    /**
     * Constructs an XmlAdaptedOrder.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedOrder() {
    }

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given order details.
     */
    public XmlAdaptedOrder(String tag, String name, String phone, String address, String date, String status,
                           List<XmlAdaptedFood> food, String deliveryman) {
        this.tag = tag;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.date = date;
        this.status = status;
        this.deliveryman = deliveryman;

        if (food == null) {
            this.food = new ArrayList<>();
        } else {
            this.food = new ArrayList<>(food);
        }
    }

    /**
     * Constructs an {@code XmlAdaptedOrder} with the given order details.
     */
    public XmlAdaptedOrder(String name, String phone, String address, String date, String status,
                           List<XmlAdaptedFood> food, String deliveryman) {
        this(UUID.randomUUID().toString(), name, phone, address, date, status, food, deliveryman);
    }

    /**
     * Converts a given Order into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedOrder
     */
    public XmlAdaptedOrder(Order source) {
        tag = source.getTag().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        date = source.getDate().toString();
        status = source.getOrderStatus().toString();
        food = source.getFood().stream()
                .map(XmlAdaptedFood::new)
                .collect(Collectors.toList());
        if (source.getDeliveryman() != null) {
            deliveryman = source.getDeliveryman().getName().fullName;
        }
    }

    /**
     * Converts this jaxb-friendly adapted order object into the model's Order object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order
     */
    public Order toModelType() throws IllegalValueException {
        final List<Food> foodStore = new ArrayList<>();
        for (XmlAdaptedFood foodItem : food) {
            foodStore.add(foodItem.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

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


        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!OrderDate.isValidDate(date)) {
            throw new IllegalValueException(OrderDate.MESSAGE_DATE_CONSTRAINTS);
        }
        final OrderDate modelDate = new OrderDate(date);

        if (foodStore.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Food.class.getSimpleName()));
        }
        final Set<Food> modelFood = new HashSet<>(foodStore);

        if (tag == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Tag"));
        }

        UUID modelTag;
        try {
            modelTag = UUID.fromString(tag);
        } catch (NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_INVALID_ID);
        }

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrderStatus.class.getSimpleName()));
        }
        if (!OrderStatus.isValidStatus(status)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OrderStatus.class.getSimpleName()));
        }
        final OrderStatus orderStatus = new OrderStatus(status);

        final Deliveryman modelDeliveryman;
        if (deliveryman == null) {
            modelDeliveryman = null;
        } else {
            modelDeliveryman = new Deliveryman(new Name(deliveryman));
        }

        return new Order(modelTag, modelName, modelPhone, modelAddress, modelDate, orderStatus,
                modelFood, modelDeliveryman);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedOrder)) {
            return false;
        }

        XmlAdaptedOrder otherOrder = (XmlAdaptedOrder) other;
        return tag.equals(otherOrder.tag)
                && Objects.equals(name, otherOrder.name)
                && Objects.equals(phone, otherOrder.phone)
                && Objects.equals(address, otherOrder.address)
                && Objects.equals(date, otherOrder.date)
                && Objects.equals(status, otherOrder.status)
                && food.equals(otherOrder.food)
                && Objects.equals(deliveryman, otherOrder.deliveryman);
    }
}
