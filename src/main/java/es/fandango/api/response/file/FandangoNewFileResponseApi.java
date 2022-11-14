package es.fandango.api.response.file;

import es.fandango.api.response.common.CommonResponseApi;
import es.fandango.api.response.common.ElementId;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

/**
 * This class build the Fandango New File response
 */
@Introspected
public class FandangoNewFileResponseApi extends CommonResponseApi {

    /**
     * The constructor for Fandango New File Response
     *
     * @param fileId The file Id
     */
    public FandangoNewFileResponseApi(Single<String> fileId) {
        this.httpResponse = Maybe
                .fromSingle(fileId)
                .map(
                        id -> HttpResponse.ok()
                                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .body(ElementId.buildWithId(id))
                )
                .toSingle();
    }
}
