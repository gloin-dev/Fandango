package es.fandango.response;

import es.fandango.model.File;
import io.micronaut.http.HttpResponse;

public class FandangoFileResponseApi {

  // The HttpResponse
  private HttpResponse response;

  /**
   * The constructor for Fandango Image Response
   *
   * @param file The file
   */
  public FandangoFileResponseApi(File file) {
    if (file != null) {
      // Build the ok response
      this.response = HttpResponse
          .ok()
          .status(200)
          .header("Content-Type", file.getContentType())
          .body(file.getData());
    } else {
      // Not found file
      this.response = HttpResponse.notFound();
    }
  }

  public HttpResponse getResponse() {
    return response;
  }
}
