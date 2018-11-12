package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.order.Order;

/**
 * An Immutable OrderBook that is serializable to XML format
 */
public class XmlSerializableOrderBook {

    public static final String MESSAGE_DUPLICATE_ORDER = "Orders list contains duplicate order(s).";

    @XmlElement
    private List<XmlAdaptedOrder> orders;

    /**
     * Creates an empty XmlSerializableOrderBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableOrderBook() {
        orders = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableOrderBook(ReadOnlyOrderBook src) {
        this();
        orders.addAll(src.getOrderList().stream().map(XmlAdaptedOrder::new).collect(Collectors.toList()));
    }

    /**
     * Converts this orderbook into the model's {@code OrderBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     *                               {@code XmlAdaptedOrder}.
     */
    public OrderBook toModelType() throws IllegalValueException {
        OrderBook orderBook = new OrderBook();
        for (XmlAdaptedOrder o : orders) {
            Order order = o.toModelType();
            if (orderBook.hasOrder(order)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ORDER);
            }
            orderBook.addOrder(order);
        }
        return orderBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableOrderBook)) {
            return false;
        }
        return orders.equals(((XmlSerializableOrderBook) other).orders);
    }
}
