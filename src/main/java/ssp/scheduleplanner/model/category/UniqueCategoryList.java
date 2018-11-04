package ssp.scheduleplanner.model.category;

import static java.util.Objects.requireNonNull;
import static ssp.scheduleplanner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import ssp.scheduleplanner.model.category.exceptions.DuplicateCategoryException;

/**
 * UniqueCategoryList which contains categories of this schedule planner.
 * Duplicate categories are not allowed in UniqueCategoryList.
 */
public class UniqueCategoryList implements Iterable<Category> {
    private ObservableList<Category> internalList = FXCollections.observableArrayList();

    public ObservableList<Category> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }
    /**
     * Adds a category to the list.
     */
    public void add(Category toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    /**
     * Replaces the contents of this list with {@code categories}.
     * {@code categories} must not contain duplicate categories.
     */
    public void setCategories(UniqueCategoryList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Returns true if {@code categories} contains only unique categories.
     */
    private boolean categoriesAreUnique(List<Category> categories) {
        for (int i = 0; i < categories.size() - 1; i++) {
            for (int j = i + 1; j < categories.size(); j++) {
                if (categories.get(i).isSameCategory(categories.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setTags(List<Category> categories) {
        requireAllNonNull(categories);
        if (!categoriesAreUnique(categories)) {
            throw new DuplicateCategoryException();
        }
        internalList.setAll(categories);
    }


}
