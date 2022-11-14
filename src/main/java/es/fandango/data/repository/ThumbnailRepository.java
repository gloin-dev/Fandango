package es.fandango.data.repository;

import es.fandango.data.model.Thumbnail;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;


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
     * @return The Thumbnail
     */
    Single<Thumbnail> saveThumbnail(Thumbnail thumbnail);

    /**
     * Delete the thumbnail in MongoDB and return the result
     *
     * @param thumbnail The thumbnail id
     * @return The result
     */
    Single<String> deleteThumbnail(String thumbnail);
}
