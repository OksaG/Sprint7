package order;


import enums.Color;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import model.Order;
import steps.OrderSteps;

import java.net.HttpURLConnection;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }


    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final Color[] color;

    OrderSteps orderSteps = new OrderSteps();

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, Color[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] orderParams() {
        return new Object[][]{
                {"Оксана", "Грабовская", "ул. 26 Бакинских Комиссаров, д 4к2,кв 26", "Юго-Западная", "89111112111", 10, "2024-12-04", "Домофон не работает", new Color[]{Color.BLACK}},
                {"Максим", "Максимов", "Ленинский пр-т., 117, Москва", "Строгино", "89111112112", 4, "2024-12-05", "Оставить у охранника", new Color[]{Color.BLACK, Color.GREY}},
                {"Егор", "Егоров", "Москва, ул. 26 Бакинских Комиссаров, д 8 кв 7", "Черкизовская", "+79111112144", 1, "2024-12-04", "Мне не важен цвет", new Color[]{}},
                {"Мария", "Мариевна", "Москва, ул Комсомольская, д 42", "Лужники", "+79111112145", 7, "2024-12-04", "Не стучать в дверь!", new Color[]{Color.GREY}}
        };
    }

    @Test
    @DisplayName("Create order")
    @Description("Positive scenario for /api/v1/orders")
    public void createOrderTest() {
        orderSteps.createOrder(new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color))
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .assertThat()
                .body("track", notNullValue());
    }
}

