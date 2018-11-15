package es.fandango.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import javax.validation.constraints.NotBlank;

/**
 * The MongoDB Configuration Properties from yml file
 */
@ConfigurationProperties("mongodb")
public class MongoConfigurationProperties {

  /** Database connection url */
  @NotBlank public
  String database;

  /** image collection */
  @NotBlank public
  String imageCollection;

  /** thumbnail collection */
  @NotBlank public
  String thumbnailCollection;

  /** file collection */
  @NotBlank public
  String fileCollection;
}
