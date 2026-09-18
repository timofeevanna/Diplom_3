package ru.praktikum.stellar.client;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.stellar.model.User;
import ru.praktikum.stellar.model.Credentials;
import static io.restassured.RestAssured.given;

public class UserClient extends ApiClient {
    @Step("Создать пользователя")
    public Response create(User user) {
        return given().spec(request()).body(user).post("/auth/register");
    }

    @Step("Авторизовать пользователя")
    public Response login(Credentials credentials) {
        return given().spec(request()).body(credentials).post("/auth/login");
    }

    public Response delete(String accessToken) {
        return given().spec(request()).header("Authorization", accessToken).delete("/auth/user");
    }
}
