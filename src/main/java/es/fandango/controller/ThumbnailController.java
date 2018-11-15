package es.fandango.controller;

import es.fandango.model.Thumbnail;
import es.fandango.response.FandangoImageResponseApi;
import es.fandango.service.ImageService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Observable;
import javax.inject.Inject;
import lombok.extern.java.Log;

@Log
@Controller("/api")
public class ThumbnailController {

  /** The image service */
  @Inject ImageService imageService;

  /**
   * Get the thumbnail
   *
   * @param imageId The image id
   * @return The image
   */
  @Get("/thumbnail/{thumbnailId}")
  public HttpResponse getThumbnail(String thumbnailId) {

    // Request the image
    Observable<Thumbnail> thumbnailById = imageService.getThumbnailById(thumbnailId);
    // Build the response
    FandangoImageResponseApi responseApi =
        new FandangoImageResponseApi(thumbnailById.blockingLast());
    // Return the response
    return responseApi.getResponse();
  }
}
