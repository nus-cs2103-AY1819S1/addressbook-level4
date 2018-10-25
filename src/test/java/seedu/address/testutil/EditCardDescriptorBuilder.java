package seedu.address.testutil;

import seedu.address.logic.anakincommands.EditCardCommand;
import seedu.address.logic.anakincommands.EditCardCommand.EditCardDescriptor;
import seedu.address.model.anakindeck.Answer;
import seedu.address.model.anakindeck.Card;
import seedu.address.model.anakindeck.Question;

/**
 * A utility class to help with building EditCardDescriptor objects.
 */
public class EditCardDescriptorBuilder {

    private EditCardDescriptor descriptor;

    public EditCardDescriptorBuilder() {
        descriptor = new EditCardDescriptor();
    }

    public EditCardDescriptorBuilder(EditCardDescriptor descriptor) {
        this.descriptor = new EditCardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCardDescriptor} with fields containing {@code person}'s details
     */
    public EditCardDescriptorBuilder(Card card) {
        descriptor = new EditCardCommand.EditCardDescriptor();
        descriptor.setQuestion(card.getQuestion());
        descriptor.setAnswer(card.getAnswer());
    }

    /**
     * Sets the {@code Answer} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withAnswer(String ans) {
        descriptor.setAnswer(new Answer(ans));
        return this;
    }

    /**
     * Sets the {@code Question} of the {@code EditCardDescriptor} that we are building.
     */
    public EditCardDescriptorBuilder withQuestion(String question) {
        descriptor.setQuestion(new Question(question));
        return this;
    }


    public EditCardDescriptor build() {
        return descriptor;
    }
}
