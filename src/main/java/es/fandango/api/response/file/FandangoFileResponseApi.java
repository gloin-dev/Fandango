package es.fandango.api.response.file;

import es.fandango.api.response.common.CommonResponseApi;
import es.fandango.api.response.common.CommonResponseApiBuilder;
import es.fandango.data.model.File;
import io.micronaut.core.annotation.Introspected;
import io.reactivex.Maybe;

/**
 * This class build the Fandango File response
 */
@Introspected
public class FandangoFileResponseApi extends CommonResponseApi {

    /**
     * The constructor for Fandango File Response
     *
     * @param file The file
     */
    public FandangoFileResponseApi(Maybe<File> file) {
        this.httpResponse = file
                .map(targetFile -> CommonResponseApiBuilder.Builder()
                        .contentType(targetFile.getContentType())
                        .filename(targetFile.getName())
                        .length(targetFile.getLength())
                        .data(targetFile.getData())
                        .buildResponse()
                )
                .toSingle(CommonResponseApiBuilder.Builder().buildNotFoundResponse());
    }
}
