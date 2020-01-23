package es.fandango.api.response.imageresized;

import static io.micronaut.http.HttpHeaders.*;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
import es.fandango.data.model.ImageResized;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image Resized response
 */
@Introspected
public class FandangoImageResizedResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango Image Resized Response
     *
     * @param imageResized The image
     */
    public FandangoImageResizedResponseApi(Maybe<ImageResized> imageResized) {
        this.responseApi = imageResized
                .map(targetImage ->
                        HttpResponse
                                .ok()
                                .header(CONTENT_TYPE, targetImage.getContentType())
                                .header(CONTENT_DISPOSITION, "inline; filename=" + targetImage.getName())
                                .header(CONTENT_LENGTH, String.valueOf(targetImage.getData().length))
                                .body(targetImage.getData()))
                .toSingle(
                        HttpResponse
                                .notFound()
                                .body(ElementId.notFound())
                );
    }
}
