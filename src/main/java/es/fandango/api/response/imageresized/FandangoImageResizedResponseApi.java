package es.fandango.api.response.imageresized;

import es.fandango.api.response.common.CommonResponseApi;
import es.fandango.api.response.common.CommonResponseApiBuilder;
import es.fandango.data.model.ImageResized;
import io.micronaut.core.annotation.Introspected;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image Resized response
 */
@Introspected
public class FandangoImageResizedResponseApi extends CommonResponseApi {

    /**
     * The constructor for Fandango Image Resized Response
     *
     * @param imageResized The image
     */
    public FandangoImageResizedResponseApi(Maybe<ImageResized> imageResized) {
        this.responseApi = imageResized
                .map(targetFile ->
                        CommonResponseApiBuilder
                                .Builder()
                                .contentType(targetFile.getContentType())
                                .filename(targetFile.getName())
                                .length(targetFile.getLength())
                                .data(targetFile.getData())
                                .buildResponse()
                )
                .toSingle(
                        CommonResponseApiBuilder
                                .Builder()
                                .buildNotFoundResponse()
                );
    }
}
