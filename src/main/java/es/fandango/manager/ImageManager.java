package es.fandango.manager;

import es.fandango.model.Image;
import es.fandango.model.Thumbnail;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

@Singleton
@ConfigurationProperties("image")
public class ImageManager {

  /** Thumbnail size */
  @NotBlank
  private Integer thumbnailSize;

  /**
   * Build the Image from the StreamingFileUpload
   *
   * @param file The file
   * @return The image
   * @throws IOException The exception
   */
  public Pair<Image, File> buildImageInfo(
      StreamingFileUpload file
  ) throws IOException {

    // Get Content Type
    MediaType contentType = file
        .getContentType()
        .orElse(new MediaType("*/*")
        );

    // Build temp file from StreamingFileUpload
    File tempFile = File.createTempFile(
        file.getFilename(),
        "tempImage"
    );

    // Transfer the file
    Boolean result = Observable.fromPublisher(
        file.transferTo(tempFile))
        .blockingFirst();

    // Build the image
    Image image = new Image(
        new ObjectId(),
        IOUtils.toByteArray(tempFile.toURI()),
        file.getFilename(),
        contentType.getName());

    // Return both objects
    return new Pair<>(
        image,
        tempFile
    );
  }

  /**
   * Create the Thumbnail from the file
   *
   * @param image The original image
   * @param file The original file
   * @return The Thumbnail
   */
  public Thumbnail buildThumbnail(
      Image image,
      File file) {

    // The ImageBytes
    byte[] imageBytes;

    // Get the resizedImage
    BufferedImage resizedImage = generateThumbnailFromOriginalImage(file);

    if (resizedImage != null) {
      imageBytes = toByteArrayAutoClosable(
          resizedImage,
          image.getContentType().split("/")[1]
      );
    } else {
      // If there are some error, use original image ?
      imageBytes = image.getData();
    }

    // Build thumbnailSize pojo
    return new Thumbnail(
        image.getId(),
        imageBytes,
        image.getName(),
        image.getContentType()
    );
  }

  /**
   * Generate the thumbnailSize from the original image
   *
   * @param file The original file
   * @return The resized image
   */
  private BufferedImage generateThumbnailFromOriginalImage(File file) {

    Integer newHeight = thumbnailSize;
    Integer newWidth = thumbnailSize;

    try {
      BufferedImage image = ImageIO.read(file);

      // Make sure the aspect ratio is maintained, so the image is not distorted
      double thumbRatio = (double) newWidth / (double) newHeight;
      int imageWidth = image.getWidth(null);
      int imageHeight = image.getHeight(null);
      double aspectRatio = (double) imageWidth / (double) imageHeight;

      if (thumbRatio < aspectRatio) {
        newHeight = (int) (newWidth / aspectRatio);
      } else {
        newWidth = (int) (newHeight * aspectRatio);
      }

      // Draw the scaled image
      BufferedImage newImage = new BufferedImage(
          newWidth,
          newHeight,
          TYPE_INT_RGB);

      Graphics2D graphics2D = newImage.createGraphics();

      graphics2D.setRenderingHint(
          RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);

      graphics2D.drawImage(
          image,
          0,
          0,
          newWidth,
          newHeight,
          null
      );

      return newImage;
    } catch (Exception ignored) {
      return null;
    }
  }

  /**
   * Build byte image from BufferedImage
   *
   * @param image The image
   * @param type The type
   * @return The byte result image
   * @throws IOException The exception
   */
  private byte[] toByteArrayAutoClosable(BufferedImage image, String type) {
    try {
      try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
        ImageIO.write(image, type, out);
        return out.toByteArray();
      }
    } catch (IOException e) {
      return new byte[0];
    }
  }
}
