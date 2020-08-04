package es.fandango.api.response.image;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

import es.fandango.api.response.common.CommonResponseApi;
import es.fandango.api.response.common.ElementId;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * This class build the Fandango New Image response
 */
@Introspected
public class FandangoNewImageResponseApi extends CommonResponseApi {

    /**
     * The constructor for Fandango New Image Response
     *
     * @param imageId The image Id
     */
    public FandangoNewImageResponseApi(Single<String> imageId) {
        this.httpResponse = Maybe
                .fromSingle(imageId)
                .map(
                        id -> HttpResponse.ok()
                                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .body(ElementId.buildWithId(id))
                )
                .toSingle();
    }
}
