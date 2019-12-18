package es.fandango.api.response;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.data.model.Image;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image response
 */
public class FandangoImageResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango Image Response
     *
     * @param image The image
     */
    public FandangoImageResponseApi(Maybe<Image> image) {
        this.responseApi = image.map(targetImage ->
                targetImage != null
                        ? HttpResponse
                        .ok()
                        .status(200)
                        .header("Content-Type", targetImage.getContentType())
                        .body(targetImage.getData())
                        : HttpResponse
                        .notFound());
    }
}
