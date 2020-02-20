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
 * Pojo for Thumbnail
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Introspected
public class Thumbnail extends CommonDataModel {

    @Builder
    public Thumbnail(
            ObjectId id,
            byte[] data,
            String name,
            String contentType,
            Long length
    ) {
        super(id, data, name, contentType, length);
    }
}
