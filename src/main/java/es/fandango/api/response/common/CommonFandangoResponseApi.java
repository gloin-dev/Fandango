package es.fandango.api.response.common;

import io.micronaut.http.MutableHttpResponse;
import io.reactivex.Single;
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
    public Single<MutableHttpResponse<Object>> responseApi;
}
