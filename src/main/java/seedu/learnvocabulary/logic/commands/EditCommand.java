package seedu.learnvocabulary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.learnvocabulary.model.Model.PREDICATE_SHOW_ALL_WORDS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.learnvocabulary.commons.core.Messages;
import seedu.learnvocabulary.commons.core.index.Index;
import seedu.learnvocabulary.commons.util.CollectionUtil;
import seedu.learnvocabulary.logic.CommandHistory;
import seedu.learnvocabulary.logic.commands.exceptions.CommandException;
import seedu.learnvocabulary.model.Model;
import seedu.learnvocabulary.model.tag.Tag;
import seedu.learnvocabulary.model.word.Meaning;
import seedu.learnvocabulary.model.word.Name;
import seedu.learnvocabulary.model.word.Word;

/**
 * Edits the details of an existing word in LearnVocabulary.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the word identified "
            + "by the index number used in the displayed word list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_MEANING + "MEANING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_EDIT_WORD_SUCCESS = "Edited Word: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_WORD = "This word already exists in LearnVocabulary.";
    public static final String MESSAGE_EMPTY_TAGS = "Words with empty tags are not allowed. "
            + "Must at least have 1 tag.";

    //@@author Harryqu123
    public static final String MESSAGE_NO_GROUP = "The group typed does not exist.";
    //@@author
    private final Index index;
    private final EditWordDescriptor editWordDescriptor;

    /**
     * @param index of the word in the filtered word list to edit
     * @param editWordDescriptor details to edit the word with
     */
    public EditCommand(Index index, EditWordDescriptor editWordDescriptor) {
        requireNonNull(index);
        requireNonNull(editWordDescriptor);

        this.index = index;
        this.editWordDescriptor = new EditWordDescriptor(editWordDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Word> lastShownList = model.getFilteredWordList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_WORDS_DISPLAYED_INDEX);
        }

        Word wordToEdit = lastShownList.get(index.getZeroBased());
        Word editedWord = createEditedWord(wordToEdit, editWordDescriptor);
        //@@author Harryqu123
        if (!model.hasTag(editedWord.getTags())) {
            throw new CommandException(MESSAGE_NO_GROUP);
        }

        if (editedWord.getTags().isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_TAGS);
        }
        //@@author
        if (!wordToEdit.isSameWord(editedWord) && model.hasWord(editedWord)) {
            throw new CommandException(MESSAGE_DUPLICATE_WORD);
        }

        model.updateWord(wordToEdit, editedWord);
        model.updateFilteredWordList(PREDICATE_SHOW_ALL_WORDS);
        model.commitLearnVocabulary();
        return new CommandResult(String.format(MESSAGE_EDIT_WORD_SUCCESS, editedWord));
    }

    /**
     * Creates and returns a {@code Word} with the details of {@code wordToEdit}
     * edited with {@code editWordDescriptor}.
     */
    private static Word createEditedWord(Word wordToEdit, EditWordDescriptor editWordDescriptor) {
        assert wordToEdit != null;

        Name updatedName = editWordDescriptor.getName().orElse(wordToEdit.getName());
        Meaning updatedMeaning = editWordDescriptor.getMeaning().orElse(wordToEdit.getMeaning());
        Set<Tag> updatedTags = editWordDescriptor.getTags().orElse(wordToEdit.getTags());

        return new Word(updatedName, updatedMeaning, updatedTags);
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
                && editWordDescriptor.equals(e.editWordDescriptor);
    }

    /**
     * Stores the details to edit the word with. Each non-empty field value will replace the
     * corresponding field value of the word.
     */
    public static class EditWordDescriptor {
        private Name name;
        private Meaning meaning;
        private Set<Tag> tags;

        public EditWordDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditWordDescriptor(EditWordDescriptor toCopy) {
            setName(toCopy.name);
            setMeaning(toCopy.meaning);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, meaning, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setMeaning(Meaning meaning) {
            this.meaning = meaning;
        }

        public Optional<Meaning> getMeaning() {
            return Optional.ofNullable(meaning);
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
            if (!(other instanceof EditWordDescriptor)) {
                return false;
            }

            // state check
            EditWordDescriptor e = (EditWordDescriptor) other;

            return getName().equals(e.getName())
                    //Russell review
                    && getMeaning().equals(e.getMeaning())
                    && getTags().equals(e.getTags());
        }
    }
}
