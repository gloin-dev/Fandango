package es.fandango.data.repository;

import es.fandango.data.model.File;
import es.fandango.data.model.info.Info;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;

public interface FileRepository {

    /**
     * Get all files info object from MongoDB
     *
     * @return The list with all images
     */
    Single<List<Info>> getAllImagesInfo();

    /**
     * Get the file
     *
     * @param fileId The file id
     * @return The file
     */
    Maybe<File> getFile(String fileId);

    /**
     * Save the file and get the id
     *
     * @param file The file
     * @return The file id
     */
    Single<File> saveFile(File file);
}
