package es.fandango.core.service;

import es.fandango.data.model.Image;
import es.fandango.data.model.info.Info;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

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
    Single<List<Info>> getAllImagesInfo();

    /**
     * Delete an image by a given id
     *
     * @param imageId The image id
     * @return The deleted image id
     */
    Single<String> deleteImageById(String imageId);

    /**
     * Process and save an image upload
     *
     * @param file The Image
     * @return The image id
     * @throws IOException A process exception
     */
    Single<String> processImageUpload(CompletedFileUpload file) throws IOException;

}
