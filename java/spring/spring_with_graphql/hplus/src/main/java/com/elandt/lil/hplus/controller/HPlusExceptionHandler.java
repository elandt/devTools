package com.elandt.lil.hplus.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;

@Component
public class HPlusExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    @Nullable
    protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {
        ErrorType type = null;

        if (ex instanceof DataIntegrityViolationException) {
            type = ErrorType.BAD_REQUEST;
        } else {
            type = ErrorType.INTERNAL_ERROR;
        }

        return GraphqlErrorBuilder.newError(env)
            .message("Recieved Message: %s", ex.getMessage())
            .errorType(type)
            .build();
    }
}
