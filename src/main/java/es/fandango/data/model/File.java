package es.fandango.data.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Pojo for Image
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class File {

    /**
     * The file _id
     */
    private ObjectId id;

    /**
     * The data
     */
    private byte[] data;

    /**
     * The name
     */
    private String name;

    /**
     * The content Type
     */
    private String contentType;
}
