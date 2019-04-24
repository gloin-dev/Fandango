package es.fandango.response;

import es.fandango.model.Image;
import io.micronaut.http.HttpResponse;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.HashMap;
import org.reactivestreams.Subscriber;

public class FandangoNewImageResponseApi {

  /** The Response Api */
  private Flowable<HttpResponse> responseApi;

  public FandangoNewImageResponseApi(Single<Image> image) {

    this.responseApi = image
        .flatMapPublisher(targetImage -> new Flowable<HttpResponse>() {
          @Override
          protected void subscribeActual(Subscriber<? super HttpResponse> s) {
            if (targetImage != null) {
              // Build the ok response
              HashMap<String, String> objectId = new HashMap<>();

              // Build the body response
              objectId.put(
                  "id",
                  targetImage.getId().toString()
              );

              s.onNext(
                  HttpResponse
                      .ok()
                      .status(200)
                      .header("Content-Type", "application/json")
                      .body(objectId));
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
