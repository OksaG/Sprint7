package courier;

import constants.MessageInResponse;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.CreateCourier;
import model.LoginCourier;
import steps.CourierSteps;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;


public class CourierCreateTest {

    private final CourierSteps courierSteps = new CourierSteps();
    private static CreateCourier courier;


    @Before
    public void courierParams() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        courier = new CreateCourier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(6));
        courier.setPassword(RandomStringUtils.randomAlphabetic(8, 10));
        courier.setFirstName(RandomStringUtils.randomAlphabetic(9).toLowerCase());
    }


    @Test
    @DisplayName("Create courier with correct params")
    @Description("Positive test for /api/v1/courier")
    public void createNewCourier() {
        courierSteps
                .createCourier(courier)
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", is(true));
    }


    @Test
    @DisplayName("Create courier with already used params")
    @Description("Negative test for /api/v1/courier")
    public void createExistedCourier() {
        courierSteps
                .createCourier(courier);
        courierSteps
                .createCourier(courier)
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo(MessageInResponse.THIS_LOGIN_ALREADY_USED));
    }


    @Test
    @DisplayName("Create courier without login")
    @Description("Negative test for /api/v1/courier")
    public void createNewCourierWithoutLogin() {
        courier.setLogin(null);
        courierSteps
                .createCourier(courier)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo(MessageInResponse.MISSING_PARAMS_ON_CREATION));
    }

    @Test
    @DisplayName("Create courier without password")
    @Description("Negative test for /api/v1/courier")
    public void createCourierWithoutPassword() {
        courier.setPassword(null);

        courierSteps
                .createCourier(courier)
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo(MessageInResponse.MISSING_PARAMS_ON_CREATION));
    }


    @Test
    @DisplayName("Create courier without firstname")
    @Description("Positive test for /api/v1/courier")
    public void createCourierWithoutFirstName() {
        courier.setFirstName(null);

        courierSteps
                .createCourier(courier)
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .body("ok", is(true));
    }

    @After
    public void courierDelete() {
        if (courier.getLogin() != null && courier.getPassword() != null) {
            LoginCourier loginCourier = new LoginCourier(courier.getLogin(), courier.getPassword());
            Integer courierId = courierSteps.loginCourier(loginCourier)
                    .extract()
                    .body()
                    .path("id");
            courierSteps.deleteCourier(courierId);
        }
    }
}

