package seedu.address.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;
import seedu.address.model.commons.Name;
import seedu.address.model.order.Order;

public class PieChartSalesView extends UiPart<Region> implements SecondPanel {
    private static final String fxml = "PieChartSales.fxml";

    @FXML
    private PieChart pieChart;

    /**
     * Constructor for the {@code PieChartSalesView}
     */
    public PieChartSalesView(ObservableList<Client> clients) {
        super(fxml);

        // data to be changed
        HashMap<Name, Integer> table = new HashMap<>();
        for (Client client : clients) {
            Set<Order> currOrders = client.getOrders();
            for (Order order : currOrders) {
                Name productName = order.getProductName();
                int quantity = Integer.parseInt(order.getQuantity().value);
                table.put(productName, table.getOrDefault(productName, 0) + quantity);
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<Name, Integer> product : table.entrySet()) {
            pieChartData.add(new PieChart.Data(
                    "Product: " + product.getKey().toString() + "\n"
                            + " Sold: " + product.getValue().toString(),
                    product.getValue()));
        }
        pieChart.getData().addAll(pieChartData);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PieChartSalesView)) {
            return false;
        }
        PieChartSalesView view = (PieChartSalesView) other;
        return pieChart.equals(view.pieChart);
    }
}
