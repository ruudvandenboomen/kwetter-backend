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
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.junit.InSequence;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(Arquillian.class)
//public class ResourceTest {
//
//    private final User testUser = new User("TestUser", "Test123", "TestUser@hotmail.com");
//    private final User testUser2 = new User("TestUser2", "Test123", "TestUser2@hotmail.com");
//    private final Kweet kweet = new Kweet("Hoi #Test");
//
//    @Before
//    public void setUp() {
//        RestAssured.port = 8080;
//        RestAssured.baseURI = "http://localhost";
//        RestAssured.basePath = "/Kwetter/api/";
//    }
//
//    @Test
//    @InSequence(1)
//    public void addUsers() {
//        given().
//                contentType("application/json").
//                body(testUser).
//                when().
//                post("/user").
//                then().
//                statusCode(201);
//
//        given().
//                contentType("application/json").
//                body(testUser2).
//                when().
//                post("/user").
//                then().
//                statusCode(201);
//    }
//
//    @Test
//    @InSequence(2)
//    public void getProfile() {
//        given().
//                when().
//                get("/user/" + testUser.getUsername()).
//                then().
//                statusCode(200).
//                body(
//                        "username", is(testUser.getUsername())
//                );
//    }
//
//    @Test
//    @InSequence(3)
//    public void createKweet() {
//        given().
//                contentType("application/json").
//                body(kweet).
//                when().
//                post("/kweet/" + testUser.getUsername()).
//                then().
//                statusCode(201);
//    }
//
//    @Test
//    @InSequence(4)
//    public void getTimeline() {
//        given().
//                when().
//                get("/kweet/" + testUser.getUsername() + "/timeline").
//                then().
//                statusCode(200).
//                body(
//                        "[0].content", is(kweet.getContent()),
//                        "[0].username", is(testUser.getUsername())
//                );
//    }
//
//    @Test
//    @InSequence(5)
//    public void startFollowing() {
//        given().
//                when().
//                put("/user/" + testUser.getUsername() + "/following/add/" + testUser2.getUsername()).
//                then().
//                statusCode(200);
//    }
//
//    @Test
//    @InSequence(6)
//    public void getFollowers() {
//        given().
//                when().
//                get("/user/" + testUser2.getUsername() + "/followers").
//                then().
//                statusCode(200).
//                body(
//                        "[0]", is(testUser.getUsername())
//                );
//    }
//
//    @Test
//    @InSequence(7)
//    public void getFollowing() {
//        given().
//                when().
//                get("/user/" + testUser.getUsername() + "/following").
//                then().
//                statusCode(200).
//                body(
//                        "[0]", is(testUser2.getUsername())
//                );
//    }
//
//}
