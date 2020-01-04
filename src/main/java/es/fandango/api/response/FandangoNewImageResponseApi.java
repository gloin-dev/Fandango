package es.fandango.api.response;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * This class build the Fandango New Image response
 */
public class FandangoNewImageResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango New Image Response
     *
     * @param imageId The image Id
     */
    public FandangoNewImageResponseApi(Single<String> imageId) {
        this.responseApi = Maybe
                .fromSingle(imageId)
                .map(id ->
                        HttpResponse
                                .ok()
                                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .body(ElementId.buildWithId(id))
                );
    }

}
