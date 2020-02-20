package es.fandango.core.service.impl;

import es.fandango.core.manager.ImageManager;
import es.fandango.core.service.ImageResizedService;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import es.fandango.data.repository.ImageRepository;
import es.fandango.data.repository.ImageResizedRepository;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
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
        Maybe<ImageResized> imageResized = imageResizedRepository.getImageResized(
                imageId,
                width,
                height
        );

        return imageResized
                .switchIfEmpty(
                        Maybe.fromSingle(
                                buildNewResizedImage(
                                        imageId,
                                        width,
                                        height
                                )
                        )
                );
    }

    private Single<ImageResized> buildNewResizedImage(
            String imageId,
            Integer width,
            Integer height
    ) {
        Maybe<Image> imageMaybe = imageRepository.getImage(imageId);

        return imageMaybe
                .switchIfEmpty(Maybe.never())
                .flatMapSingle((Function<Image, SingleSource<ImageResized>>) image -> {

                    ImageResized imageResized = imageManager.buildResizedImage(
                            image,
                            width,
                            height
                    );

                    return imageResizedRepository.saveImageResized(imageResized);
                });
    }
}
