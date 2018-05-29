package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@ProfileNot("refresh-scope")
@Controller
public class HelloController {

    @RequestMapping(value = "${hello.path}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> hello(@RequestParam(required = false) final String name) {

        final String message = String.format("Hi %s!", Objects.toString(name, "stranger"));

        return ResponseEntity.ok(new AnyResource().withField("message", message));
    }
}

