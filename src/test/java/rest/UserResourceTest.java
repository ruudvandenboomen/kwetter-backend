///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rest;
//
//import domain.Kweet;
//import domain.User;
//import io.restassured.RestAssured;
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.CoreMatchers.is;
//import org.junit.Before;
//import org.junit.Test;
//
//public class UserResourceTest {
//
//    @Before
//    public void setUp() {
//        RestAssured.port = 8080;
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.basePath = "/Kwetter/api/";
//    }
//
//    @Test
//    public void addUser() {
//        User user = new User("TestUser", "TestUser@hotmail.com");
//
//        given().
//                contentType("application/json").
//                body(user).
//                when().
//                post("/user").
//                then().
//                statusCode(201);
//    }
//
//    @Test
//    public void getProfile() {
//        given().
//                when().
//                get("/user/TestUser").
//                then().
//                statusCode(200).
//                body(
//                        "username", is("TestUser")
//                );
//    }
//
//    @Test
//    public void createKweet() {
//        Kweet kweet = new Kweet("Hoi");
//        given().
//                contentType("application/json").
//                body(kweet).
//                when().
//                post("/kweet/TestUser").
//                then().
//                statusCode(201);
//    }
//
//    @Test
//    public void getTimeline() {
//        given().
//                when().
//                get("/kweet/TestUser/timeline").
//                then().
//                statusCode(200).
//                body(
//                        "[0].content", is("Hoi"),
//                        "[0].username", is("TestUser")
//                );
//    }
//
//    @Test
//    public void getUserProfile() {
//        given().
//                when().
//                get("/user/TestUser").
//                then().
//                statusCode(200).
//                body(
//                        "username", is("TestUser")
//                );
//    }
//
//}
