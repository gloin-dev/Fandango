package es.fandango.data.model.info;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Object to get info about collection
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
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

    /**
     * The file length
     */
    private Long length;
}
