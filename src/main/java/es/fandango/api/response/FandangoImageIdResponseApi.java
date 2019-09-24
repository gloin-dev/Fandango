package es.fandango.api.response;

import es.fandango.data.model.ImageId;
import io.micronaut.http.HttpResponse;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import org.reactivestreams.Subscriber;

/**
 * This class build the Fandango Image response
 */
public class FandangoImageIdResponseApi {

  /** The Response Api */
  private Flowable<HttpResponse> responseApi;

  /**
   * The constructor for Fandango Image Response
   *
   * @param imageList The image list
   */
  public FandangoImageIdResponseApi(Single<List<ImageId>> imageList) {
    this.responseApi = imageList
        .flatMapPublisher(targetImageList -> new Flowable<HttpResponse>() {
          @Override
          protected void subscribeActual(Subscriber<? super HttpResponse> s) {
            if (targetImageList != null) {
              // Build the ok response
              s.onNext(
                  HttpResponse
                      .ok()
                      .status(200)
                      .header("Content-Type", "application/json")
                      .body(targetImageList)
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
