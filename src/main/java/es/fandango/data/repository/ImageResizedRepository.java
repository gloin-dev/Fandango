package es.fandango.data.repository;

import es.fandango.data.model.ImageResized;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface ImageResizedRepository {

    /**
     * Get the resized image
     *
     * @param imageId The image Id
     * @param width   The width
     * @param height  The height
     * @return The result
     */
    Maybe<ImageResized> getImageResized(
            String imageId,
            Integer width,
            Integer height
    );

    /**
     * Save the resized image
     *
     * @param image The image
     * @return The result
     */
    Single<ImageResized> saveImageResized(ImageResized image);
}
