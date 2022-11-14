package es.fandango.core.manager;

import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import es.fandango.data.model.Thumbnail;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import javax.imageio.ImageIO;
import jakarta.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class ImageManagerTest {

    @Inject
    ImageManager imageManager;

    /**
     * The file upload
     */
    private CompletedFileUpload fileUpload;

    /**
     * The generated image
     */
    private Image image;

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
    void test_buildImage() throws IOException {

        image = imageManager.buildImage(fileUpload);

        Assertions.assertNotNull(image);
    }

    @Test
    @Order(2)
    void test_buildThumbnail() {

        Thumbnail thumbnail = imageManager.buildThumbnail(image);

        Assertions.assertNotNull(thumbnail);
    }

    @Test
    @Order(3)
    void test_buildResizedImage() throws IOException {

        ImageResized imageResized = imageManager.buildResizedImage(
                image,
                250,
                300
        );

        BufferedImage buf = ImageIO.read(new ByteArrayInputStream(imageResized.getData()));
        int height = buf.getHeight();
        int width = buf.getWidth();

        Assertions.assertEquals(300, height);
        Assertions.assertNotNull(imageResized);
    }
}
