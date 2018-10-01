package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.carpark.Address;
import seedu.address.model.carpark.Carpark;
import seedu.address.model.carpark.CarparkNumber;
import seedu.address.model.carpark.LotsAvailable;
import seedu.address.model.carpark.TotalLots;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * JAXB-friendly version of the Person.
 */
public class XmlAdaptedCarpark {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    @XmlElement(required = true)
    private String carparkNumber;
    @XmlElement(required = true)
    private String lotsAvailable;
    @XmlElement(required = true)
    private String totalLots;
    @XmlElement(required = true)
    private String address;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedCarpark.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCarpark() {}

    /**
     * Constructs an {@code XmlAdaptedCarpark} with the given carpark details.
     */
    public XmlAdaptedCarpark(String carkparkNumber, String lotsAvailable, String lotType,
                             String totalLots, String address, List<XmlAdaptedTag> tagged) {
        this.carparkNumber = carkparkNumber;
        this.lotsAvailable = lotsAvailable;
        this.totalLots = totalLots;
        this.address = address;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }

    /**
     * Converts a given Person into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCarpark
     */
    public XmlAdaptedCarpark(Carpark source) {
        this.carparkNumber = source.getCarparkNumber().value;
        this.lotsAvailable = source.getLotsAvailable().value;
        this.totalLots = source.getTotalLots().value;
        this.address = source.getAddress().value;
        this.tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }

    /**
     * Converts this jaxb-friendly adapted carpark object into the model's Person object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted carpark
     */
    public Carpark toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (carparkNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Carpark.class.getSimpleName()));
        }
        if (!CarparkNumber.isValidCarNum(carparkNumber)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final CarparkNumber modelCarparkNumber = new CarparkNumber(carparkNumber);

        if (lotsAvailable == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LotsAvailable.class.getSimpleName()));
        }
        if (!LotsAvailable.isValidLotsAvail(lotsAvailable)) {
            throw new IllegalValueException(LotsAvailable.MESSAGE_LOTS_AVAIL_CONSTRAINTS);
        }
        final LotsAvailable modelLotsAvailable = new LotsAvailable (lotsAvailable);

        if (totalLots == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    TotalLots.class.getSimpleName()));
        }
        if (!TotalLots.isValidTotalLots(totalLots)) {
            throw new IllegalValueException(TotalLots.MESSAGE_TOTAL_LOTS_CONSTRAINTS);
        }
        final TotalLots modelTotalLots = new TotalLots(totalLots);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_ADDRESS_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Carpark(modelCarparkNumber, modelTotalLots, modelLotsAvailable,
                modelAddress, modelTags);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCarpark)) {
            return false;
        }

        XmlAdaptedCarpark otherPerson = (XmlAdaptedCarpark) other;
        return Objects.equals(carparkNumber, otherPerson.carparkNumber)
                && Objects.equals(totalLots, otherPerson.totalLots)
                && Objects.equals(lotsAvailable, otherPerson.lotsAvailable)
                && Objects.equals(address, otherPerson.address)
                && tagged.equals(otherPerson.tagged);
    }
}
