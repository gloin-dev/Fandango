package es.fandango.api.controller;

import static io.micronaut.http.HttpHeaders.CONTENT_TYPE;
import static io.micronaut.http.MediaType.MULTIPART_FORM_DATA;

import es.fandango.api.response.common.ElementId;
import io.micronaut.core.io.ResourceResolver;
import io.micronaut.http.HttpRequestFactory;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class ImageResizedControllerTest {

    @Inject
    @Client("/api")
    RxHttpClient client;

    private File file;

    private String id;

    @BeforeAll
    void injectData() throws URISyntaxException {
        ResourceResolver resourceResolver = new ResourceResolver();
        URL url = resourceResolver.getResource("classpath:files/tux.png").get();
        file = new File(url.toURI());
    }


    @Order(1)
    @Test
    public void test_uploadImage() {

        MultipartBody file = MultipartBody
                .builder()
                .addPart("file", this.file)
                .build();

        MutableHttpRequest<MultipartBody> post = HttpRequestFactory
                .INSTANCE
                .post("/images", file)
                .basicAuth("user", "password")
                .header(CONTENT_TYPE, MULTIPART_FORM_DATA);

        HttpResponse<ElementId> exchange = client.toBlocking().exchange(post, ElementId.class);

        Assertions.assertEquals(200, exchange.code());
        Assertions.assertNotNull(exchange.getBody().get());

        id = exchange.getBody().get().getId();

    }

    @Order(2)
    @Test
    public void test_getResizedImage() throws IOException {
        HttpResponse<byte[]> exchange = client.toBlocking().exchange("/images/" + id + "/300", byte[].class);

        BufferedImage buf = ImageIO.read(new ByteArrayInputStream(exchange.body()));
        int height = buf.getHeight();

        Assertions.assertEquals(200, exchange.code());
        Assertions.assertEquals(300, height);
        Assertions.assertNotNull(exchange.body());
    }
}
