package ru.praktikum.stellar.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public abstract class ApiClient {
    public static final String BASE_URL = System.getProperty("baseUrl", "https://stellarburgers.education-services.ru");

    protected RequestSpecification request() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL).setBasePath("/api").setContentType(ContentType.JSON)
                .setConfig(RestAssuredConfig.config().httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 15000)
                        .setParam("http.socket.timeout", 30000)))
                .build();
    }
}
