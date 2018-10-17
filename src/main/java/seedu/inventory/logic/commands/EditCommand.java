package seedu.inventory.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_IMAGE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_QUANTITY;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_SKU;
import static seedu.inventory.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.inventory.model.Model.PREDICATE_SHOW_ALL_ITEMS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.inventory.commons.core.Messages;
import seedu.inventory.commons.core.index.Index;
import seedu.inventory.commons.util.CollectionUtil;
import seedu.inventory.logic.CommandHistory;
import seedu.inventory.logic.commands.exceptions.CommandException;
import seedu.inventory.model.Model;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Item;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;

/**
 * Edits the details of an existing item in the inventory.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the item identified "
            + "by the index number used in the displayed item list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PRICE + "PRICE] "
            + "[" + PREFIX_QUANTITY + "QUANTITY] "
            + "[" + PREFIX_SKU + "SKU] "
            + "[" + PREFIX_IMAGE + "IMAGE_PATH] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_QUANTITY + "1000 "
            + PREFIX_SKU + "iphone-xr";

    public static final String MESSAGE_EDIT_ITEM_SUCCESS = "Edited Item: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_ITEM = "This item already exists in the inventory.";

    private final Index index;
    private final EditItemDescriptor editItemDescriptor;

    /**
     * @param index of the item in the filtered item list to edit
     * @param editItemDescriptor details to edit the item with
     */
    public EditCommand(Index index, EditItemDescriptor editItemDescriptor) {
        requireNonNull(index);
        requireNonNull(editItemDescriptor);

        this.index = index;
        this.editItemDescriptor = new EditItemDescriptor(editItemDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Item> lastShownList = model.getFilteredItemList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ITEM_DISPLAYED_INDEX);
        }

        Item itemToEdit = lastShownList.get(index.getZeroBased());
        Item editedItem = createEditedItem(itemToEdit, editItemDescriptor);

        if (!itemToEdit.isSameItem(editedItem) && model.hasItem(editedItem)) {
            throw new CommandException(MESSAGE_DUPLICATE_ITEM);
        }

        model.updateItem(itemToEdit, editedItem);
        model.updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS);
        model.commitInventory();
        return new CommandResult(String.format(MESSAGE_EDIT_ITEM_SUCCESS, editedItem));
    }

    /**
     * Creates and returns a {@code Item} with the details of {@code itemToEdit}
     * edited with {@code editItemDescriptor}.
     */
    private static Item createEditedItem(Item itemToEdit, EditItemDescriptor editItemDescriptor) {
        assert itemToEdit != null;

        Name updatedName = editItemDescriptor.getName().orElse(itemToEdit.getName());
        Price updatedPrice = editItemDescriptor.getPrice().orElse(itemToEdit.getPrice());
        Quantity updatedQuantity = editItemDescriptor.getQuantity().orElse(itemToEdit.getQuantity());
        Sku updatedSku = editItemDescriptor.getSku().orElse(itemToEdit.getSku());
        Image updatedImage = editItemDescriptor.getImage().orElse(itemToEdit.getImage());
        Set<Tag> updatedTags = editItemDescriptor.getTags().orElse(itemToEdit.getTags());

        return new Item(updatedName, updatedPrice, updatedQuantity, updatedSku, updatedImage, updatedTags);
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
                && editItemDescriptor.equals(e.editItemDescriptor);
    }

    /**
     * Stores the details to edit the item with. Each non-empty field value will replace the
     * corresponding field value of the item.
     */
    public static class EditItemDescriptor {
        private Name name;
        private Price price;
        private Quantity quantity;
        private Sku sku;
        private Image image;
        private Set<Tag> tags;

        public EditItemDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditItemDescriptor(EditItemDescriptor toCopy) {
            setName(toCopy.name);
            setPrice(toCopy.price);
            setQuantity(toCopy.quantity);
            setSku(toCopy.sku);
            setImage(toCopy.image);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, price, quantity, sku, image, tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPrice(Price price) {
            this.price = price;
        }

        public Optional<Price> getPrice() {
            return Optional.ofNullable(price);
        }

        public void setQuantity(Quantity quantity) {
            this.quantity = quantity;
        }

        public Optional<Quantity> getQuantity() {
            return Optional.ofNullable(quantity);
        }

        public void setSku(Sku sku) {
            this.sku = sku;
        }

        public Optional<Sku> getSku() {
            return Optional.ofNullable(sku);
        }

        public void setImage(Image image) {
            this.image = image;
        }

        public Optional<Image> getImage() {
            return Optional.ofNullable(image);
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
            if (!(other instanceof EditItemDescriptor)) {
                return false;
            }

            // state check
            EditItemDescriptor e = (EditItemDescriptor) other;

            return getName().equals(e.getName())
                    && getPrice().equals(e.getPrice())
                    && getQuantity().equals(e.getQuantity())
                    && getSku().equals(e.getSku())
                    && getImage().equals(e.getImage())
                    && getTags().equals(e.getTags());
        }
    }
}
