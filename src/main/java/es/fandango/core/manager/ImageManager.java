package es.fandango.core.manager;

import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import es.fandango.data.model.Thumbnail;
import io.micronaut.http.multipart.CompletedFileUpload;

import java.io.IOException;

public interface ImageManager {

    /**
     * Build the Image from the StreamingFileUpload
     *
     * @param file The file
     * @return The image
     * @throws IOException The exception
     */
    Image buildImage(CompletedFileUpload file) throws IOException;

    /**
     * Create the Thumbnail from the file
     *
     * @param image The original image
     * @return The Thumbnail
     */
    Thumbnail buildThumbnail(Image image);

    /**
     * Resize the image from the original image
     *
     * @param image  The target image
     * @param width  The width
     * @param height The target height
     * @return The Image Resized
     */
    ImageResized buildResizedImage(
            Image image,
            Integer width,
            Integer height
    );
}
