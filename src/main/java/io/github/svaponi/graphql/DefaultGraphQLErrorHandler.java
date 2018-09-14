package io.github.svaponi.graphql;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DefaultGraphQLErrorHandler implements GraphQLErrorHandler {

    @Override
    public List<GraphQLError> processErrors(final List<GraphQLError> errors) {

        errors.stream()
                .filter(this::isClientError)
                .forEach(error -> log.error("GraphQL client error: {}", error.getMessage()));

        errors.stream()
                .filter(e -> !isClientError(e))
                .forEach(error -> log.error("GraphQL server error: {}", error.getMessage()));

        return errors;
    }

    protected boolean isClientError(final GraphQLError error) {
        return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
    }
}