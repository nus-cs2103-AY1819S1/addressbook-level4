package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FOOD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Set;

import seedu.address.logic.commands.order.AddCommand;
import seedu.address.logic.commands.order.EditCommand.EditOrderDescriptor;
import seedu.address.model.order.Food;
import seedu.address.model.order.Order;


/**
 * A utility class for Order.
 */
public class OrderUtil {

    /**
     * Returns an add command string for adding the {@code order}.
     */
    public static String getAddCommand(Order order) {
        return AddCommand.COMMAND_WORD + " " + getOrderDetails(order);
    }

    /**
     * Returns the part of command string for the given {@code order}'s details.
     */
    public static String getOrderDetails(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + order.getName().fullName + " ");
        sb.append(PREFIX_PHONE + order.getPhone().value + " ");
        sb.append(PREFIX_ADDRESS + order.getAddress().value + " ");
        sb.append(PREFIX_DATE + order.getDate().toString() + " ");
        order.getFood().stream().forEach(
            s -> sb.append(PREFIX_FOOD + s.foodName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditOrderDescriptor}'s details.
     */
    public static String getEditOrderDescriptorDetails(EditOrderDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE).append(date).append(" "));
        if (descriptor.getFood().isPresent()) {
            Set<Food> food = descriptor.getFood().get();
            if (food.isEmpty()) {
                sb.append(PREFIX_FOOD);
            } else {
                food.forEach(s -> sb.append(PREFIX_FOOD).append(s.foodName).append(" "));
            }
        }
        return sb.toString();
    }

}
