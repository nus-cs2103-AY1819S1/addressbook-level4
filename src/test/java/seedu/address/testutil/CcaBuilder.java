package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import seedu.address.model.budget.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building Cca objects.
 */
//@@author javenseow
public class CcaBuilder {
    public static final String DEFAULT_CCANAME = "jcrc";
    public static final Person DEFAULT_HEAD = CARL;
    public static final Person DEFAULT_VICEHEAD = DANIEL;
    public static final int DEFAULT_BUDGET = 500;

    private Tag ccaName;
    private Person head;
    private Person viceHead;
    private Budget budget;

    public CcaBuilder() {
        this.ccaName = new Tag(DEFAULT_CCANAME);
        this.head = new PersonBuilder(DEFAULT_HEAD).build();
        this.viceHead = new PersonBuilder(DEFAULT_VICEHEAD).build();
        this.budget = new Budget(DEFAULT_BUDGET);
    }

    /**
     * Sets the cca name of the {@code Cca} we are building.
     */
    public CcaBuilder withCcaName(String ccaName) {
        this.ccaName = new Tag(ccaName);
        return this;
    }

    /**
     * Sets the head of the {@code Cca} we are building.
     */
    public CcaBuilder withHead(Person head) {
        this.head = new PersonBuilder(head).build();
        return this;
    }

    /**
     * Sets the vice head of the {@code Cca} we are building.
     */
    public CcaBuilder withViceHead(Person viceHead) {
        this.viceHead = new PersonBuilder(viceHead).build();
        return this;
    }

    /**
     * Sets the {@code Budget} of the {@code Cca} we are building.
     */
    public CcaBuilder withBudget(int budget) {
        this.budget = new Budget(budget);
        return this;
    }

    public Cca build() {
        return new Cca(ccaName, head, viceHead, budget);
    }
}
