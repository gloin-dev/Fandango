package es.fandango.api.response.file;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * This class build the Fandango New File response
 */
public class FandangoNewFileResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango New File Response
     *
     * @param fileId The file Id
     */
    public FandangoNewFileResponseApi(Single<String> fileId) {
        this.responseApi = Maybe
                .fromSingle(fileId)
                .map(id ->
                        HttpResponse
                                .ok()
                                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON)
                                .body(ElementId.buildWithId(id))
                ).toSingle();
    }
}
