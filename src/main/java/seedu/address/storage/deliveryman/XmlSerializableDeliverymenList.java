package seedu.address.storage.deliveryman;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.deliveryman.Deliveryman;
import seedu.address.model.deliveryman.DeliverymenList;

/**
 * An Immutable DeliverymenList that is serializable to XML format
 */
public class XmlSerializableDeliverymenList {

    public static final String MESSAGE_DUPLICATE_DELIVERYMAN = "Deliverymen List contains duplicate deliverymen.";

    @XmlElement
    private List<XmlAdaptedDeliveryman> deliverymen;

    /**
     * Creates an empty XmlSerializableDeliverymenList.
     * Required for marshalling.
     */
    public XmlSerializableDeliverymenList() {
        deliverymen = new ArrayList<>();
    }

    /**
     * Converts DeliverymenList to Serializable
     */
    public XmlSerializableDeliverymenList(DeliverymenList src) {
        this();
        deliverymen.addAll(src.getDeliverymenList().stream().map(XmlAdaptedDeliveryman::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this deliverymen list into the model's {@code DeliverymenList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or dupicates
     */
    public DeliverymenList toModelType() throws IllegalValueException {
        DeliverymenList deliverymenList = new DeliverymenList();
        for (XmlAdaptedDeliveryman d : deliverymen) {
            Deliveryman dMan = d.toModelType();
            if (deliverymenList.hasDeliveryman(dMan)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_DELIVERYMAN);
            }
            deliverymenList.addDeliveryman(dMan);
        }
        return deliverymenList;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableDeliverymenList)) {
            return false;
        }
        return deliverymen.equals(((XmlSerializableDeliverymenList) other).deliverymen);
    }
}
