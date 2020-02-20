package es.fandango.data.config;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import es.fandango.data.model.File;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import es.fandango.data.model.Thumbnail;
import io.micronaut.context.annotation.Context;

@Context
public class MongoRepository {

    /**
     * The MongoDB client
     */
    private final MongoClient mongoClient;

    /**
     * The MongoDB Configuration Properties
     */
    private final MongoConfigurationProperties mongoProperties;

    /**
     * Constructor for MongoRepository config class
     *
     * @param mongoClient     The mongo client
     * @param mongoProperties The mongo properties
     */
    public MongoRepository(
            MongoClient mongoClient,
            MongoConfigurationProperties mongoProperties
    ) {
        this.mongoClient = mongoClient;
        this.mongoProperties = mongoProperties;
    }

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
                .getCollection(mongoProperties.imageCollection, Image.class);
    }

    /**
     * Get the mongo image resized collection
     *
     * @return The image resized collection
     */
    public MongoCollection<ImageResized> imageResizedCollection() {
        return mongoDatabase()
                .getCollection(
                        mongoProperties.imageResizedCollection,
                        ImageResized.class
                );
    }

    /**
     * Get the mongo thumbnail collection
     *
     * @return The thumbnail collection
     */
    public MongoCollection<Thumbnail> thumbnailCollection() {
        return mongoDatabase()
                .getCollection(mongoProperties.thumbnailCollection, Thumbnail.class);
    }

    /**
     * Get the mongo file collection
     *
     * @return The file collection
     */
    public MongoCollection<File> fileCollection() {
        return mongoDatabase()
                .getCollection(mongoProperties.fileCollection, File.class);
    }
}
