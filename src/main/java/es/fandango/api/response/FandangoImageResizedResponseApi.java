package es.fandango.api.response;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image response
 */
public class FandangoImageResizedResponseApi extends CommonFandangoResponseApi {

    public FandangoImageResizedResponseApi(Maybe<ImageResized> imageResized) {
        this.responseApi = imageResized.map(targetImage ->
                targetImage != null
                        ? HttpResponse
                        .ok()
                        .status(200)
                        .header("Content-Type", targetImage.getContentType())
                        .header("Content-Disposition", "inline; filename=" + targetImage.getName())
                        .header("Content-Length", String.valueOf(targetImage.getData().length))
                        .body(targetImage.getData())
                        : HttpResponse
                        .notFound());
    }
}
