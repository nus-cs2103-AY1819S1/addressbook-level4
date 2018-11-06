package ssp.scheduleplanner.testutil;

import ssp.scheduleplanner.model.category.Category;
import ssp.scheduleplanner.model.tag.UniqueTagList;

/**
 * A utility class to help with building Task objects.
 */
public class CategoryBuilder {

    public static final String DEFAULT_NAME = "Life";

    private String name;
    private UniqueTagList tags;

    public CategoryBuilder() {
        name = DEFAULT_NAME;
        tags = new UniqueTagList();
    }

    /**
     * Initializes the CategoryBuilder with the data of {@code categoryToCopy}.
     */
    public CategoryBuilder(Category categoryToCopy) {
        name = categoryToCopy.getName();
        tags = categoryToCopy.getUniqueTagList();
    }

    /**
     * Sets the {@code Name} of the {@code Category} that we are building.
     */
    public CategoryBuilder withName(String name) {
        this.name = name;
        this.tags = new UniqueTagList();
        return this;
    }

    public Category build() {
        return new Category(name);
    }

}
