package es.fandango.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

/**
 * Pojo for ImageResized
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageResized {

  /** The image _id */
  private ObjectId id;

  /** The data */
  private byte[] data;

  /** The name */
  private String name;

  /** The content Type */
  private String contentType;
}
