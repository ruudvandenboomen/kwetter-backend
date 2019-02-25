/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import org.junit.Before;
import org.junit.Test;

public class UserResourceTest {

    @Before
    public void setup() {
        RestAssured.baseURI = "https://localhost";
        RestAssured.basePath = "/Kwetter/api/";
        RestAssured.port = 8080;
    }

    @Test
    public void basicTest() {
        given().when().get("/user/Henk").then().statusCode(200);
    }
}
