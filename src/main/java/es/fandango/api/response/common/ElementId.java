package es.fandango.api.response.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Element to wrap id of new elements when upload
 */
@AllArgsConstructor
@Data
@Schema(name = "ElementId", description = "New element creation id")
public class ElementId {

    private String id;

    public static ElementId buildWithId(String id) {
        return new ElementId(id);
    }
}
