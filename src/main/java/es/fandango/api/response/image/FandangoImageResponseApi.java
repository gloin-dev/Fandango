package es.fandango.api.response.image;

import es.fandango.api.response.common.CommonResponseApi;
import es.fandango.api.response.common.CommonResponseApiBuilder;
import es.fandango.data.model.Image;
import io.micronaut.core.annotation.Introspected;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image response
 */
@Introspected
public class FandangoImageResponseApi extends CommonResponseApi {

    /**
     * The constructor for Fandango Image Response
     *
     * @param image The image
     */
    public FandangoImageResponseApi(Maybe<Image> image) {
        this.responseApi = image
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
