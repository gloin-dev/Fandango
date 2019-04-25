package es.fandango.repository.impl;

import es.fandango.model.Image;
import es.fandango.model.ImageId;
import es.fandango.repository.ImageRepository;
import es.fandango.repository.impl.common.MongoRepository;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

@Slf4j
@Singleton
public class ImageRepositoryImpl implements ImageRepository {

  /** The mongo repository */
  @Inject
  MongoRepository mongoRepository;

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
  public Single<List<ImageId>> getAllImageIds() {
    // Return all imageId object list
    return Flowable.fromPublisher(
        mongoRepository
            .imageCollection()
            .find()
    ).map(image -> new ImageId(image.getId().toString())).toList();
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
}
