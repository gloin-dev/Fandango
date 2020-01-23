package es.fandango.api.response.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Element to wrap id of new elements when upload
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Introspected
public class ElementId {

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private Boolean status;

    public static ElementId buildWithId(String id) {
        return new ElementId(id, true);
    }

    public static ElementId notFound() {
        return new ElementId("not found", false);
    }
}
