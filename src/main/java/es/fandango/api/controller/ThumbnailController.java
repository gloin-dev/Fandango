package es.fandango.api.controller;

import es.fandango.api.response.FandangoThumbnailResponseApi;
import es.fandango.core.service.ThumbnailService;
import es.fandango.data.model.Thumbnail;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Maybe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller("/api")
public class ThumbnailController {

    /**
     * The image service
     */
    private final ThumbnailService thumbnailService;

    /**
     * Thumbnail Controller constructor
     *
     * @param thumbnailService The image service
     */
    public ThumbnailController(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }

    /**
     * Get the thumbnail
     *
     * @param thumbnailId The thumbnail id
     * @return The thumbnail
     */
    @Operation(
            method = "GET",
            description = "Get the given thumbnail by Id",
            tags = {"Thumbnails"}
    )
    @ApiResponse(responseCode = "404", description = "Thumbnail not found")
    @ApiResponse(responseCode = "200", description = "The requested Thumbnail")
    @Get("/thumbnails/{thumbnailId}")
    public Maybe<HttpResponse<Object>> getThumbnail(String thumbnailId) {

        // Request the image
        Maybe<Thumbnail> thumbnailById = thumbnailService.getThumbnailById(thumbnailId);
        // Build the response
        FandangoThumbnailResponseApi responseApi = new FandangoThumbnailResponseApi(thumbnailById);
        // Return the response
        return responseApi.getResponseApi();
    }
}
