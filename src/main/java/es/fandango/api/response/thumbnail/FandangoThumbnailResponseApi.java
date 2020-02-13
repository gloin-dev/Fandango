package es.fandango.api.response.thumbnail;

import es.fandango.api.response.common.CommonResponseApi;
import es.fandango.api.response.common.CommonResponseApiBuilder;
import es.fandango.data.model.Thumbnail;
import io.micronaut.core.annotation.Introspected;
import io.reactivex.Maybe;

/**
 * This class build the Fandango Image response
 */
@Introspected
public class FandangoThumbnailResponseApi extends CommonResponseApi {

    /**
     * The constructor for Fandango Thumbnail Response
     *
     * @param thumbnail The thumbnail
     */
    public FandangoThumbnailResponseApi(Maybe<Thumbnail> thumbnail) {
        this.responseApi = thumbnail
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
