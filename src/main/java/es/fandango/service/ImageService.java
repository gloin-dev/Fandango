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
   * @return The image
   */
  Observable<Image> getImageById(String imageId);

  /**
   * Get the thumbnail for given id
   *
   * @return The getAllImages
   */
  Observable<Thumbnail> getThumbnailById(String thumbnailId);

  /**
   * This method process a new image, save it and create the thumbnail
   *
   * @param file The file
   * @return The id of new file
   */
  String processFileUpload(StreamingFileUpload file) throws IOException;
}
