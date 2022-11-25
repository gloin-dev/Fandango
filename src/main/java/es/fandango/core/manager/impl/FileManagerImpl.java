package es.fandango.core.manager.impl;

import es.fandango.core.manager.FileManager;
import es.fandango.data.model.File;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;
import org.bson.types.ObjectId;

import java.io.IOException;

@Singleton
public class FileManagerImpl implements FileManager {

    /**
     * Convert the input stream to file
     *
     * @param file The uploaded file
     * @return The File object
     * @throws IOException The Exception
     */
    @Override
    public File buildFile(CompletedFileUpload file) throws IOException {
        // Get Content Type
        MediaType contentType = file
                .getContentType()
                .orElse(new MediaType("*/*"));

        return new File(
                new ObjectId(),
                file.getBytes(),
                file.getFilename(),
                contentType.getName(),
                file.getSize()
        );
    }
}
