package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class HelloApplication {

    public static void main(final String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @Controller
    public class AnyUrlController {

        @RequestMapping(value = "/**") // whatever does not match other endpoints ...
        public HttpEntity<?> root(final HttpServletRequest request) {

            return ResponseEntity.ok(new AnyResource()
                    .withField("method", request.getMethod())
                    .withField("uri", request.getRequestURI())
            );
        }
    }
}

