package seedu.address.model.order;

import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

import seedu.address.model.Model;
import seedu.address.model.commons.ID;
import seedu.address.model.product.Product;
import seedu.address.model.product.Quantity;

/**
 * Represents an Order in Sellah.
 */
public class Order {
    public static final String REGEX = "\\d+ \\d+ (\\d{4}/)?\\d{0,2}/\\d{0,2}";

    public static final String MESSAGE_CONSTRAINTS =
            "Please follow the format for orders: -o PRODUCT_ID QUANTITY TIME\n"
                    + "Valid formats of time: MM/DD, YYYY/MM/DD\n"
                    + "Example: -o 10312 20 2021/10/20";

    public static final String MESSAGE_CONSTRAINTS_ID = "The product with given ID doesn't exist.";
    public static final String MESSAGE_CONSTRAINTS_QUANTITY = "There is not enough stock for the requested product.";

    private static final Quantity QUANTITY_ZERO = new Quantity("0");

    public final ID id;
    public final Quantity quantity;
    public final LocalDate time;

    /**
     * Constructor of {@code Order}
     */
    public Order(ID id, Quantity quantity, LocalDate time, Model model) {
        checkArgument(isValidProductID(id, model), MESSAGE_CONSTRAINTS_ID);

        Product product = model.getProductById(id);

        checkArgument(isValidQuantity(quantity, product), MESSAGE_CONSTRAINTS_QUANTITY);

        this.id = id;
        this.quantity = quantity;
        this.time = time;
    }

    private static boolean isValidProductID(ID id, Model model) {
        return model.hasProduct(id);
    }

    private static boolean isValidQuantity(Quantity quantity, Product product) {
        return product.hasEnoughStock(quantity);
    }

    public static boolean isPositiveQuantity(Quantity quantity) {
        return quantity.moreThan(QUANTITY_ZERO);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return id.equals(otherOrder.id)
                       && quantity.equals(otherOrder.quantity)
                       && time.equals(otherOrder.time);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return "[ Product ID: " + id + ", Quantity: " + quantity + ", Time " + time + "]";
    }
}
