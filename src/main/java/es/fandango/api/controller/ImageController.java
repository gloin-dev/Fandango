package es.fandango.api.controller;

import es.fandango.api.response.FandangoImageResponseApi;
import es.fandango.api.response.FandangoNewImageResponseApi;
import es.fandango.core.service.ImageService;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageId;
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
public class ImageController {

    /** The image service */
    private final ImageService imageService;

    /**
     * Image Controller constructor
     * @param imageService The image service
     */
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    /**
     * Get the images ids
     *
     * @return The images ids
     */
    @Get("/images")
    public Single<List<ImageId>> getImages() {
        // Request all the images ids
        return imageService.getAllImageIds();
    }

    /**
     * Get the image
     *
     * @param imageId The image id
     * @return The image
     */
    @Get("/image/{imageId}")
    public Maybe<HttpResponse<Object>> getImage(String imageId) {
        // Request the image
        Maybe<Image> imageById = imageService.getImageById(imageId);
        // Build the response
        FandangoImageResponseApi responseApi = new FandangoImageResponseApi(imageById);
        // Return the response
        return responseApi.getResponseApi();
    }

    /**
     * Upload a target image
     *
     * @param file The target image to upload
     * @return The image id
     * @throws IOException The image Exception
     */
    @Post(uri = "/image",
            consumes = MediaType.MULTIPART_FORM_DATA,
            produces = MediaType.APPLICATION_JSON
    )
    public Maybe<HttpResponse<Object>> uploadImage(
            @Body("file") CompletedFileUpload file
    ) throws IOException {

        // Request the new image
        Single<String> imageId = imageService.processImageUpload(file);
        // Build the response
        FandangoNewImageResponseApi responseApi = new FandangoNewImageResponseApi(Maybe.fromSingle(imageId));
        // Return the response
        return responseApi.getResponseApi();
    }
}
