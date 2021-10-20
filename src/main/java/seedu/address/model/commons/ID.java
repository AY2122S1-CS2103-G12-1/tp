package seedu.address.model.commons;

/**
 * Represents a Client's or Product's ID in the address book.
 * Guarantees: immutable; is unique.
 */
public class ID {
    private static int clientIDCounter = 0;
    private static int productIDCounter = 0;

    private final int id;

    private ID(int id) {
        this.id = id;
    }

    /**
     * Returns a unique {@code ID} for {@code Client}.
     */
    public static ID newClientID() {
        return new ID(clientIDCounter++);
    }

    /**
     * Returns a unique {@code ID} for {@code Product}.
     */
    public static ID newProductID() {
        return new ID(productIDCounter++);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ID // instanceof handles nulls
                && this.getId() == ((ID) other).getId()); // state check
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
