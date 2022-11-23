package ru.netology.data;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class APIHelper {
    private static final RequestSpecification peqSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8080)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
public static int buyPayCardAPI(DataHelper.Card data) {
    return given()
            .spec(peqSpec)
            .body(data)
            .when()
            .post("/api/v1/pay")
            .getStatusCode();
}
public static  int buyCreditCardAPI(DataHelper.Card data) {
    return given()
            .spec(peqSpec)
            .body(data)
            .when()
            .post("/api/v1/credit")
            .getStatusCode();
}

}
