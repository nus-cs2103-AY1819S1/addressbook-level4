package seedu.address.storage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@@author snajef
/**
 * A JAXB-ready version of the PrescriptionList, for easy un/marshalling.
 * @author Darien Chong
 *
 */
@XmlRootElement
public class XmlAdaptedPrescriptionList implements Iterable<XmlAdaptedPrescription> {
    @XmlElement(required = true, name = "prescription")
    private List<XmlAdaptedPrescription> prescriptions;

    public XmlAdaptedPrescriptionList() {
        prescriptions = new ArrayList<>();
    }

    /** Defensive copy c'tor. */
    public XmlAdaptedPrescriptionList(List<XmlAdaptedPrescription> prescriptions) {
        this.prescriptions = new ArrayList<>(prescriptions);
    }

    /** Defensive copy c'tor. */
    public XmlAdaptedPrescriptionList(XmlAdaptedPrescriptionList prescriptions) {
        this(prescriptions.prescriptions);
    }

    /** Setter method to hot swap the internal list. */
    public void setPrescription(List<XmlAdaptedPrescription> prescriptions) {
        this.prescriptions = new ArrayList<>(prescriptions);
    }

    @Override
    public Iterator<XmlAdaptedPrescription> iterator() {
        return prescriptions.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof XmlAdaptedPrescriptionList)) {
            return false;
        }

        XmlAdaptedPrescriptionList xapxs = (XmlAdaptedPrescriptionList) o;
        return prescriptions.equals(xapxs.prescriptions);
    }
}
