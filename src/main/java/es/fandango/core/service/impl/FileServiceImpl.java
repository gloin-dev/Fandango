package es.fandango.core.service.impl;

import es.fandango.core.manager.FileManager;
import es.fandango.core.service.FileService;
import es.fandango.data.model.File;
import es.fandango.data.model.info.Info;
import es.fandango.data.repository.FileRepository;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.List;

@Singleton
public class FileServiceImpl implements FileService {

    /**
     * The File repository
     */
    private final FileRepository fileRepository;

    /**
     * The File service
     */
    private final FileManager fileManager;

    /**
     * The File service
     *
     * @param fileRepository The file service repository
     */
    public FileServiceImpl(
            FileRepository fileRepository,
            FileManager fileManager
    ) {
        this.fileRepository = fileRepository;
        this.fileManager = fileManager;
    }

    @Override
    public Single<List<Info>> getAllFilesInfo() {
        return fileRepository.getAllImagesInfo();
    }

    @Override
    public Maybe<File> getFileById(String fileId) {
        return fileRepository.getFile(fileId);
    }

    @Override
    public Single<String> processFileUpload(CompletedFileUpload fileUpload) throws IOException {

        // Build the file from stream
        File file = fileManager.buildFile(fileUpload);
        // Save and return the file id
        Single<File> fileSingle = fileRepository.saveFile(file);
        // Return the id from the saved file
        return fileSingle.map(output -> output.getId().toString());
    }

}
