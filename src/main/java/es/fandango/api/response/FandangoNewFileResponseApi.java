package es.fandango.api.response;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;
import io.reactivex.Single;

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
                                .status(200)
                                .header("Content-Type", "application/json")
                                .body(ElementId.buildWithId(id))
                );
    }
}
