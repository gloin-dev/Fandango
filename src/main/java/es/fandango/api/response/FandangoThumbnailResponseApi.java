package es.fandango.api.response;

import es.fandango.data.model.Thumbnail;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image response
 */
public class FandangoThumbnailResponseApi {

    /**
     * The Response Api
     */
    Maybe<HttpResponse<Object>> responseApi;

    /**
     * The constructor for Fandango Thumbnail Response
     *
     * @param thumbnail The thumbnail
     */
    public FandangoThumbnailResponseApi(Maybe<Thumbnail> thumbnail) {
        this.responseApi = thumbnail.map(targetThumbnail ->
                targetThumbnail != null
                        ? HttpResponse
                        .ok()
                        .status(200)
                        .header("Content-Type", targetThumbnail.getContentType())
                        .body(targetThumbnail.getData())
                        : HttpResponse
                        .notFound());
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
