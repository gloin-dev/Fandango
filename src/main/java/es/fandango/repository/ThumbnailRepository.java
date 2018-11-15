package es.fandango.repository;

import es.fandango.model.Thumbnail;
import org.reactivestreams.Publisher;

public interface ThumbnailRepository {

  /**
   * Get the thumbnail from MongoDB
   *
   * @param thumbnailId The thumbnailId
   * @return The Thumbnail
   */
  Publisher<Thumbnail> getThumbnail(String thumbnailId);

  /**
   * Save the given thumbnail
   *
   * @param thumbnail The thumbnail
   */
  void saveThumbnail(Thumbnail thumbnail);
}
