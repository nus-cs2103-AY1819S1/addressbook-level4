package seedu.parking.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.parking.commons.exceptions.IllegalValueException;
import seedu.parking.model.CarparkFinder;
import seedu.parking.model.ReadOnlyCarparkFinder;
import seedu.parking.model.carpark.Carpark;

/**
 * An Immutable CarparkFinder that is serializable to XML format
 */
@XmlRootElement(name = "carparkfinder")
public class XmlSerializableCarparkFinder {

    public static final String MESSAGE_DUPLICATE_CARPARK = "Car parks list contains duplicate car park(s).";

    @XmlElement
    private List<XmlAdaptedCarpark> carparks;

    /**
     * Creates an empty XmlSerializableCarparkFinder.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableCarparkFinder() {
        carparks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableCarparkFinder(ReadOnlyCarparkFinder src) {
        this();
        carparks.addAll(src.getCarparkList().stream().map(XmlAdaptedCarpark::new).collect(Collectors.toList()));
    }

    /**
     * Converts this carparkfinder into the model's {@code CarparkFinder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedCarpark}.
     */
    public CarparkFinder toModelType() throws IllegalValueException {
        CarparkFinder carparkFinder = new CarparkFinder();
        for (XmlAdaptedCarpark c : carparks) {
            Carpark carpark = c.toModelType();
            if (carparkFinder.hasCarpark(carpark)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_CARPARK);
            }
            carparkFinder.addCarpark(carpark);
        }
        return carparkFinder;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableCarparkFinder)) {
            return false;
        }
        return carparks.equals(((XmlSerializableCarparkFinder) other).carparks);
    }
}
