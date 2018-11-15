package es.fandango.controller;

import es.fandango.model.Image;
import es.fandango.response.FandangoImageResponseApi;
import es.fandango.response.FandangoNewImageResponseApi;
import es.fandango.service.ImageService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.io.IOException;
import javax.inject.Inject;
import lombok.extern.java.Log;

@Log
@Controller("/api")
public class ImageController {

  /** The image service */
  @Inject ImageService imageService;

  /**
   * Get the image
   *
   * @param imageId The image id
   * @return The image
   */
  @Get("/image/{imageId}")
  public HttpResponse getImage(String imageId) {

    // Request the image
    Observable<Image> imageById = imageService.getImageById(imageId);
    // Build the response
    FandangoImageResponseApi responseApi = new FandangoImageResponseApi(imageById.blockingLast());
    // Return the response
    return responseApi.getResponse();
  }

  // See https://docs.micronaut.io/latest/guide/index.html#uploads
  @Post(uri = "/image",
      consumes = MediaType.MULTIPART_FORM_DATA,
      produces = MediaType.APPLICATION_JSON)
  public HttpResponse putImage(@Body("file") StreamingFileUpload file) throws IOException {

    // Request the new image
    String newImageId = imageService.processFileUpload(file);
    // Build the response
    FandangoNewImageResponseApi responseApi = new FandangoNewImageResponseApi(newImageId);
    // Return the response
    return responseApi.getResponse();
  }
}
