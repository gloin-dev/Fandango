package es.fandango.controller;

import es.fandango.model.Image;
import es.fandango.model.ImageId;
import es.fandango.response.FandangoImageIdResponseApi;
import es.fandango.response.FandangoImageResponseApi;
import es.fandango.response.FandangoNewImageResponseApi;
import es.fandango.service.ImageService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.io.IOException;
import java.util.List;
import javax.inject.Inject;

@Controller("/api")
public class ImageController {

  /** The image service */
  @Inject ImageService imageService;

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

  // See https://docs.micronaut.io/latest/guide/index.html#uploads
  @Post(uri = "/image",
      consumes = MediaType.MULTIPART_FORM_DATA,
      produces = MediaType.APPLICATION_JSON)
  public Flowable<HttpResponse> putImage(
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
