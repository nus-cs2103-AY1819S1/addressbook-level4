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
        if (contains(toAdd)) {
            throw new DuplicateCategoryException();
        }
        internalList.add(toAdd);
    }

    @Override
    public Iterator<Category> iterator() {
        return internalList.iterator();
    }

    /**
     * Returns true if the category list contains an equivalent category as the given argument.
     */
    public boolean contains(Category toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameCategory);
    }

    /**
     * Returns true if the category list contains a category with same name as {@code name}.
     */
    public boolean contains(String name) {
        requireNonNull(name);
        Iterator catIterator = internalList.iterator();

        if (internalList.isEmpty()) {
            return false;
        }
        while (catIterator.hasNext()) {
            Category nextCategory = (Category) catIterator.next();
            if (name.equals(nextCategory.getName())) {
                return true;
            }
        }
        return false;
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
     * Replaces the contents of this list with {@code tasks}.
     * {@code tasks} must not contain duplicate tasks.
     */
    public void setCategories(List<Category> categories) {
        requireAllNonNull(categories);
        if (!categoriesAreUnique(categories)) {
            throw new DuplicateCategoryException();
        }
        internalList.setAll(categories);
    }

    /**
     * Remove selected category from schedule planner.
     */
    public void removeCategory(String name) {
        requireNonNull(name);
        Category category = getCategory(name);
        internalList.remove(category);
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



    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other instanceof UniqueCategoryList) {
            if (internalList.equals(((UniqueCategoryList) other).internalList)) {
                return true;
            }
            boolean isSameCategoryList = true;
            UniqueCategoryList otherCats = (UniqueCategoryList) other;
            Iterator catIterator = otherCats.internalList.iterator();
            while (catIterator.hasNext() && isSameCategoryList) {
                Category nextOtherCategory = (Category) catIterator.next();
                Category nextCategory = getCategory(nextOtherCategory.getName());
                isSameCategoryList = isSameCategoryList
                        && (nextOtherCategory.equals(nextCategory));
            }
            return isSameCategoryList;
        }
        return false;
    }

    public Category getCategory(String name) {
        Iterator catIterator = internalList.iterator();
        while (catIterator.hasNext()) {
            Category nextCat = (Category) catIterator.next();
            if (name.equals(nextCat.getName())) {
                return nextCat;
            }
        }
        return null;
    }

}
