package es.fandango.repository.impl;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.model.Image;
import es.fandango.repository.ImageRepository;
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
public class ImageRepositoryImpl implements ImageRepository {

  /** The mongo repository */
  @Inject
  MongoRepository mongoRepository;

  @Override
  public Publisher<Image> getImage(String imageId) {

    // Build the search filter
    Bson filter = eq(new ObjectId(imageId));

    // Return the image
    return mongoRepository
        .imageCollection()
        .find(filter, Image.class)
        .first();
  }

  @Override
  public String saveImageAndGetId(Image image) {

    Success success = Observable.fromPublisher(
        mongoRepository
            .imageCollection()
            .insertOne(image))
        .blockingFirst();

    log.info(success.name() + " saved Image with id : " + image.getId().toString());

    return image
        .getId()
        .toString();
  }
}
