//@@theJrLinguist
package seedu.address.storage;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

import javax.xml.bind.annotation.XmlValue;

public class XmlPersonIndex {

    @XmlValue
    private String index;

    private static ObservableList<Person> personList;

    public static void setPersonList(ObservableList<Person> organiserPersonList) {
        personList = organiserPersonList;
    }

    /**
     * Constructs an XmlPersonIndex.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlPersonIndex() {}

    /**
     * Constructs a {@code XmlPersonIndex} with the given {@code index}.
     */
    public XmlPersonIndex(String index) {
        this.index = index;
    }

    /**
     * Returns a model Person.
     */
    public Person toModelType() throws IllegalValueException  {
        try {
            return personList.get(Integer.valueOf(index));
        } catch (IndexOutOfBoundsException e){
            throw new IllegalValueException("No such person exists in the event organiser");
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlPersonIndex)) {
            return false;
        }

        return index.equals(((XmlPersonIndex) other).index);
    }
}
