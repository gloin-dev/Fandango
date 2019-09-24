package es.fandango.data.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import es.fandango.data.model.File;
import es.fandango.data.model.Image;
import es.fandango.data.model.Thumbnail;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class MongoRepository {

  /** The MongoDB client */
  @Inject
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

  /**
   * Get the mongo file collection
   *
   * @return The file collection
   */
  public MongoCollection<File> fileCollection() {
    return mongoDatabase()
        .getCollection(
            mongoProperties.fileCollection,
            File.class
        );
  }
}
