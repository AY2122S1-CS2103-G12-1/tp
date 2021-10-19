package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;

/**
 * Second panel containing the details of client/product.
 */
public class ViewMoreClient extends UiPart<Region> implements SecondPanel {
    private static String fxml = "ViewMoreClient.fxml";

    @FXML
    private Label id;

    @FXML
    private Label name;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label email;

    @FXML
    private Label address;

    /**
     * Constructor for the ViewMore
     */
    public ViewMoreClient() {
        super(fxml);
    }

    public void setClientDetails(Client client) {
        id.setText("ID: " + client.getId().toString());
        name.setText("Name: " + client.getName().toString());
        phoneNumber.setText("Phone Number: " + client.getPhoneNumber().toString());
        email.setText("Email: " + client.getEmail().toString());
        address.setText("Address: " + client.getAddress().toString());
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ViewMoreClient)) {
            return false;
        }

        ViewMoreClient view = (ViewMoreClient) other;
        return id.equals(view.id);
    }
}