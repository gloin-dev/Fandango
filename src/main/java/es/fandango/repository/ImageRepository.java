package es.fandango.repository;

import es.fandango.model.Image;
import org.reactivestreams.Publisher;

public interface ImageRepository {

  /**
   * Get the image from MongoDB
   *
   * @param imageId The imageId
   * @return The Image
   */
  Publisher<Image> getImage(String imageId);

  /**
   * Save the image in MongoDB and return the id
   *
   * @param image The image
   * @return The id
   */
  String saveImageAndGetId(Image image);
}
