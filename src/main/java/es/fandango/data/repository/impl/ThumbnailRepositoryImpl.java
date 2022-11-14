package es.fandango.data.repository.impl;

import es.fandango.data.config.MongoRepository;
import es.fandango.data.model.Thumbnail;
import es.fandango.data.repository.ThumbnailRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

@Slf4j
@Singleton
public class ThumbnailRepositoryImpl implements ThumbnailRepository {

    /**
     * The mongo repository
     */
    private final MongoRepository mongoRepository;

    /**
     * Constructor for Mongo Repository
     *
     * @param mongoRepository The Mongo Repository
     */
    public ThumbnailRepositoryImpl(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Maybe<Thumbnail> getThumbnail(String thumbnailId) {

        // Build the search filter
        Bson filter = eq(new ObjectId(thumbnailId));

        // Return the thumbnail
        return Flowable
                .fromPublisher(
                        mongoRepository
                                .thumbnailCollection()
                                .find(filter, Thumbnail.class)
                                .limit(1)
                ).firstElement();
    }

    @Override
    public Single<Thumbnail> saveThumbnail(Thumbnail thumbnail) {

        return Single.fromPublisher(
                mongoRepository
                        .thumbnailCollection()
                        .insertOne(thumbnail)
        ).map(success -> thumbnail);
    }

    @Override
    public Single<String> deleteThumbnail(String thumbnailId) {

        // Build the search filter
        Bson filter = eq(new ObjectId(thumbnailId));

        // Return the thumbnailId
        return Single
                .fromPublisher(
                        mongoRepository
                                .thumbnailCollection()
                                .findOneAndDelete(filter)

                )
                .map(success -> thumbnailId);
    }
}
