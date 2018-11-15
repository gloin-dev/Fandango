package es.fandango.repository.impl;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.model.Thumbnail;
import es.fandango.repository.ThumbnailRepository;
import es.fandango.repository.impl.common.MongoRepository;
import io.reactivex.Observable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.java.Log;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

import static com.mongodb.client.model.Filters.eq;

@Log
@Singleton
public class ThumbnailRepositoryImpl implements ThumbnailRepository {

  /** The mongo repository */
  @Inject
  MongoRepository mongoRepository;

  @Override
  public Publisher<Thumbnail> getThumbnail(String thumbnailId) {
    // Build the search filter
    Bson filter = eq(new ObjectId(thumbnailId));

    // Return the image
    return mongoRepository
        .thumbnailCollection()
        .find(filter, Thumbnail.class)
        .first();
  }

  @Override
  public void saveThumbnail(Thumbnail thumbnail) {

    Success success = Observable.fromPublisher(
        mongoRepository
            .thumbnailCollection()
            .insertOne(thumbnail)
    ).blockingFirst();

    log.info(success.name() + " saved Thumbnail with id : " + thumbnail.getId().toString());
  }
}
