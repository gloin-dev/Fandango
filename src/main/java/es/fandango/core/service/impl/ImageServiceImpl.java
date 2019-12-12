package es.fandango.core.service.impl;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.core.manager.impl.ImageManagerImpl;
import es.fandango.core.service.ImageService;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageId;
import es.fandango.data.model.Thumbnail;
import es.fandango.data.repository.ImageRepository;
import es.fandango.data.repository.ThumbnailRepository;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Slf4j
@Singleton
public class ImageServiceImpl implements ImageService {

    /**
     * The Image repository
     */
    @Inject
    ImageRepository imageRepository;

    /**
     * The Thumbnail repository
     */
    @Inject
    ThumbnailRepository thumbnailRepository;

    /**
     * The module to handle image operations
     */
    @Inject
    ImageManagerImpl imageManager;

    @Override
    public Maybe<Image> getImageById(String imageId) {
        return imageRepository.getImage(imageId);
    }

    @Override
    public Single<List<ImageId>> getAllImageIds() {
        return imageRepository.getAllImageIds();
    }

    @Override
    public Maybe<Thumbnail> getThumbnailById(String thumbnailId) {
        return thumbnailRepository.getThumbnail(thumbnailId);
    }

    @Override
    public Single<Image> processImageUpload(
            CompletedFileUpload completedFileUpload
    ) throws IOException {

        // Build the Image from the StreamingFileUpload and return the image and the File
        Image image = imageManager.buildImageInfo(completedFileUpload);
        // Save the image and get the id to generate the thumbnail
        Single<Image> imageSingle = imageRepository.saveImage(image);
        // Build the thumbnail from the image
        Thumbnail thumbnail = imageManager.buildThumbnail(image, completedFileUpload);
        // Save the thumbnail and get the id to generate the response
        Success success = thumbnailRepository.saveThumbnail(thumbnail);
        // Return the Image id
        return imageSingle;
    }
}
