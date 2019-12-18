package es.fandango.api.response;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;
import io.reactivex.Single;

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
                                .status(200)
                                .header("Content-Type", "application/json")
                                .body(ElementId.buildWithId(id))
                );
    }

}
