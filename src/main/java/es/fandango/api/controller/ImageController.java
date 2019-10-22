package es.fandango.api.controller;

import es.fandango.api.response.FandangoImageIdResponseApi;
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
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Controller("/api")
public class ImageController {

    /**
     * The image service
     */
    @Inject
    private ImageService imageService;

    /**
     * Get the images ids
     *
     * @return The images ids
     */
    @Get("/images")
    public Flowable<HttpResponse> getImages() {
        // Request the image
        Single<List<ImageId>> allImageIds = imageService.getAllImageIds();
        // Build the response
        FandangoImageIdResponseApi responseApi = new FandangoImageIdResponseApi(allImageIds);
        // Return the response
        return responseApi.getResponseApi();
    }

    /**
     * Get the image
     *
     * @param imageId The image id
     * @return The image
     */
    @Get("/image/{imageId}")
    public Flowable<HttpResponse> getImage(String imageId) {
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
            produces = MediaType.APPLICATION_JSON)
    public Flowable<HttpResponse> uploadImage(
            @Body("file") CompletedFileUpload file
    ) throws IOException {

        // Request the new image
        Single<Image> imageSingle = imageService.processImageUpload(file);
        // Build the response
        FandangoNewImageResponseApi responseApi = new FandangoNewImageResponseApi(imageSingle);
        // Return the response
        return responseApi.getResponseApi();
    }
}
