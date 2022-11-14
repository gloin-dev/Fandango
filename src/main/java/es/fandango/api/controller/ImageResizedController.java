package es.fandango.api.controller;

import es.fandango.api.response.imageresized.FandangoImageResizedResponseApi;
import es.fandango.core.service.ImageResizedService;
import es.fandango.data.model.ImageResized;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Controller("/api")
public class ImageResizedController {

    /**
     * The image service
     */
    private final ImageResizedService imageResizedService;

    /**
     * Image Controller constructor
     *
     * @param imageResizedService The image service
     */
    public ImageResizedController(ImageResizedService imageResizedService) {
        this.imageResizedService = imageResizedService;
    }


    /**
     * Get the image
     *
     * @param imageId The image id
     * @return The image
     */
    @Operation(
            method = "GET",
            description = "Get the given image resized by Id and new resolution",
            tags = {"Images"}
    )
    @ApiResponse(responseCode = "404", description = "Image not found")
    @ApiResponse(responseCode = "200", description = "The requested Image Resized")
    @Get("/images/{imageId}/{width}")
    public Single<MutableHttpResponse<Object>> getResizedImage(
            String imageId,
            Integer width
    ) {
        // Request the resized image
        final Maybe<ImageResized> imageResized = imageResizedService.getResizedImageById(
                imageId,
                width,
                width
        );
        // Build the response
        final FandangoImageResizedResponseApi responseApi = new FandangoImageResizedResponseApi(imageResized);
        // Return the response
        return responseApi.getHttpResponse();
    }
}
