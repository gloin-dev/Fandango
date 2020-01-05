package es.fandango.api.response.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Element to wrap id of new elements when upload
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ElementId {

    @JsonProperty("id")
    private String id;

    public static ElementId buildWithId(String id) {
        return new ElementId(id);
    }
}
