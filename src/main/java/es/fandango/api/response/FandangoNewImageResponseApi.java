package es.fandango.api.response;

import es.fandango.api.response.common.ElementId;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

public class FandangoNewImageResponseApi {

    /**
     * The Response Api
     */
    private Maybe<HttpResponse<Object>> responseApi;

    public FandangoNewImageResponseApi(Maybe<String> imageId) {

        this.responseApi = imageId.map(id ->

                HttpResponse
                        .ok()
                        .status(200)
                        .header("Content-Type", "application/json")
                        .body(ElementId.buildWithId(id))
        );
    }

    /**
     * Get the Http Response
     *
     * @return The HttpResponse
     */
    public Maybe<HttpResponse<Object>> getResponseApi() {
        return responseApi;
    }
}
