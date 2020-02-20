package es.fandango.core.manager;

import es.fandango.data.model.File;
import io.micronaut.http.multipart.CompletedFileUpload;

import java.io.IOException;

public interface FileManager {

    /**
     * Build the File object from the Streaming File Upload
     *
     * @param streamingFileUpload The streaming file upload
     * @return The file
     * @throws IOException The exception
     */
    File buildFile(CompletedFileUpload streamingFileUpload) throws IOException;
}
