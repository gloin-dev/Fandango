package es.fandango.repository.impl.common;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import es.fandango.config.MongoConfigurationProperties;
import es.fandango.model.Image;
import es.fandango.model.Thumbnail;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class MongoRepository {

  /** The MongoDB client */
  private final MongoClient mongoClient;

  /** The MongoDB Configuration Properties */
  private final MongoConfigurationProperties mongoProperties;

  /**
   * Get the Mongo Database
   *
   * @return The Mongo Database
   */
  private MongoDatabase mongoDatabase() {
    return mongoClient.getDatabase(mongoProperties.database);
  }

  /**
   * Get the mongo image collection
   *
   * @return The image collection
   */
  public MongoCollection<Image> imageCollection() {
    return mongoDatabase()
        .getCollection(
            mongoProperties.imageCollection,
            Image.class
        );
  }

  /**
   * Get the mongo thumbnail collection
   *
   * @return The thumbnail collection
   */
  public MongoCollection<Thumbnail> thumbnailCollection() {
    return mongoDatabase()
        .getCollection(
            mongoProperties.thumbnailCollection,
            Thumbnail.class
        );
  }
}
