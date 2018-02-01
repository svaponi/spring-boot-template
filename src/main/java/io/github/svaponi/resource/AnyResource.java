package io.github.svaponi.resource;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic resource to be converted in any JSON object.
 */
public class AnyResource {

    @JsonAnySetter
    private final Map<String, Object> fields = new HashMap<>();

    public AnyResource withField(final String name, final Object value) {
        this.fields.put(name, value);
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> fields() {
        return this.fields;
    }
}
