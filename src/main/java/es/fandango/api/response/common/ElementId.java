package es.fandango.api.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Element to wrap id of new elements when upload
 */
@AllArgsConstructor
@Data
public class ElementId {

    private String id;

    public static ElementId buildWithId(String id){
        return new ElementId(id);
    }
}
