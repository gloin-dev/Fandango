package es.fandango.api.response.file;

import static io.micronaut.http.HttpHeaders.*;

import es.fandango.api.response.common.CommonFandangoResponseApi;
import es.fandango.api.response.common.ElementId;
import es.fandango.data.model.File;
import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;

/**
 * This class build the Fandango File response
 */
public class FandangoFileResponseApi extends CommonFandangoResponseApi {

    /**
     * The constructor for Fandango File Response
     *
     * @param file The file
     */
    public FandangoFileResponseApi(Maybe<File> file) {
        this.responseApi = file
                .map(targetFile ->
                        HttpResponse
                                .ok()
                                .header(CONTENT_TYPE, targetFile.getContentType())
                                .header(CONTENT_DISPOSITION, "inline; filename=" + targetFile.getName())
                                .header(CONTENT_LENGTH, String.valueOf(targetFile.getData().length))
                                .body(targetFile.getData()))
                .toSingle(
                        HttpResponse
                                .notFound()
                                .body(ElementId.notFound())
                );

    }
}
