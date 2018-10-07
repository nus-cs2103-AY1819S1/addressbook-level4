package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalArticles.ALICE;
import static seedu.address.testutil.TypicalArticles.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.article.Article;
import seedu.address.model.article.exceptions.DuplicateArticleException;
import seedu.address.testutil.ArticleBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getArticleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateArticles_throwsDuplicateArticleException() {
        // Two articles with the same identity fields
        Article editedAlice = new ArticleBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Article> newArticles = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newArticles);

        thrown.expect(DuplicateArticleException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasArticle_nullArticle_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasArticle(null);
    }

    @Test
    public void hasArticle_articleNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasArticle(ALICE));
    }

    @Test
    public void hasArticle_articleInAddressBook_returnsTrue() {
        addressBook.addArticle(ALICE);
        assertTrue(addressBook.hasArticle(ALICE));
    }

    @Test
    public void hasArticle_articleWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addArticle(ALICE);
        Article editedAlice = new ArticleBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasArticle(editedAlice));
    }

    @Test
    public void getArticleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getArticleList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose articles list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Article> articles = FXCollections.observableArrayList();

        AddressBookStub(Collection<Article> articles) {
            this.articles.setAll(articles);
        }

        @Override
        public ObservableList<Article> getArticleList() {
            return articles;
        }
    }

}
