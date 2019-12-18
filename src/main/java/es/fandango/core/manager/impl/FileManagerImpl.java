package es.fandango.core.manager.impl;

import es.fandango.core.manager.FileManager;
import es.fandango.data.model.File;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import org.bson.types.ObjectId;

import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class FileManagerImpl implements FileManager {

    /**
     * Convert the input stream to file
     *
     * @param streamingFileUpload The uploaded file
     * @return The File object
     * @throws IOException The Exception
     */
    @Override
    public File buildFile(CompletedFileUpload streamingFileUpload) throws IOException {

        // Get Content Type
        MediaType contentType = streamingFileUpload
                .getContentType()
                .orElse(new MediaType("*/*")
                );

        return new File(
                new ObjectId(),
                streamingFileUpload.getBytes(),
                streamingFileUpload.getFilename(),
                contentType.getName()
        );
    }
}
