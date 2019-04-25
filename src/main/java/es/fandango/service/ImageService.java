package es.fandango.service;

import es.fandango.model.Image;
import es.fandango.model.ImageId;
import es.fandango.model.Thumbnail;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.io.IOException;
import java.util.List;

public interface ImageService {

  /**
   * Get the image for given id
   *
   * @param imageId The image Id
   * @return The image
   */
  Maybe<Image> getImageById(String imageId);

  /**
   * Get all images ids
   *
   * @return The images ids
   */
  Single<List<ImageId>> getAllImageIds();

  /**
   * Get the thumbnail for given id
   *
   * @param thumbnailId The thumbnail id
   * @return The thumbnail
   */
  Maybe<Thumbnail> getThumbnailById(String thumbnailId);

  /**
   * Process and save an image upload
   *
   * @param file The Image
   * @return The image id
   * @throws IOException A process exception
   */
  Single<Image> processImageUpload(CompletedFileUpload file) throws IOException;
}
