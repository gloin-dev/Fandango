package es.fandango.repository;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.model.Thumbnail;
import io.reactivex.Maybe;
import javax.inject.Singleton;

@Singleton
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
