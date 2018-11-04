package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.UpdateCommand.EditCcaDescriptor;
import seedu.address.model.cca.Budget;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.cca.Outstanding;
import seedu.address.model.cca.Spent;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.Date;
import seedu.address.model.transaction.Entry;
import seedu.address.model.transaction.Remarks;

//@@author ericyjw

/**
 * A utility class to help with building EditCcaDescriptor objects.
 */
public class EditCcaDescriptorBuilder {

    private EditCcaDescriptor descriptor;

    public EditCcaDescriptorBuilder() {
        descriptor = new EditCcaDescriptor();
    }

    public EditCcaDescriptorBuilder(EditCcaDescriptor descriptor) {
        this.descriptor = new EditCcaDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCcaDescriptor} with fields containing {@code cca}'s details
     */
    public EditCcaDescriptorBuilder(Cca cca) {
        descriptor = new EditCcaDescriptor();
        descriptor.setCcaName(cca.getName());
        descriptor.setHead(cca.getHead());
        descriptor.setViceHead(cca.getViceHead());
        descriptor.setBudget(cca.getBudget());
        descriptor.setSpent(cca.getSpent());
        descriptor.setOutstanding(cca.getOutstanding());
        descriptor.setTransaction(cca.getEntries());
    }

    /**
     * Sets the {@code CcaName} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withCcaName(String name) {
        descriptor.setCcaName(new CcaName(name));
        return this;
    }

    /**
     * Sets the {@code Name} of the head of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withHead(String head) {
        descriptor.setHead(new Name(head));
        return this;
    }

    /**
     * Sets the {@code Name} of the vice-head of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withViceHead(String viceHead) {
        descriptor.setViceHead(new Name(viceHead));
        return this;
    }

    /**
     * Sets the {@code Budget} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withBudget(String budget) {
        descriptor.setBudget(new Budget(Integer.valueOf(budget)));
        return this;
    }

    /**
     * Sets the {@code Spent} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withSpent(String spent) {
        descriptor.setSpent(new Spent(Integer.valueOf(spent)));
        return this;
    }

    /**
     * Sets the {@code Outstanding} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withOustanding(String outstanding) {
        descriptor.setOutstanding(new Outstanding(Integer.valueOf(outstanding)));
        return this;
    }

    /**
     * Parses the transaction {@code entries} into a {@code Set<Entries>} and set it to the {@code EditCcaDescriptor}
     * that we are building.
     */
    public EditCcaDescriptorBuilder withEntries(Entry... entries) {
        Set<Entry> entrySet = Stream.of(entries).map(Entry::new).collect(Collectors.toSet());
        descriptor.setTransaction(entrySet);
        return this;
    }

    /**
     * Sets the Entry Number of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withEntryNum(int entryNum) {
        descriptor.setEntryNum(entryNum);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(Integer.valueOf(amount)));
        return this;
    }

    /**
     * Sets the {@code Remarks} of the {@code EditCcaDescriptor} that we are building.
     */
    public EditCcaDescriptorBuilder withRemarks(String remarks) {
        descriptor.setRemarks(new Remarks(remarks));
        return this;
    }


    public EditCcaDescriptor build() {
        return descriptor;
    }

}
