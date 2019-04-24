package es.fandango.service.impl;

import es.fandango.model.File;
import es.fandango.repository.FileRepository;
import es.fandango.service.FileService;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

@Singleton
@AllArgsConstructor
public class FileServiceImpl implements FileService {

  /** The File repository */
  @Inject
  FileRepository fileRepository;

  @Override
  public Observable<File> getFileById(String fileId) {

    // Get the File
    Publisher<File> file = fileRepository.getFile(fileId);
    // Return the File
    return Observable.fromPublisher(file);
  }

  @Override
  public String processFileUpload(StreamingFileUpload streamingFileUpload) throws IOException {

    // Build the file from stream
    File file = buildFile(streamingFileUpload);
    // Save and return the file id
    return fileRepository.saveFileAndGetId(file);
  }

  /**
   * Convert the input stream to file
   *
   * @param streamingFileUpload The uploaded file
   * @return The File object
   * @throws IOException The Exception
   */
  private File buildFile(StreamingFileUpload streamingFileUpload) throws IOException {

    // Build temp file from StreamingFileUpload
    java.io.File tempFile = java.io.File.createTempFile(
        streamingFileUpload.getFilename(),
        "tempFile"
    );

    // Get Content Type
    MediaType contentType = streamingFileUpload
        .getContentType()
        .orElse(new MediaType("*/*")
        );

    // Transfer the file
    Boolean result = Observable.fromPublisher(
        streamingFileUpload.transferTo(tempFile))
        .blockingFirst();

    // Build the file
    return new File(
        new ObjectId(),
        IOUtils.toByteArray(tempFile.toURI()),
        streamingFileUpload.getFilename(),
        contentType.getName());
  }
}
