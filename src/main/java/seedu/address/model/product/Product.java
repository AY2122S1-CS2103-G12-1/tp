package seedu.address.model.product;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.Category;
import seedu.address.model.commons.ID;
import seedu.address.model.commons.Name;

/**
 * Represents a Product in the app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Product implements Category {
    // Identity fields
    private final ID id;

    // Data fields
    private final Name name;
    private final UnitPrice unitPrice;
    private final Quantity quantity;

    public Product(Name name, UnitPrice unitPrice, Quantity quantity) {
        this(ID.newProductID(), name, unitPrice, quantity);
    }

    private Product(ID id, Name name, UnitPrice unitPrice, Quantity quantity) {
        requireAllNonNull(id, name, unitPrice);

        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public ID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public UnitPrice getUnitPrice() {
        return unitPrice;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    /**
     * Returns a new copy of the {@code Product} with the same ID but the supplied data fields. <br>
     * The only way to copy the ID of a {@code Product} over to another {@code Product}.
     *
     * @param product ID of the original product.
     * @param name New name for the product.
     * @param unitPrice New unit price for the product.
     * @param quantity New quantity for the product.
     */
    public static Product updateProduct(Product product, Name name, UnitPrice unitPrice, Quantity quantity) {
        return new Product(product.getId(), name, unitPrice, quantity);
    }

    /**
     * @see #updateProduct(Product, Name, UnitPrice, Quantity)
     */
    public static Product updateProduct(Product copyTo, Product copyFrom) {
        return new Product(copyTo.getId(), copyFrom.name, copyFrom.unitPrice, copyFrom.quantity);
    }

    /**
     * Returns true if both products have the same id.
     * This defines a weaker notion of equality between two products.
     *
     * @param otherProduct Another Product being compared to.
     */
    public boolean isSameProduct(Product otherProduct) {
        if (otherProduct == this) {
            return true;
        }

        return otherProduct != null && otherProduct.getId().equals(this.getId());
    }

    /**
     * Returns true if both products have the same identity and data fields.
     * This defines a stronger notion of equality between two products.
     *
     * @param other Any Object being compared to. May or may not be a instance of Product.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Product)) {
            return false;
        }

        Product otherProduct = (Product) other;
        return id.equals(otherProduct.id)
                       && name.equals(otherProduct.name)
                       && unitPrice.equals(otherProduct.unitPrice)
                       && quantity.equals(otherProduct.quantity);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(id, name, unitPrice, quantity);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("ID: ").append(id)
                .append("; Name: ").append(name)
                .append("; Unit Price: ").append(unitPrice);

        if (quantity != null) {
            builder.append("; Quantity: ").append(quantity);
        }

        return builder.toString();
    }
}
