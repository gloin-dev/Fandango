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
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.http.client.multipart.MultipartBody;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@MicronautTest
public class FileControllerTest {

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
    public void test_uploadFile() {

        MultipartBody file = MultipartBody
                .builder()
                .addPart("file", this.file)
                .build();

        MutableHttpRequest<MultipartBody> post = HttpRequestFactory
                .INSTANCE
                .post("/files", file)
                .basicAuth("user", "password")
                .header(CONTENT_TYPE, MULTIPART_FORM_DATA);

        HttpResponse<ElementId> exchange = client.toBlocking().exchange(post, ElementId.class);

        Assertions.assertEquals(200, exchange.code());
        Assertions.assertNotNull(exchange.getBody().get());

        id = exchange.getBody().get().getId();

    }

    @Order(2)
    @Test
    public void test_getFiles() {
        HttpResponse<List> exchange = client.toBlocking().exchange("/files", List.class);
        Assertions.assertEquals(200, exchange.code());
        Assertions.assertEquals(1, exchange.getBody().get().size());
    }

    @Order(3)
    @Test
    public void test_getFile() {
        HttpResponse<Object> exchange = client.toBlocking().exchange("/files/" + id, Object.class);
        Assertions.assertEquals(200, exchange.code());
    }

    @Order(4)
    @Test
    public void test_uploadFileUnauthorized() {

        MultipartBody file = MultipartBody
                .builder()
                .addPart("file", this.file)
                .build();

        MutableHttpRequest<MultipartBody> post = HttpRequestFactory
                .INSTANCE
                .post("/files", file)
                .header(CONTENT_TYPE, MULTIPART_FORM_DATA);

        Assertions.assertThrows(HttpClientResponseException.class, () -> client
                .toBlocking()
                .exchange(
                        post,
                        ElementId.class
                ));
    }
}
