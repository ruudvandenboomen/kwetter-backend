///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package rest;
//
//import com.github.tomakehurst.wiremock.client.WireMock;
//import static com.jayway.restassured.RestAssured.given;
//import com.jayway.restassured.http.ContentType;
//import domain.User;
//import java.io.File;
//import java.net.URL;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.container.test.api.RunAsClient;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.junit.InSequence;
//import org.jboss.arquillian.test.api.ArquillianResource;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//@RunWith(Arquillian.class)
//public class UserResourceTest {
//
//    private final WireMock wiremock = new WireMock(8888);
//
//    @Deployment(testable = false)
//    public static WebArchive createDeployment() {
//        File[] files = Maven.resolver()
//                .loadPomFromFile("pom.xml")
//                .importRuntimeDependencies()
//                .resolve()
//                .withTransitivity()
//                .asFile();
//        return ShrinkWrap.create(WebArchive.class)
//                .addPackage("config")
//                .addPackage("dao")
//                .addPackage("domain")
//                .addPackage("qualifier")
//                .addPackage("rest")
//                .addPackage("services")
//                .addPackage("util")
//                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
//                .addAsResource( new File("src/main/resources/META-INF/persistence.xml"))
//                .addAsLibraries(files);
//    }
//
//    @ArquillianResource
//    private URL contextPath;
//
//    @Test
//    @RunAsClient
//    @InSequence(1)
//    public void testAddCompany(@ArquillianResource URL url) {
//        given()
//                .when()
//                .get(url.toString() + "api/user/Ruud")
//                .then()
//                .statusCode(204);
//    }
//}
