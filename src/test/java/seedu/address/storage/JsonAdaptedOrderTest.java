package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalProducts.CANNON;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.commons.ID;
import seedu.address.model.order.Order;
import seedu.address.model.product.Quantity;

public class JsonAdaptedOrderTest {
    private static final ID VALID_ID = CANNON.getId();
    private static final Quantity VALID_QUANTITY = CANNON.getQuantity();
    private static final LocalDate VALID_TIME = LocalDate.now();

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        Order validOrder = new Order(VALID_ID, VALID_QUANTITY, VALID_TIME);
        JsonAdaptedOrder order = new JsonAdaptedOrder(validOrder);
        assertEquals(validOrder, order.toModelType());
    }
}