package es.fandango.api.controller;

import es.fandango.api.response.common.ElementId;
import es.fandango.api.response.file.FandangoFileResponseApi;
import es.fandango.api.response.file.FandangoNewFileResponseApi;
import es.fandango.core.service.FileService;
import es.fandango.data.model.File;
import es.fandango.data.model.info.Info;
import io.micronaut.http.MediaType;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.multipart.FileUpload;
import io.micronaut.validation.Validated;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;
import java.util.List;

@Controller("/api")
@Validated
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
     * Get the files ids
     *
     * @return The files info ids
     */
    @Operation(
            method = "GET",
            description = "Get the list of uploaded files",
            tags = {"Files"}
    )
    @ApiResponse(responseCode = "200", description = "The list of saved files")
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
    @Operation(
            method = "GET",
            description = "Get the given file by Id",
            tags = {"Files"}
    )
    @ApiResponse(responseCode = "404", description = "File not found")
    @ApiResponse(responseCode = "200", description = "The requested File")
    @Get(value = "/files/{fileId}",
            produces = MediaType.APPLICATION_OCTET_STREAM
    )
    public Single<MutableHttpResponse<Object>> getFile(String fileId) {

        // Request the file
        final Maybe<File> file = fileService.getFileById(fileId);
        // Build the response
        final FandangoFileResponseApi responseApi = new FandangoFileResponseApi(file);
        // Return the response
        return responseApi.getHttpResponse();
    }

    /**
     * Upload a target file
     *
     * @param file The target file to upload
     * @return The file id
     * @throws IOException The file Exception
     */
    @Operation(
            method = "POST",
            description = "Upload a File",
            tags = {"Files"}
    )
    @ApiResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(anyOf = ElementId.class)
            )
    )
    @RequestBody(
            content = @Content(
                    mediaType = MediaType.MULTIPART_FORM_DATA,
                    schema = @Schema(anyOf = FileUpload.class)
            )
    )
    @Post(uri = "/files",
            consumes = MediaType.MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON)
    public Single<MutableHttpResponse<Object>> uploadFile(
            @Body("file") CompletedFileUpload file
    ) throws IOException {

        // Request the new file
        final Single<String> fileId = fileService.processFileUpload(file);
        // Build the response
        final FandangoNewFileResponseApi responseApi = new FandangoNewFileResponseApi(fileId);
        // Return the response
        return responseApi.getHttpResponse();
    }
}
