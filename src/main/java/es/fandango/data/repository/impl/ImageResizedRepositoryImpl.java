package es.fandango.data.repository.impl;

import static com.mongodb.client.model.Filters.eq;

import es.fandango.data.config.MongoRepository;
import es.fandango.data.model.ImageResized;
import es.fandango.data.repository.ImageResizedRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class ImageResizedRepositoryImpl implements ImageResizedRepository {

    /**
     * The mongo repository
     */
    private final MongoRepository mongoRepository;

    /**
     * Constructor for Mongo Repository
     *
     * @param mongoRepository The Mongo Repository
     */
    public ImageResizedRepositoryImpl(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Maybe<ImageResized> getImageResized(
            String imageId,
            Integer width,
            Integer height
    ) {

        // Build the search filter
        Bson idFilter = eq("searchId", imageId.concat(String.valueOf(width)));

        // Return the image
        return Flowable
                .fromPublisher(
                        mongoRepository
                                .imageResizedCollection()
                                .find(idFilter, ImageResized.class)
                                .limit(1))
                .firstElement();
    }

    @Override
    public Single<ImageResized> saveImageResized(ImageResized image) {
        // Save and return the image
        return Single
                .fromPublisher(
                        mongoRepository
                                .imageResizedCollection()
                                .insertOne(image)
                )
                .map(success -> image);
    }
}
