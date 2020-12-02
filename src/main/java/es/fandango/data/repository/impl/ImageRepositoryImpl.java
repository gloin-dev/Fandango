package es.fandango.data.repository.impl;

import static com.mongodb.client.model.Filters.eq;

import es.fandango.data.config.MongoRepository;
import es.fandango.data.model.Image;
import es.fandango.data.model.info.Info;
import es.fandango.data.repository.ImageRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.inject.Singleton;
import java.util.List;

@Slf4j
@Singleton
public class ImageRepositoryImpl implements ImageRepository {

    /**
     * The mongo repository
     */
    private final MongoRepository mongoRepository;

    /**
     * Constructor for Mongo Repository
     *
     * @param mongoRepository The Mongo Repository
     */
    public ImageRepositoryImpl(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }


    @Override
    public Single<List<Info>> getAllImageIds() {
        // Return all imageId object list
        return Flowable.fromPublisher(
                mongoRepository
                        .imageCollection()
                        .find()
        )
                .map(image -> new Info(
                        image.getId().toString(),
                        image.getName(),
                        image.getContentType(),
                        image.getLength()
                ))
                .toList();
    }

    @Override
    public Maybe<Image> getImage(String imageId) {

        // Build the search filter
        Bson filter = eq(new ObjectId(imageId));

        // Return the image
        return Flowable
                .fromPublisher(
                        mongoRepository
                                .imageCollection()
                                .find(filter, Image.class)
                                .limit(1)
                ).firstElement();
    }

    @Override
    public Single<Image> saveImage(Image image) {
        // Save and return the image
        return Single
                .fromPublisher(
                        mongoRepository
                                .imageCollection()
                                .insertOne(image)
                )
                .map(success -> image);
    }

    @Override
    public Single<String> deleteImage(String imageId) {

        // Build the search filter
        Bson filter = eq(new ObjectId(imageId));

        // Return the image
        return Single
                .fromPublisher(
                        mongoRepository
                                .imageCollection()
                                .findOneAndDelete(filter)

                )
                .map(success -> imageId);
    }
}
