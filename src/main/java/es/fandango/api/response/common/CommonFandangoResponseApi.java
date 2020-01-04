package es.fandango.api.response.common;

import io.micronaut.http.HttpResponse;
import io.reactivex.Maybe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Common Response Api
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
public class CommonFandangoResponseApi {

    /**
     * The Response Api
     */
    @Schema(description = "Common response for data stream")
    public Maybe<HttpResponse<Object>> responseApi;
}
