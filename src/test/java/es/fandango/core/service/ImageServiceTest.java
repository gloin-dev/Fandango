package es.fandango.core.service;

import es.fandango.data.model.Image;
import es.fandango.data.model.ImageId;
import es.fandango.data.model.Thumbnail;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.micronaut.http.server.netty.multipart.NettyCompletedFileUpload;
import io.micronaut.test.annotation.MicronautTest;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import io.reactivex.Single;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class ImageServiceTest {

  private String searchId;

  @Inject
  ImageService imageService;

  /** The file upload */
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
  void test_getImageById() {

    Image image = imageService
        .getImageById(searchId)
        .blockingGet();

    Assertions.assertNotNull(image);
  }

  @Test
  @Order(3)
  void test_getThumbnailById() {

    Thumbnail thumbnail = imageService
            .getThumbnailById(searchId)
            .blockingGet();

    Assertions.assertNotNull(thumbnail);
  }

  @Test
  @Order(4)
  void test_getAllImageIds() {

    List<ImageId> imageIds = imageService
            .getAllImageIds()
            .blockingGet();

    Assertions.assertEquals(1, imageIds.size());
  }
}
