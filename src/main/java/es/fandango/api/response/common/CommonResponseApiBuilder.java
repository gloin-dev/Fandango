package es.fandango.api.response.common;

import static io.micronaut.http.HttpHeaders.*;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Common response builder for HTTP responses
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponseApiBuilder {

    private String filename;

    private String contentType;

    private Long length;

    private byte[] data;

    /**
     * The builder class entry point
     *
     * @return The builder
     */
    public static CommonResponseApiBuilder Builder() {
        return new CommonResponseApiBuilder();
    }

    /**
     * The data if the response is ok
     *
     * @param filename The target file name
     * @return The builder
     */
    public CommonResponseApiBuilder filename(String filename) {
        this.filename = filename;
        return this;
    }

    /**
     * The data if the response is ok
     *
     * @param contentType The target file contentType
     * @return The builder
     */
    public CommonResponseApiBuilder contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * The data if the response is ok
     *
     * @param length The target file length
     * @return The builder
     */
    public CommonResponseApiBuilder length(Long length) {
        this.length = length;
        return this;
    }

    /**
     * The data if the response is ok
     *
     * @param data The target file data
     * @return The builder
     */
    public CommonResponseApiBuilder data(byte[] data) {
        this.data = data;
        return this;
    }

    /**
     * Build the 200 http entity
     *
     * @return The Http Entity
     */
    public MutableHttpResponse<Object> buildResponse() {
        return HttpResponse.ok()
                .header(CONTENT_TYPE, contentType)
                .header(CONTENT_DISPOSITION, "inline; filename=" + filename)
                .header(CONTENT_LENGTH, String.valueOf(length))
                .body(data);
    }

    /**
     * Build the 404 http entity
     *
     * @return The Http Entity
     */
    public MutableHttpResponse<Object> buildNotFoundResponse() {
        return HttpResponse.notFound().body(ElementId.notFound());
    }
}
