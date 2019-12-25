package es.fandango.core.service.impl;

import es.fandango.core.manager.ImageManager;
import es.fandango.core.service.ImageService;
import es.fandango.data.model.Image;
import es.fandango.data.model.Info;
import es.fandango.data.model.Thumbnail;
import es.fandango.data.repository.ImageRepository;
import es.fandango.data.repository.ThumbnailRepository;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
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
    public Single<String> processImageUpload(CompletedFileUpload completedFileUpload) throws IOException {

        // Build the Image from the StreamingFileUpload and return the image and the File
        Image image = imageManager.buildImageInfo(completedFileUpload);
        // Build the thumbnail from the image
        Thumbnail thumbnail = imageManager.buildThumbnail(image, completedFileUpload);
        // Save the image and get the id to generate the thumbnail
        Single<Image> savedImage = imageRepository.saveImage(image);
        // Save the thumbnail and get the id to generate the response
        Single<Thumbnail> savedThumbnail = thumbnailRepository.saveThumbnail(thumbnail);
        // Combine both Operations
        return Single.fromObservable(
                Observable.zip(
                        savedImage.toObservable(),
                        savedThumbnail.toObservable(),
                        (image1, thumbnail1) -> image.getId().toString()
                ));
    }
}
