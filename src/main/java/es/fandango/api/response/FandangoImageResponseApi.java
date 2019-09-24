package es.fandango.api.response;

import es.fandango.data.model.Image;
import io.micronaut.http.HttpResponse;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import org.reactivestreams.Subscriber;

/**
 * This class build the Fandango Image response
 */
public class FandangoImageResponseApi {

  /** The Response Api */
  private Flowable<HttpResponse> responseApi;

  /**
   * The constructor for Fandango Image Response
   *
   * @param image The image
   */
  public FandangoImageResponseApi(Maybe<Image> image) {
    this.responseApi = image
        .flatMapPublisher(targetImage -> new Flowable<HttpResponse>() {
          @Override
          protected void subscribeActual(Subscriber<? super HttpResponse> s) {
            if (targetImage != null) {
              // Build the ok response
              s.onNext(
                  HttpResponse
                      .ok()
                      .status(200)
                      .header("Content-Type", targetImage.getContentType())
                      .body(targetImage.getData())
              );
              s.onComplete();
            } else {
              // Not found image
              s.onNext(HttpResponse.notFound());
              s.onComplete();
            }
          }
        });
  }

  /**
   * Get the Http Response
   *
   * @return The HttpResponse
   */
  public Flowable<HttpResponse> getResponseApi() {
    return responseApi;
  }
}
