package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ARTICLES;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.article.Address;
import seedu.address.model.article.Article;
import seedu.address.model.article.Email;
import seedu.address.model.article.Name;
import seedu.address.model.article.Phone;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing article in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the article identified "
            + "by the index number used in the displayed article list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_ARTICLE_SUCCESS = "Edited Article: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ARTICLE = "This article already exists in the address book.";

    private final Index index;
    private final EditArticleDescriptor editArticleDescriptor;

    /**
     * @param index of the article in the filtered article list to edit
     * @param editArticleDescriptor details to edit the article with
     */
    public EditCommand(Index index, EditArticleDescriptor editArticleDescriptor) {
        requireNonNull(index);
        requireNonNull(editArticleDescriptor);

        this.index = index;
        this.editArticleDescriptor = new EditArticleDescriptor(editArticleDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Article> lastShownList = model.getFilteredArticleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ARTICLE_DISPLAYED_INDEX);
        }

        Article articleToEdit = lastShownList.get(index.getZeroBased());
        Article editedArticle = createEditedArticle(articleToEdit, editArticleDescriptor);

        if (!articleToEdit.isSameArticle(editedArticle) && model.hasArticle(editedArticle)) {
            throw new CommandException(MESSAGE_DUPLICATE_ARTICLE);
        }

        model.updateArticle(articleToEdit, editedArticle);
        model.updateFilteredArticleList(PREDICATE_SHOW_ALL_ARTICLES);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_EDIT_ARTICLE_SUCCESS, editedArticle));
    }

    /**
     * Creates and returns a {@code Article} with the details of {@code articleToEdit}
     * edited with {@code editArticleDescriptor}.
     */
    private static Article createEditedArticle(Article articleToEdit, EditArticleDescriptor editArticleDescriptor) {
        assert articleToEdit != null;

        Name updatedName = editArticleDescriptor.getName().orElse(articleToEdit.getName());
        Phone updatedPhone = editArticleDescriptor.getPhone().orElse(articleToEdit.getPhone());
        Email updatedEmail = editArticleDescriptor.getEmail().orElse(articleToEdit.getEmail());
        Address updatedAddress = editArticleDescriptor.getAddress().orElse(articleToEdit.getAddress());
        Set<Tag> updatedTags = editArticleDescriptor.getTags().orElse(articleToEdit.getTags());

        return new Article(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editArticleDescriptor.equals(e.editArticleDescriptor);
    }

    /**
     * Stores the details to edit the article with. Each non-empty field value will replace the
     * corresponding field value of the article.
     */
    public static class EditArticleDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditArticleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditArticleDescriptor(EditArticleDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
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
            if (!(other instanceof EditArticleDescriptor)) {
                return false;
            }

            // state check
            EditArticleDescriptor e = (EditArticleDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags());
        }
    }
}
