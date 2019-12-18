package es.fandango.api.response;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.data.model.Thumbnail;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image response
 */
public class FandangoThumbnailResponseApi extends CommonFandangoResponseApi {

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
}
