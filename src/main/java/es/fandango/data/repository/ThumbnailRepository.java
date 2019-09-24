package es.fandango.data.repository;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.data.model.Thumbnail;
import io.reactivex.Maybe;

public interface ThumbnailRepository {

  /**
   * Get the thumbnail from MongoDB
   *
   * @param thumbnailId The thumbnailId
   * @return The Thumbnail
   */
  Maybe<Thumbnail> getThumbnail(String thumbnailId);

  /**
   * Save the given thumbnail
   *
   * @param thumbnail The thumbnail
   * @return The operation result
   */
  Success saveThumbnail(Thumbnail thumbnail);
}
