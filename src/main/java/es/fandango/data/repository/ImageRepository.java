package es.fandango.data.repository;

import es.fandango.data.model.Image;
import es.fandango.data.model.Info;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

public interface ImageRepository {

    /**
     * Get all images info object from MongoDB
     *
     * @return The list with all images
     */
    Single<List<Info>> getAllImageIds();

    /**
     * Get the image from MongoDB
     *
     * @param imageId The imageId
     * @return The Image
     */
    Maybe<Image> getImage(String imageId);

    /**
     * Save the image in MongoDB and return the id
     *
     * @param image The image
     * @return The image
     */
    Single<Image> saveImage(Image image);
}
