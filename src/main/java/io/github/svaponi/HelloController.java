package io.github.svaponi;

import io.github.svaponi.resource.AnyResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Slf4j
@Controller
public class HelloController {

    final static String REQUEST_METHOD = "request-method";
    final static String REQUEST_URI = "request-uri";
    final static String REQUEST_QUERY = "request-query";

    @RequestMapping(value = "/**") // whatever is not defined by other controllers goes here ...
    public HttpEntity<?> root(final HttpServletRequest request) {

        Assert.notNull(request, "null http request");

        final AnyResource resource = new AnyResource()
                .withField(REQUEST_METHOD, request.getMethod())
                .withField(REQUEST_URI, request.getRequestURI())
                .withFieldIfValue(REQUEST_QUERY, request.getQueryString(), Objects::nonNull);

        return ResponseEntity.ok(resource);
    }
}


