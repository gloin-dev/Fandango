package es.fandango.api.handler;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

/**
 * The Generic error handler
 */
@Slf4j(topic = "HttpErrorHandler")
@Produces
@Singleton
@Requires(classes = {Exception.class, ExceptionHandler.class})
public class HttpErrorHandler implements ExceptionHandler<Exception, HttpResponse<Object>> {

    @Override
    public HttpResponse<Object> handle(HttpRequest request, Exception exception) {
        log.error("Error -> {}", exception);
        return HttpResponse.notFound();
    }
}
