package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RefreshScope
@Controller
public class MessageController {

    @Value("${greeting.template:Hi %s!}")
    private String template;

    @Value("${greeting.name:stranger}")
    private String defaultName;

    // @RefreshScope does not work with URL mapping
    @RequestMapping(value = "${path.message}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> sayHi(@RequestParam(required = false) final String name) {

        final String message = String.format(template, Objects.toString(name, defaultName));

        return ResponseEntity.ok(new AnyResource().withField("message", message));
    }


    // whatever is not ${path.message} goes here ...
    @RequestMapping(value = "/**", produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> root(final HttpServletRequest request) {

        return ResponseEntity.ok(new AnyResource()
                .withField("method", request.getMethod())
                .withField("uri", request.getRequestURI())
        );
    }
}

