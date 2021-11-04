package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Name;
import seedu.address.model.order.Order;
import seedu.address.model.product.Quantity;

/**
 * Jackson-friendly version of {@link Order}.
 */
public class JsonAdaptedOrder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String productName;
    private final String quantity;
    private final LocalDate time;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given {@code id} and {@code quantity}, {@code time}.
     */
    @JsonCreator
    private JsonAdaptedOrder(@JsonProperty("productName") String productName,
                             @JsonProperty("quantity") String quantity,
                             @JsonProperty("time") LocalDate time) {
        this.productName = productName;
        this.quantity = quantity;
        this.time = time;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        this(source.getProductName().fullName, source.getQuantity().value, source.getTime());
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     */
    public Order toModelType() throws IllegalValueException {
        final Name modelProductName;
        if (productName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        } else {
            modelProductName = new Name(productName);
        }

        final Quantity modelQuantity;
        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        } else {
            modelQuantity = new Quantity(quantity);
        }

        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }

        return new Order(modelProductName, modelQuantity, time);
    }
}
