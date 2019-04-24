package es.fandango.controller;

import es.fandango.model.Thumbnail;
import es.fandango.response.FandangoThumbnailResponseApi;
import es.fandango.service.ImageService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller("/api")
public class ThumbnailController {

  /** The image service */
  @Inject ImageService imageService;

  /**
   * Get the thumbnail
   *
   * @param thumbnailId The image id
   * @return The image
   */
  @Get("/thumbnail/{thumbnailId}")
  public Flowable<HttpResponse> getThumbnail(String thumbnailId) {

    // Request the image
    Maybe<Thumbnail> thumbnailById = imageService.getThumbnailById(thumbnailId);
    // Build the response
    FandangoThumbnailResponseApi responseApi = new FandangoThumbnailResponseApi(thumbnailById);
    // Return the response
    return responseApi.getResponseApi();
  }
}
