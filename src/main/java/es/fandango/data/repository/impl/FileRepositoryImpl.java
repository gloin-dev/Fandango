package es.fandango.data.repository.impl;

import es.fandango.data.config.MongoRepository;
import es.fandango.data.model.File;
import es.fandango.data.model.info.Info;
import es.fandango.data.repository.FileRepository;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
    public Single<List<Info>> getAllImagesInfo() {
        // Return all imageId object list
        return Flowable.fromPublisher(
                mongoRepository
                        .fileCollection()
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
    public Maybe<File> getFile(String fileId) {

        Bson filter = eq(new ObjectId(fileId));

        // Return the image
        return Flowable
                .fromPublisher(
                        mongoRepository
                                .fileCollection()
                                .find(filter, File.class)
                                .limit(1)
                ).firstElement();
    }

    @Override
    public Single<File> saveFile(File file) {

        // Save and return the image
        return Single
                .fromPublisher(
                        mongoRepository
                                .fileCollection()
                                .insertOne(file)
                )
                .map(success -> file);
    }
}
