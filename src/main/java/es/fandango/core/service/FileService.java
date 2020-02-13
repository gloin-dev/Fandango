package es.fandango.core.service;

import es.fandango.data.model.File;
import es.fandango.data.model.info.Info;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.io.IOException;
import java.util.List;

public interface FileService {

    /**
     * Get all files info list
     *
     * @return The info list
     */
    Single<List<Info>> getAllFilesInfo();


    /**
     * Get the file for given id
     *
     * @param fileId The file Id
     * @return The file
     */
    Maybe<File> getFileById(String fileId);

    /**
     * Process and save an file upload
     *
     * @param file The File
     * @return The file id
     * @throws IOException A process exception
     */
    Single<String> processFileUpload(CompletedFileUpload file) throws IOException;
}
