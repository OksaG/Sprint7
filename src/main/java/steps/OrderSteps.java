package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.Order;

import static constants.ApiPath.CREATE_AND_GET_ORDER_API_PATH;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Create order with color scooter = {orders.color}")
    public ValidatableResponse createOrder(Order orders) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(orders)
                .when()
                .post(CREATE_AND_GET_ORDER_API_PATH)
                .then();
    }

    @Step("Get orders")
    public ValidatableResponse getOrders() {
        return given().log().all()
                .when()
                .get(CREATE_AND_GET_ORDER_API_PATH)
                .then();
    }

}
