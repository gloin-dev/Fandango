package es.fandango.core.manager.impl;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import es.fandango.core.manager.ImageManager;
import es.fandango.data.model.Image;
import es.fandango.data.model.ImageResized;
import es.fandango.data.model.Thumbnail;
import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.http.MediaType;
import io.micronaut.http.multipart.CompletedFileUpload;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import javax.imageio.ImageIO;
import javax.inject.Singleton;
import javax.validation.constraints.NotBlank;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Slf4j(topic = "ImageManager")
@Singleton
@ConfigurationProperties("image")
public class ImageManagerImpl implements ImageManager {

    /**
     * Thumbnail size
     */
    @NotBlank
    public Integer thumbnailSize;

    @SneakyThrows
    @Override
    public Image buildImage(CompletedFileUpload file) {
        // Get Content Type
        MediaType contentType = file
                .getContentType()
                .orElse(new MediaType("*/*"));

        // Build the image
        return new Image(
                new ObjectId(),
                file.getBytes(),
                file.getFilename(),
                contentType.getName(),
                file.getSize()
        );
    }

    @Override
    public Thumbnail buildThumbnail(Image image) {
        // Get the resizedImage
        byte[] imageBytes = scaleImage(
                image.getData(),
                image.getContentType().split("/")[1],
                thumbnailSize,
                thumbnailSize
        );

        // Build thumbnailSize pojo
        return new Thumbnail(
                image.getId(),
                imageBytes,
                image.getName(),
                image.getContentType(),
                (long) imageBytes.length
        );
    }

    @Override
    public ImageResized buildResizedImage(
            Image image,
            Integer width,
            Integer height
    ) {
        // The ImageBytes
        byte[] imageBytes = scaleImage(
                image.getData(),
                image.getContentType().split("/")[1],
                width,
                height
        );

        // Build thumbnailSize pojo
        return new ImageResized(
                new ObjectId(),
                imageBytes,
                image.getName(),
                image.getContentType(),
                image.getId().toString().concat(String.valueOf(width)),
                width,
                height,
                (long) imageBytes.length
        );
    }

    /**
     * Generate the thumbnailSize from the original image
     *
     * @param originalImage The original image
     * @return The resized image
     */
    private byte[] scaleImage(
            byte[] originalImage,
            String contentType,
            Integer newWidth,
            Integer newHeight
    ) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                originalImage
        );

        try {

            BufferedImage image = ImageIO.read(byteArrayInputStream);

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
                    TYPE_INT_RGB
            );

            Graphics2D graphics2D = newImage.createGraphics();

            graphics2D.setRenderingHint(
                    RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR
            );

            graphics2D.drawImage(
                    image,
                    0,
                    0,
                    newWidth,
                    newHeight,
                    null
            );

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ImageIO.write(
                    newImage,
                    contentType,
                    outputStream
            );

            return outputStream.toByteArray();
        } catch (Exception exception) {
            log.error("Error -> {}", exception.getLocalizedMessage());
            return originalImage;
        }
    }
}
