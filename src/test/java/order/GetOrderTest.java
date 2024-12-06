package order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    OrderSteps orderSteps = new OrderSteps();

    @Test
    @DisplayName("Check status code and body of orders")
    @Description("Positive test for /api/v1/orders")
    public void getOrders() {
        orderSteps.getOrders()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());
    }
}
