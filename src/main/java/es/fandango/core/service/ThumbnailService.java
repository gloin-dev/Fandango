package es.fandango.core.service;

import es.fandango.data.model.Thumbnail;
import io.reactivex.rxjava3.core.Maybe;

public interface ThumbnailService {

    /**
     * Get the thumbnail for given id
     *
     * @param thumbnailId The thumbnail id
     * @return The thumbnail
     */
    Maybe<Thumbnail> getThumbnailById(String thumbnailId);
}
