package seedu.address.model.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class IdTest {
    @Test
    public void constructor_success() {
        assertEquals(0, ID.newClientID().getId());
        assertEquals("0", ID.newProductID().toString());
    }

    @Test
    public void equals() {
        ID idClient = ID.newClientID();
        ID idProduct = ID.newProductID();

        // same object -> returns true
        assertEquals(idClient, idClient);
        assertEquals(idProduct, idProduct);

        // same id -> returns true
        assertEquals(idClient.getId(), idProduct.getId());

        // different id -> returns false
        assertNotEquals(idClient, ID.newProductID());
    }
}
