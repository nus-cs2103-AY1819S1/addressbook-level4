package seedu.address.storage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.OrderBook;
import seedu.address.model.ReadOnlyOrderBook;
import seedu.address.model.deliveryman.DeliverymenList;
import seedu.address.storage.deliveryman.XmlSerializableDeliverymenList;

/**
 * JAXB storage of FoodZoom information - namely an orderBook and a deliverymenList.
 */
@XmlRootElement(name = "foodzoom")
public class XmlFoodZoom {
    @XmlElement(required = true)
    private XmlSerializableOrderBook orderBook;

    @XmlElement(required = true)
    private XmlSerializableDeliverymenList deliverymenList;

    public XmlFoodZoom() {
        orderBook = new XmlSerializableOrderBook();
        deliverymenList = new XmlSerializableDeliverymenList();
    }

    public XmlFoodZoom(ReadOnlyOrderBook ordersSrc, DeliverymenList deliverymenSrc) {
        orderBook = new XmlSerializableOrderBook(ordersSrc);
        deliverymenList = new XmlSerializableDeliverymenList(deliverymenSrc);
    }

    public OrderBook getOrderBook() throws IllegalValueException {
        return orderBook.toModelType();
    }

    public DeliverymenList getDeliverymenList() throws IllegalValueException {
        return deliverymenList.toModelType();
    }
}
