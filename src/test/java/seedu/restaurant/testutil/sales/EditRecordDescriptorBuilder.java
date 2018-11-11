package seedu.restaurant.testutil.sales;

import seedu.restaurant.logic.commands.sales.EditSalesCommand.EditRecordDescriptor;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.model.sales.SalesRecord;

//@@author HyperionNKJ
/**
 * A utility class to help with building EditRecordDescriptor objects.
 */
public class EditRecordDescriptorBuilder {

    private EditRecordDescriptor descriptor;

    public EditRecordDescriptorBuilder() {
        descriptor = new EditRecordDescriptor();
    }

    public EditRecordDescriptorBuilder(EditRecordDescriptor descriptor) {
        this.descriptor = new EditRecordDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecordDescriptor} with fields containing {@code salesRecord}'s details
     */
    public EditRecordDescriptorBuilder(SalesRecord salesRecord) {
        descriptor = new EditRecordDescriptor();
        descriptor.setDate(salesRecord.getDate());
        descriptor.setName(salesRecord.getName());
        descriptor.setQuantitySold(salesRecord.getQuantitySold());
        descriptor.setPrice(salesRecord.getPrice());
    }

    /**
     * Sets the {@code Date} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withName(String name) {
        descriptor.setName(new ItemName(name));
        return this;
    }

    /**
     * Sets the {@code QuantitySold} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withQuantitySold(String quantitySold) {
        descriptor.setQuantitySold(new QuantitySold(quantitySold));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price));
        return this;
    }

    public EditRecordDescriptor build() {
        return descriptor;
    }
}
