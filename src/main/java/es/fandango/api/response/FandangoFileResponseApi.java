package es.fandango.api.response;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.data.model.File;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

public class FandangoFileResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango File Response
     *
     * @param file The file
     */
    public FandangoFileResponseApi(Maybe<File> file) {

        this.responseApi = file.map(targetFile ->
                targetFile != null
                        ? HttpResponse
                        .ok()
                        .status(200)
                        .header("Content-Type", targetFile.getContentType())
                        .body(targetFile.getData())
                        : HttpResponse
                        .notFound());

    }
}
