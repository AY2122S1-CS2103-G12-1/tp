package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commons.Name;
import seedu.address.model.product.Product;
import seedu.address.model.product.Quantity;
import seedu.address.model.product.UnitPrice;

/**
 * Jackson-friendly version of {@link Product}.
 */
public class JsonAdaptedProduct {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Product's %s field is missing!";

    private final String name;
    private final String unitPrice;
    private final String quantity;

    /**
     * Constructs a {@code JsonAdaptedProduct} with the given product details.
     */
    @JsonCreator
    public JsonAdaptedProduct(@JsonProperty("name") String name,
                              @JsonProperty("unitPrice") String unitPrice,
                              @JsonProperty("quantity") String quantity) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    /**
     * Converts a given {@code Product} into this class for Jackson use.
     */
    public JsonAdaptedProduct(Product source) {
        name = source.getName().fullName;
        unitPrice = source.getUnitPrice().value;
        quantity = isNull(source.getQuantity()) ? null : source.getQuantity().value;
    }

    private <T> boolean isNull(T obj) {
        return obj == null;
    }

    /**
     * Converts this Jackson-friendly adapted product object into the model's {@code Product} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted product.
     */
    public Product toModelType() throws IllegalValueException {
        final Name modelName;
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        } else {
            modelName = new Name(name);
        }

        final UnitPrice modelUnitPrice;
        if (unitPrice == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    UnitPrice.class.getSimpleName()));
        } else {
            modelUnitPrice = new UnitPrice(unitPrice);
        }

        final Quantity modelQuantity = quantity == null ? null : new Quantity(quantity);

        return new Product(modelName, modelUnitPrice, modelQuantity);
    }
}
