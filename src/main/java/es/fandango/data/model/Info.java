package es.fandango.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Object to get info about collection
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Info {

    /**
     * The target id
     */
    private String id;

    /**
     * The name
     */
    private String name;

    /**
     * The content Type
     */
    private String contentType;
}
