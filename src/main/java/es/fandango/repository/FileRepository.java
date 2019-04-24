package es.fandango.repository;

import es.fandango.model.File;
import javax.inject.Singleton;
import org.reactivestreams.Publisher;

@Singleton
public interface FileRepository {

  /**
   * Get the file
   *
   * @param fileId The file id
   * @return The file
   */
  Publisher<File> getFile(String fileId);

  /**
   * Save the file and get the id
   *
   * @param file The file
   * @return The file id
   */
  String saveFileAndGetId(File file);
}
