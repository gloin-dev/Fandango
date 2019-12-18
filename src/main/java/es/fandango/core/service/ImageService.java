package es.fandango.core.service;

import es.fandango.data.model.Image;
import es.fandango.data.model.Info;
import es.fandango.data.model.Thumbnail;
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
    Single<List<Info>> getAllImagesInfo();

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
    Single<String> processImageUpload(CompletedFileUpload file) throws IOException;
}
