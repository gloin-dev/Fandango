package es.fandango.api.response.thumbnail;

import static io.micronaut.http.HttpHeaders.*;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
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
        this.responseApi = thumbnail
                .map(targetThumbnail ->
                        HttpResponse
                                .ok()
                                .header(CONTENT_TYPE, targetThumbnail.getContentType())
                                .header(CONTENT_DISPOSITION, "inline; filename=" + targetThumbnail.getName())
                                .header(CONTENT_LENGTH, String.valueOf(targetThumbnail.getData().length))
                                .body(targetThumbnail.getData()))
                .toSingle(
                        HttpResponse
                                .notFound()
                                .body(ElementId.notFound())
                );
    }
}
