package es.fandango.data.repository;

import es.fandango.data.model.File;
import org.reactivestreams.Publisher;

/** TODO : Refactor in the same way as Image and Thumbnail Repository */
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
