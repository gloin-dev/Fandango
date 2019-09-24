package es.fandango.data.repository.impl;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.data.model.Thumbnail;
import es.fandango.data.repository.ThumbnailRepository;
import es.fandango.data.config.MongoRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

@Slf4j
@Singleton
public class ThumbnailRepositoryImpl implements ThumbnailRepository {

  /** The mongo repository */
  @Inject
  MongoRepository mongoRepository;

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
  public Success saveThumbnail(Thumbnail thumbnail) {

    return Observable.fromPublisher(
        mongoRepository
            .thumbnailCollection()
            .insertOne(thumbnail)
    ).blockingFirst();
  }
}
