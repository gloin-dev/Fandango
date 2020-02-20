package es.fandango.api.controller;

import es.fandango.api.response.thumbnail.FandangoThumbnailResponseApi;
import es.fandango.core.service.ThumbnailService;
import es.fandango.data.model.Thumbnail;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Maybe;
import io.reactivex.Single;
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
    public Single<MutableHttpResponse<Object>> getThumbnail(String thumbnailId) {

        // Request the image
        final Maybe<Thumbnail> thumbnailById = thumbnailService.getThumbnailById(thumbnailId);
        // Build the response
        final FandangoThumbnailResponseApi responseApi = new FandangoThumbnailResponseApi(thumbnailById);
        // Return the response
        return responseApi.getResponseApi();
    }
}
