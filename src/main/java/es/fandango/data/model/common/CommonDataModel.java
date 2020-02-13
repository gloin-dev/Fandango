package es.fandango.data.model.common;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Common POJO for Image
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Introspected
public class CommonDataModel {

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

    /**
     * The file length
     */
    private Long length;
}
