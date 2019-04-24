package es.fandango.response;

import es.fandango.model.Thumbnail;
import io.micronaut.http.HttpResponse;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import org.reactivestreams.Subscriber;

/**
 * This class build the Fandango Image response
 */
public class FandangoThumbnailResponseApi {

  /** The Response Api */
  private Flowable<HttpResponse> responseApi;

  /**
   * The constructor for Fandango Image Response
   *
   * @param thumbnail The thumbnail
   */
  public FandangoThumbnailResponseApi(Maybe<Thumbnail> thumbnail) {
    this.responseApi = thumbnail
        .flatMapPublisher(targetThumbnail -> new Flowable<HttpResponse>() {
          @Override
          protected void subscribeActual(Subscriber<? super HttpResponse> s) {
            if (targetThumbnail != null) {
              // Build the ok response
              s.onNext(
                  HttpResponse
                      .ok()
                      .status(200)
                      .header("Content-Type", targetThumbnail.getContentType())
                      .body(targetThumbnail.getData())
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
