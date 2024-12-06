package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import model.CreateCourier;
import model.LoginCourier;

import static constants.ApiPath.*;
import static io.restassured.RestAssured.given;

public class CourierSteps {

    @Step("Create courier")
    public ValidatableResponse createCourier(CreateCourier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE_API_PATH)
                .then();
    }

    @Step("Courier login")
    public ValidatableResponse loginCourier(LoginCourier courier) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_LOGIN_API_PATH)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(Integer courierId) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .pathParams("id", courierId)
                .when()
                .delete(COURIER_DELETE_API_PATH)
                .then();
    }
}
