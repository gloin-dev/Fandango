package es.fandango.core.service;

import es.fandango.data.model.ImageResized;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.micronaut.test.annotation.MicronautTest;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.reactivex.Single;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class ImageResizedServiceTest {

    private String searchId;

    @Inject
    ImageResizedService imageResizedService;

    @Inject
    ImageService imageService;

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
        diskFileUpload.setContent(new File(url.toURI()));
        fileUpload = new NettyCompletedFileUpload(diskFileUpload);
    }

    @Test
    @Order(1)
    void test_processImageUpload() throws IOException {

        Single<String> imageSingle = imageService.processImageUpload(fileUpload);

        searchId = imageSingle.blockingGet();

        Assertions.assertNotNull(searchId);
    }

    @Test
    @Order(2)
    void test_getResizedImageById() throws IOException {

        ImageResized imageResized = imageResizedService
                .getResizedImageById(
                        searchId,
                        250,
                        300
                )
                .blockingGet();

        BufferedImage buf = ImageIO.read(new ByteArrayInputStream(imageResized.getData()));
        int height = buf.getHeight();
        int width = buf.getWidth();

        Assertions.assertEquals(300, height);
        Assertions.assertNotNull(imageResized);
    }
}
