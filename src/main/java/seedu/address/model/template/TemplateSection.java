package seedu.address.model.template;

import java.util.function.Predicate;

import seedu.address.model.category.Category;
import seedu.address.model.entry.Taggable;

/**
 * A section of the template, representing a category with tags.
 */
public class TemplateSection {
    private String title;
    private Category category;
    private Predicate<Taggable> predicate;

    public TemplateSection(String title, String cateName, Predicate<Taggable> predicate) {
        this.title = title;
        this.category = new Category(cateName);
        this.predicate = predicate;
    }

    public String getTitle() {
        return this.title;
    }

    public Category getCategory() {
        return this.category;
    }

    public Predicate<Taggable> getPredicate() {
        return this.predicate;
    }
}
