package es.fandango.api.response.common;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.MutableHttpResponse;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
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
@Introspected
public class CommonResponseApi<T> {
    /**
     * The Response Api
     */
    @Schema(description = "Common response for data stream")
    public @NonNull Single<MutableHttpResponse<Object>> httpResponse;
}
