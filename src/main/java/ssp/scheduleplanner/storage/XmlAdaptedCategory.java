package ssp.scheduleplanner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import ssp.scheduleplanner.commons.exceptions.IllegalValueException;

import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.Tag;

/**
 * JAXB-friendly version of the Task.
 */
public class XmlAdaptedCategory {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Category's %s field is missing!";

    public static final String MESSAGE_DUPLICATE_TAG = "Tag list of the category contains duplicate tag(s).";

    @XmlElement(required = true)
    private String name;
    @XmlElement
    private List<XmlAdaptedTag> tags = new ArrayList();

    /**
     * Constructs an XmlAdaptedCategory.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedCategory() {
    }

    /**
     * Constructs an {@code XmlAdaptedCategory} with the given category details.
     */
    public XmlAdaptedCategory(String name, List<XmlAdaptedTag> tags) {
        this.name = name;
        this.tags = new ArrayList<>(tags);
    }

    /**
     * Converts a given Category into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedCategory
     */
    public XmlAdaptedCategory(Category source) {
        name = source.getName();
        tags.addAll(source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this jaxb-friendly adapted category object into the model's Category object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted category
     */
    public Category toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        Category category = new Category(name);

        for (XmlAdaptedTag tag : tags) {
            Tag modelTag = tag.toModelType();
            if (category.hasTag(modelTag)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TAG);
            }
            category.addTag(modelTag);
        }

        return category;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedCategory)) {
            return false;
        }

        boolean isSameCategory = true;
        if (name.equals(((XmlAdaptedCategory) other).name)) {
            if (tags.size() == ((XmlAdaptedCategory) other).tags.size()) {
                int i = 0;
                while (isSameCategory && (i < tags.size())) {
                    isSameCategory = isSameCategory
                            && (tags.contains(((XmlAdaptedCategory) other).tags.get(i)));
                    i++;
                }
                return isSameCategory;
            }
        }
        return false;
    }
}
