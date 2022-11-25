package es.fandango.core.service.impl;

import es.fandango.core.manager.ImageManager;
import es.fandango.core.service.ImageService;
import es.fandango.data.model.Image;
import es.fandango.data.model.Thumbnail;
import es.fandango.data.model.info.Info;
import es.fandango.data.repository.ImageRepository;
import es.fandango.data.repository.ThumbnailRepository;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
@Singleton
public class ImageServiceImpl implements ImageService {

    /**
     * The Image repository
     */
    private final ImageRepository imageRepository;

    /**
     * The Thumbnail repository
     */
    private final ThumbnailRepository thumbnailRepository;

    /**
     * The module to handle image operations
     */
    private final ImageManager imageManager;

    /**
     * Full constructor for Image Service
     *
     * @param imageRepository     The image repository
     * @param thumbnailRepository The thumbnail repository
     * @param imageManager        The image manager
     */
    public ImageServiceImpl(
            ImageRepository imageRepository,
            ThumbnailRepository thumbnailRepository,
            ImageManager imageManager
    ) {
        this.imageRepository = imageRepository;
        this.thumbnailRepository = thumbnailRepository;
        this.imageManager = imageManager;
    }

    @Override
    public Maybe<Image> getImageById(String imageId) {
        return imageRepository.getImage(imageId);
    }

    @Override
    public Single<List<Info>> getAllImagesInfo() {
        return imageRepository.getAllImageIds();
    }

    @Override
    public Single<String> deleteImageById(String imageId) {
        // Delete the image
        Single<String> deletedImage = imageRepository.deleteImage(imageId);
        // Delete the thumbnail
        Single<String> deletedThumbnail = thumbnailRepository.deleteThumbnail(imageId);
        // Combine both Operations
        return Single.zip(
                deletedImage,
                deletedThumbnail,
                (outputImageId, outputThumbnailId) -> outputImageId
        );
    }

    @Override
    public Single<String> processImageUpload(CompletedFileUpload completedFileUpload) throws IOException {
        // Build the image
        Image image = imageManager.buildImage(completedFileUpload);
        // Build the thumbnail
        Thumbnail thumbnail = imageManager.buildThumbnail(image);
        // Save the image
        Single<Image> savedImage = imageRepository.saveImage(image);
        // Save the thumbnail
        Single<Thumbnail> savedThumbnail = thumbnailRepository.saveThumbnail(thumbnail);
        // Combine both Operations
        return Single.zip(
                savedImage,
                savedThumbnail,
                (outputImage, outputThumbnail) -> image.getId().toString()
        );
    }
}
