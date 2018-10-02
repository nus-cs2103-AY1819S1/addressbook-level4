package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.article.Article;
import seedu.address.model.article.UniqueArticleList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameArticle comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueArticleList articles;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        articles = new UniqueArticleList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Articles in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the article list with {@code articles}.
     * {@code articles} must not contain duplicate articles.
     */
    public void setArticles(List<Article> articles) {
        this.articles.setArticles(articles);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setArticles(newData.getArticleList());
    }

    //// article-level operations

    /**
     * Returns true if a article with the same identity as {@code article} exists in the address book.
     */
    public boolean hasArticle(Article article) {
        requireNonNull(article);
        return articles.contains(article);
    }

    /**
     * Adds a article to the address book.
     * The article must not already exist in the address book.
     */
    public void addArticle(Article p) {
        articles.add(p);
    }

    /**
     * Replaces the given article {@code target} in the list with {@code editedArticle}.
     * {@code target} must exist in the address book.
     * The article identity of {@code editedArticle} must not be the same as another existing article in the address book.
     */
    public void updateArticle(Article target, Article editedArticle) {
        requireNonNull(editedArticle);

        articles.setArticle(target, editedArticle);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeArticle(Article key) {
        articles.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return articles.asUnmodifiableObservableList().size() + " articles";
        // TODO: refine later
    }

    @Override
    public ObservableList<Article> getArticleList() {
        return articles.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && articles.equals(((AddressBook) other).articles));
    }

    @Override
    public int hashCode() {
        return articles.hashCode();
    }
}
