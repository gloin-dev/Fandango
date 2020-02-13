package es.fandango.core.service;

import es.fandango.data.model.File;
import es.fandango.data.model.info.Info;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.micronaut.test.annotation.MicronautTest;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class FileServiceTest {

    private String searchId;

    @Inject
    FileService fileService;

    /**
     * The file upload
     */
    private CompletedFileUpload fileUpload;

    @BeforeAll
    void injectData() throws IOException, URISyntaxException {

        ResourceResolver resourceResolver = new ResourceResolver();

        DiskFileUpload diskFileUpload = new DiskFileUpload(
                "tux",
                "tux.png",
                "image/png",
                "binary",
                Charset.defaultCharset(),
                100
        );

        URL url = resourceResolver.getResource("classpath:files/tux.png").get();
        diskFileUpload.setContent(new java.io.File(url.toURI()));
        fileUpload = new NettyCompletedFileUpload(diskFileUpload);
    }

    @Test
    @Order(1)
    void test_processFileUpload() throws IOException {

        Single<String> imageSingle = fileService.processFileUpload(fileUpload);

        searchId = imageSingle.blockingGet();

        Assertions.assertNotNull(searchId);
    }

    @Test
    @Order(2)
    void test_getFileById() {

        Maybe<File> fileById = fileService.getFileById(searchId);

        File file = fileById.blockingGet();

        Assertions.assertNotNull(file);
    }

    @Test
    @Order(3)
    void test_getAllFilesInfo() {

        Single<List<Info>> allFilesInfo = fileService.getAllFilesInfo();

        List<Info> infos = allFilesInfo.blockingGet();

        Assertions.assertEquals(1,infos.size());
    }
}
