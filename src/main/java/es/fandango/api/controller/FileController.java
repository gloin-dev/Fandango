package es.fandango.api.controller;

import es.fandango.api.response.FandangoFileResponseApi;
import es.fandango.api.response.FandangoNewFileResponseApi;
import es.fandango.core.service.FileService;
import es.fandango.data.model.File;
import es.fandango.data.model.Info;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.io.IOException;
import java.util.List;

@Controller("/api")
public class FileController {

    /**
     * The file service
     */
    private final FileService fileService;

    /**
     * Constructor for File Controller
     *
     * @param fileService The file service
     */
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Get the images ids
     *
     * @return The info ids
     */
    @Get("/files")
    public Single<List<Info>> getFilesInfo() {
        // Request all the images ids
        return fileService.getAllFilesInfo();
    }

    /**
     * Download the file by given id
     *
     * @param fileId The file id
     * @return The file
     */
    @Get(value = "/files/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM
    )
    public Maybe<HttpResponse<Object>> getFile(String fileId) {

        // Request the file
        Maybe<File> file = fileService.getFileById(fileId);
        // Build the response
        FandangoFileResponseApi responseApi = new FandangoFileResponseApi(file);
        // Return the response
        return responseApi.getResponseApi();
    }

    /**
     * Upload a target file
     *
     * @param file The target file to upload
     * @return The file id
     * @throws IOException The file Exception
     */
    @Post(uri = "/files",
            consumes = MediaType.MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON)
    public Maybe<HttpResponse<Object>> uploadFile(
            @Body("file") CompletedFileUpload file
    ) throws IOException {

        // Request the new file
        Single<String> fileId = fileService.processFileUpload(file);
        // Build the response
        FandangoNewFileResponseApi responseApi = new FandangoNewFileResponseApi(fileId);
        // Return the response
        return responseApi.getResponseApi();
    }
}
