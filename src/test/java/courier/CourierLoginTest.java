package courier;

import constants.MessageInResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import model.CreateCourier;
import model.LoginCourier;
import steps.CourierSteps;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {


    protected static final CourierSteps courierSteps = new CourierSteps();
    static CreateCourier courier;
    protected static Integer courierId;

    @BeforeClass
    public static void courierParams() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        courier = new CreateCourier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(6));
        courier.setPassword(RandomStringUtils.randomAlphabetic(8, 10));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(9).toLowerCase());
        courierSteps.createCourier(courier);
    }

    @Test
    @DisplayName("Success login")
    @Description("Positive test for /api/v1/courier/login")
    public void loginCourier() {
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
        courierId = courierSteps
                .loginCourier(loginCourier)
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("id", notNullValue())
                .extract()
                .body()
                .path("id");
    }

    @Test
    @DisplayName("Login error")
    @Description("Negative test for /api/v1/courier/login")
    public void loginCourierError() {
        LoginCourier loginCourier = new LoginCourier(RandomStringUtils.randomAlphabetic(12), RandomStringUtils.randomAlphabetic(5, 8));
        courierSteps
                .loginCourier(loginCourier)
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo(MessageInResponse.ACCOUNT_NOT_FOUND));


    }

    @Test
    @DisplayName("Login without password")
    @Description("Negative test for /api/v1/courier/login")
    public void loginWithoutPasswordError() {
        LoginCourier loginCourier = new LoginCourier(courier.getLogin(), null);
        courierSteps
                .loginCourier(loginCourier)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo(MessageInResponse.MISSING_PARAMS_LOGIN_PASSWORD));

    }

    @Test
    @DisplayName("Authentication without login")
    @Description("Negative test for /api/v1/courier/login")
    public void loginWithoutLoginError() {
        LoginCourier loginCourier = new LoginCourier(null, courier.getPassword());
        courierSteps
                .loginCourier(loginCourier)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo(MessageInResponse.MISSING_PARAMS_LOGIN_PASSWORD));

    }

    @AfterClass
    public static void courierDelete() {
        courierSteps.deleteCourier(courierId);
    }
}


