package es.fandango.service;

import es.fandango.model.Image;
import es.fandango.model.Thumbnail;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.io.IOException;

public interface ImageService {

  /**
   * Get the image for given id
   *
   * @param imageId The image Id
   * @return The image
   */
  Observable<Image> getImageById(String imageId);

  /**
   * Get the thumbnail for given id
   *
   * @param thumbnailId The thumbnail id
   * @return The thumbnail
   */
  Observable<Thumbnail> getThumbnailById(String thumbnailId);

  /**
   * Process and save an image upload
   *
   * @param file The Image
   * @return The image id
   * @throws IOException A process exception
   */
  String processImageUpload(StreamingFileUpload file) throws IOException;
}
