package es.fandango.api.error;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;
import static io.micronaut.http.MediaType.APPLICATION_JSON;

import es.fandango.api.response.common.ElementId;
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
@Slf4j(topic = "ErrorHandler")
@Produces
@Singleton
@Requires(classes = {Exception.class, ExceptionHandler.class})
public class HttpErrorHandler implements ExceptionHandler<Exception, HttpResponse<Object>> {

    /**
     * Log message
     */
    private static final String API_EXCEPTION = "API exception : {}";

    @Override
    public HttpResponse<Object> handle(HttpRequest request, Exception e) {
        StackTraceElement errorLocation = e.getStackTrace()[0];
        String className = errorLocation.getClassName();
        int lineNumber = errorLocation.getLineNumber();
        String methodName = errorLocation.getMethodName();
        String message = e.getMessage()
                + ", class : " + className
                + ", method : " + methodName
                + ", line : " + lineNumber;

        log.error(API_EXCEPTION, message);

        return HttpResponse
                .notFound()
                .body(ElementId.notFound())
                .header(CONTENT_TYPE, APPLICATION_JSON);
    }
}
