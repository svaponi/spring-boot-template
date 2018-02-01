package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Integration Tests with rest template
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class MessageControllerIT {

    @LocalServerPort
    private int port;

    private String localhost;

    @Autowired
    private TestRestTemplate template;

    @Value("${path.message}")
    private String path;

    @Before
    public void setUp() throws Exception {
        localhost = InetAddress.getLocalHost().getHostName();
    }

    @Test
    public void sayHiShouldReply() throws Exception {
        final URL base = new URL("http://" + localhost + ":" + port + path);
        final ResponseEntity<AnyResource> response = template.getForEntity(base.toString(), AnyResource.class);
        assertThat(response.getBody().fields().get("message"), equalTo("Hi stranger!"));
    }

    @Test
    public void sayHiShouldReplyWithName() throws Exception {
        final URL base = new URL("http://" + localhost + ":" + port + path + "?name=Sam");
        final ResponseEntity<AnyResource> response = template.getForEntity(base.toString() + "", AnyResource.class);
        assertThat(response.getBody().fields().get("message"), equalTo("Hi Sam!"));
    }
}