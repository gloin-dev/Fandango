package es.fandango.core.manager;

import es.fandango.core.service.FileService;
import es.fandango.data.model.File;
import es.fandango.data.model.Image;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.micronaut.test.annotation.MicronautTest;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class FileManagerTest {

    @Inject
    FileManager fileManager;

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
    void test_buildFile() throws IOException {

        File file = fileManager.buildFile(fileUpload);

        Assertions.assertNotNull(file);
    }
}
