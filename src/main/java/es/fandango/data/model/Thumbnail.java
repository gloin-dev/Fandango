package es.fandango.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Pojo for Thumbnail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Thumbnail {

    /**
     * The image _id
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
