package es.fandango.repository;

import es.fandango.model.Image;
import es.fandango.model.ImageId;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

public interface ImageRepository {

  /**
   * Get the image from MongoDB
   *
   * @param imageId The imageId
   * @return The Image
   */
  Maybe<Image> getImage(String imageId);

  /**
   * Get all images from MongoDB
   *
   * @return The list with all images
   */
  Single<List<ImageId>> getAllImageIds();

  /**
   * Save the image in MongoDB and return the id
   *
   * @param image The image
   * @return The id
   */
  Single<Image> saveImage(Image image);
}
