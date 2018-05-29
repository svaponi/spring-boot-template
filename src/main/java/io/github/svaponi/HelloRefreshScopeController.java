package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Profile("refresh-scope")
@RefreshScope
@Controller
public class HelloRefreshScopeController {

    @Value("${hello.template}")
    private String template;

    @Value("${hello.name}")
    private String defaultName;

    // @RefreshScope does not work with URL mapping
    @RequestMapping(value = "${hello.path}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<?> hello(@RequestParam(required = false) final String name) {

        final String message = String.format(template, Objects.toString(name, defaultName));

        return ResponseEntity.ok(new AnyResource().withField("message", message));
    }
}

