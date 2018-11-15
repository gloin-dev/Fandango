package es.fandango.response;

import es.fandango.model.Image;
import es.fandango.model.Thumbnail;
import io.micronaut.http.HttpResponse;

/**
 * This class build the Fandango Image response
 */
public class FandangoImageResponseApi {

  // The HttpResponse
  private HttpResponse response;

  /**
   * The constructor for Fandango Image Response
   *
   * @param image The image
   */
  public FandangoImageResponseApi(Image image) {
    if (image != null) {
      // Build the ok response
      this.response = HttpResponse
          .ok()
          .status(200)
          .header("Content-Type", image.getContentType())
          .body(image.getData());
    } else {
      // Not found image
      this.response = HttpResponse.notFound();
    }
  }

  /**
   * The constructor for Fandango Image Response
   *
   * @param thumbnail The thumbnail
   */
  public FandangoImageResponseApi(Thumbnail thumbnail) {
    if (thumbnail != null) {
      // Build the ok response
      this.response = HttpResponse
          .ok()
          .status(200)
          .header("Content-Type", thumbnail.getContentType())
          .body(thumbnail.getData());
    } else {
      // Not found image
      this.response = HttpResponse.notFound();
    }
  }

  /**
   * Get the response
   *
   * @return The ready response
   */
  public HttpResponse getResponse() {
    return response;
  }
}
