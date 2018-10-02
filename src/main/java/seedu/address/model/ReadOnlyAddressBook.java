package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.article.Article;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the articles list.
     * This list will not contain any duplicate articles.
     */
    ObservableList<Article> getArticleList();

}
