package seedu.learnvocabulary.testutil;

import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_MEANING;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.learnvocabulary.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.learnvocabulary.logic.commands.AddCommand;
import seedu.learnvocabulary.logic.commands.EditCommand.EditWordDescriptor;
import seedu.learnvocabulary.model.tag.Tag;

import seedu.learnvocabulary.model.word.Word;

/**
 * A utility class for Word.
 */
public class WordUtil {

    /**
     * Returns an add command string for adding the {@code word}.
     */
    public static String getAddCommand(Word word) {
        return AddCommand.COMMAND_WORD + " " + getWordDetails(word);
    }

    /**
     * Returns the part of command string for the given {@code word}'s details.
     */
    public static String getWordDetails(Word word) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + word.getName().fullName + " ");
        sb.append(PREFIX_MEANING + word.getMeaning().fullMeaning + " ");
        word.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );

        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditWordDescriptor}'s details.
     */
    public static String getEditWordDescriptorDetails(EditWordDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getMeaning().ifPresent(meaning -> sb.append(PREFIX_MEANING).append(meaning.fullMeaning).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
