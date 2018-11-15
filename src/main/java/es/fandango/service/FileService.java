package es.fandango.service;

import es.fandango.model.File;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.io.IOException;

public interface FileService {

  /**
   * Get the file for given id
   *
   * @param fileId The file Id
   * @return The file
   */
  Observable<File> getFileById(String fileId);

  /**
   * Process and save an file upload
   *
   * @param file The File
   * @return The file id
   * @throws IOException A process exception
   */
  String processFileUpload(StreamingFileUpload file) throws IOException;
}
