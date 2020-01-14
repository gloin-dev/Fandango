package es.fandango.api.response.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Element to wrap id of new elements when upload
 */
@Data
public class ElementId {

    @JsonProperty("id")
    private String id;

    @JsonProperty("status")
    private Boolean status;

    /**
     * Private constructor
     *
     * @param id     The id
     * @param status The status
     */
    private ElementId(String id, Boolean status) {
        this.id = id;
        this.status = status;
    }

    public static ElementId buildWithId(String id) {
        return new ElementId(id, true);
    }

    public static ElementId notFound() {
        return new ElementId("not found", false);
    }
}
