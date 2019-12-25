package es.fandango.core.service.impl;

import es.fandango.core.manager.ImageManager;
import es.fandango.core.service.ImageResizedService;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import es.fandango.data.repository.ImageRepository;
import es.fandango.data.repository.ImageResizedRepository;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Slf4j(topic = "ImageResized")
@Singleton
public class ImageResizedServiceImpl implements ImageResizedService {

    /**
     * The Image resized repository
     */
    private final ImageResizedRepository imageResizedRepository;

    /**
     * The Image resized repository
     */
    private final ImageRepository imageRepository;

    /**
     * The image manager
     */
    private final ImageManager imageManager;

    /**
     * Public constructor for Image resized service
     *
     * @param imageResizedRepository The image resized repository
     */
    public ImageResizedServiceImpl(
            ImageResizedRepository imageResizedRepository,
            ImageRepository imageRepository,
            ImageManager imageManager
    ) {
        this.imageResizedRepository = imageResizedRepository;
        this.imageRepository = imageRepository;
        this.imageManager = imageManager;
    }

    @Override
    public Maybe<ImageResized> getResizedImageById(
            String imageId,
            Integer width,
            Integer height
    ) {

        // Find the image
        Single<ImageResized> imageResized = imageResizedRepository.getImageResized(
                imageId,
                width,
                height
        );

        return Maybe.fromSingle(imageResized
                .doOnSuccess(imageResized1 -> log.info("Recovered resized image : {}", imageResized1.getName()))
                .onErrorReturn(throwable -> buildNewResizedImage(
                        imageId,
                        width,
                        height))
        );
    }

    private ImageResized buildNewResizedImage(
            String imageId,
            Integer width,
            Integer height
    ) {
        Maybe<Image> imageMaybe = imageRepository.getImage(imageId);
        Image image = imageMaybe.blockingGet();
        ImageResized imageResized = imageManager.buildResizedImage(
                image,
                width,
                height
        );
        imageResizedRepository.saveImageResized(imageResized);
        return imageResized;

    }
}
