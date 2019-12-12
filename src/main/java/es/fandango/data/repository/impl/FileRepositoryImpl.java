package es.fandango.data.repository.impl;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.data.config.MongoRepository;
import es.fandango.data.model.File;
import es.fandango.data.repository.FileRepository;
import io.reactivex.Observable;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

import javax.inject.Singleton;

@Slf4j
@Singleton
public class FileRepositoryImpl implements FileRepository {

    /**
     * The mongo repository
     */
    private final MongoRepository mongoRepository;

    /**
     * Constructor for Mongo Repository
     *
     * @param mongoRepository The Mongo Repository
     */
    public FileRepositoryImpl(MongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Publisher<File> getFile(String fileId) {

        // Build the search filter
        Bson filter = eq(new ObjectId(fileId));

        // Return the file
        return mongoRepository
                .fileCollection()
                .find(filter, File.class)
                .first();
    }

    @Override
    public String saveFileAndGetId(File file) {

        Success success = Observable.fromPublisher(
                mongoRepository
                        .fileCollection()
                        .insertOne(file))
                .blockingFirst();

        log.info(success.name() + " saved File with id : " + file.getId().toString());

        return file
                .getId()
                .toString();
    }
}
