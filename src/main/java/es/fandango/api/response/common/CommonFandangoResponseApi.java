package es.fandango.api.response.common;

import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Response Api
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonFandangoResponseApi {

    /**
     * The Response Api
     */
    public Maybe<HttpResponse<Object>> responseApi;
}
