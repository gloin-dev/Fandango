package es.fandango.data.model;

import es.fandango.data.model.common.CommonDataModel;
import io.micronaut.core.annotation.Introspected;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;

/**
 * Pojo for Image
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Introspected
public class ImageResized extends CommonDataModel {
    /**
     * The search id
     */
    private String searchId;

    /**
     * The image width
     */
    private Integer width;

    /**
     * The image height
     */
    private Integer height;

    @Builder
    public ImageResized(
            ObjectId id,
            byte[] data,
            String name,
            String contentType,
            String searchId,
            Integer width,
            Integer height,
            Long length
    ) {
        super(id, data, name, contentType, length);
        this.searchId = searchId;
        this.width = width;
        this.height = height;
    }
}
