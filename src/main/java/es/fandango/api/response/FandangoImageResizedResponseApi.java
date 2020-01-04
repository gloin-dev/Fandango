package es.fandango.api.response;

import static io.micronaut.http.HttpHeaders.*;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.data.model.ImageResized;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image Resized response
 */
public class FandangoImageResizedResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango Image Resized Response
     *
     * @param imageResized The image
     */
    public FandangoImageResizedResponseApi(Maybe<ImageResized> imageResized) {
        this.responseApi = imageResized.map(targetImage ->
                targetImage != null
                        ? HttpResponse
                        .ok()
                        .status(HttpStatus.OK)
                        .header(CONTENT_TYPE, targetImage.getContentType())
                        .header(CONTENT_DISPOSITION, "inline; filename=" + targetImage.getName())
                        .header(CONTENT_LENGTH, String.valueOf(targetImage.getData().length))
                        .body(targetImage.getData())
                        : HttpResponse
                        .notFound());
    }
}
