package seedu.address.model;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.expense.Category;
import seedu.address.model.expense.Cost;
import seedu.address.model.expense.Date;
import seedu.address.model.expense.Name;
import seedu.address.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Stores the details to edit the expense with. Each non-empty field value will replace the
 * corresponding field value of the expense.
 */
public class EditExpenseDescriptor {
    private Name name;
    private Category category;
    private Cost cost;
    private Set<Tag> tags;
    private Date date;

    public EditExpenseDescriptor() {}

    /**
     * Copy constructor.
     * A defensive copy of {@code tags} is used internally.
     */
    public EditExpenseDescriptor(EditExpenseDescriptor toCopy) {
        setName(toCopy.name);
        setCategory(toCopy.category);
        setCost(toCopy.cost);
        setTags(toCopy.tags);
        setDate(toCopy.date);
    }

    /**
     * Returns true if at least one field is edited.
     */
    public boolean isAnyFieldEdited() {
        return CollectionUtil.isAnyNonNull(name, category, cost, tags);
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Optional<Name> getName() {
        return Optional.ofNullable(name);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Optional<Date> getDate() {
        return Optional.ofNullable(date);
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Optional<Category> getCategory() {
        return Optional.ofNullable(category);
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Optional<Cost> getCost() {
        return Optional.ofNullable(cost);
    }

    /**
     * Sets {@code tags} to this object's {@code tags}.
     * A defensive copy of {@code tags} is used internally.
     */
    public void setTags(Set<Tag> tags) {
        this.tags = (tags != null) ? new HashSet<>(tags) : null;
    }

    /**
     * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     * Returns {@code Optional#empty()} if {@code tags} is null.
     */
    public Optional<Set<Tag>> getTags() {
        return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditExpenseDescriptor)) {
            return false;
        }

        // state check
        EditExpenseDescriptor e = (EditExpenseDescriptor) other;

        return getName().equals(e.getName())
                && getCategory().equals(e.getCategory())
                && getCost().equals(e.getCost())
                && getTags().equals(e.getTags())
                && getDate().equals(e.getDate());
    }
}
