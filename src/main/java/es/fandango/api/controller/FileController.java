package es.fandango.api.controller;

import es.fandango.api.response.FandangoFileResponseApi;
import es.fandango.api.response.FandangoNewFileResponseApi;
import es.fandango.core.service.FileService;
import es.fandango.data.model.File;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;

import javax.inject.Inject;
import java.io.IOException;

@Controller("/api")
public class FileController {

    /**
     * The file service
     */
    @Inject
    private FileService fileService;

    /**
     * Download the file by given id
     *
     * @param fileId The file id
     * @return The file
     */
    @Get(value = "/file/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM
    )
    public HttpResponse getFile(String fileId) {

        // Request the file
        Observable<File> fileById = fileService.getFileById(fileId);
        // Build the response
        FandangoFileResponseApi responseApi = new FandangoFileResponseApi(fileById.blockingLast());
        // Return the response
        return responseApi.getResponse();
    }

    /**
     * Upload a target file
     *
     * @param file The target file to upload
     * @return The file id
     * @throws IOException The file Exception
     */
    @Post(uri = "/file",
            consumes = MediaType.MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON)
    public HttpResponse uploadFile(@Body("file") StreamingFileUpload file) throws IOException {

        // Request the new image
        String newFileId = fileService.processFileUpload(file);
        // Build the response
        FandangoNewFileResponseApi responseApi = new FandangoNewFileResponseApi(newFileId);
        // Return the response
        return responseApi.getResponse();
    }
}
