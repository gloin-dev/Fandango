package es.fandango.core.manager;

import es.fandango.data.model.Image;
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
    Image buildImageInfo(CompletedFileUpload file) throws IOException;

    /**
     * Create the Thumbnail from the file
     *
     * @param image The original image
     * @param file  The original file
     * @return The Thumbnail
     */
    Thumbnail buildThumbnail(Image image, CompletedFileUpload file);
}
