package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BudgetBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.tag.Tag;

/**
 * An Immutable BudgetBook that is serializable to XML format
 */
@XmlRootElement(name = "budgetbook")
public class XmlSerializableBudgetBook {

    public static final String MESSAGE_DUPLICATE_TAG = "Budget list contains duplicate CCA tag(s).";

    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableBudgetBook.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableBudgetBook() {
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableBudgetBook(ReadOnlyBudgetBook src) {
        this();
        tags.addAll(src.getCcaTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    /**
     * Converts this budgetbook into the model's {@code BudgetBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedTag}.
     */
    public BudgetBook toModelType() throws IllegalValueException {
        BudgetBook budgetBook = new BudgetBook();
        for (XmlAdaptedTag t : tags) {
            Tag tag = t.toModelType();
            if (budgetBook.hasTag(tag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            budgetBook.addTag(tag);
        }
        return budgetBook;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableBudgetBook)) {
            return false;
        }
        return tags.equals(((XmlSerializableBudgetBook) other).tags);
    }
}
