package es.fandango.api.response;

import es.fandango.api.response.common.ElementId;
import io.micronaut.core.util.StringUtils;
import io.micronaut.http.HttpResponse;

public class FandangoNewFileResponseApi {

    // The HttpResponse
    private HttpResponse response;

    public FandangoNewFileResponseApi(String id) {
        if (!StringUtils.isEmpty(id)) {
            // Build the ok response
            this.response = HttpResponse
                    .ok()
                    .status(200)
                    .header("Content-Type", "application/json")
                    .body(ElementId.buildWithId(id));
        } else {
            // Bad request
            this.response = HttpResponse.badRequest();
        }
    }

    public HttpResponse getResponse() {
        return response;
    }
}
