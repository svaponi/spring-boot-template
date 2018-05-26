package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class MessageController {

    @RequestMapping(value = "/**") // whatever is not ${path.message} goes here ...
    public HttpEntity<?> root(final HttpServletRequest request) {
        return ResponseEntity.ok(new AnyResource()
                .withField("method", request.getMethod())
                .withField("uri", request.getRequestURI())
        );
    }

    @RequestMapping(value = "${path.message}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> sayHi(@RequestParam(required = false) final String name) {

        final String message = String.format("Hi %s!", Objects.toString(name, "stranger"));

        return ResponseEntity.ok(new AnyResource().withField("message", message));
    }
}

