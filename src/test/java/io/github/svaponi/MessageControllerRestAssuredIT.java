package io.github.svaponi;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration Tests with Rest Assured
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class MessageControllerRestAssuredIT {

    @LocalServerPort
    private int port;

    @Value("${path.message}")
    private String path;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void sayHiShouldReply() {
        get(path).then().statusCode(200).assertThat()
                .body("message", equalTo("Hi stranger!"));
    }

    @Test
    public void sayHiShouldReplyWithName() {
        get(path + "?name=Sam").then().statusCode(200).assertThat()
                .body("message", equalTo("Hi Sam!"));
    }

}