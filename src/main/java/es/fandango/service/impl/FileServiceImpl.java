package es.fandango.service.impl;

import es.fandango.model.File;
import es.fandango.repository.FileRepository;
import es.fandango.service.FileService;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class FileServiceImpl implements FileService {

  /** The File repository */
  @Inject
  FileRepository fileRepository;

  @Override public Observable<File> getFileById(String fileId) {
    return Observable
        .fromPublisher(fileRepository.getFile(fileId));
  }

  @Override public String processFileUpload(
      StreamingFileUpload file
  ) throws IOException {

    return "";
  }
}
