package es.fandango.core.service;

import es.fandango.data.model.ImageResized;
import io.reactivex.Maybe;

public interface ImageResizedService {

    /**
     * Search in cache or Build the desired Image resized
     *
     * @param imageId The image Id
     * @param width   The target width
     * @param height  The target height
     * @return The image
     */
    Maybe<ImageResized> getResizedImageById(
            String imageId,
            Integer width,
            Integer height
    );
}
