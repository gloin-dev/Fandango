package es.fandango.service.impl;

import com.mongodb.reactivestreams.client.Success;
import es.fandango.manager.ImageManager;
import es.fandango.model.Image;
import es.fandango.model.ImageId;
import es.fandango.model.Thumbnail;
import es.fandango.repository.ImageRepository;
import es.fandango.repository.ThumbnailRepository;
import es.fandango.service.ImageService;
import io.micronaut.http.multipart.CompletedFileUpload;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.util.Pair;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Singleton
public class ImageServiceImpl implements ImageService {

  /** The Image repository */
  @Inject
  ImageRepository imageRepository;

  /** The Thumbnail repository */
  @Inject
  ThumbnailRepository thumbnailRepository;

  /** The module to handle image operations */
  @Inject
  ImageManager imageManager;

  @Override
  public Maybe<Image> getImageById(String imageId) {
    // Get the Image
    return imageRepository.getImage(imageId);
  }

  @Override
  public Single<List<ImageId>> getAllImageIds() {
    // Get the Image
    return imageRepository.getAllImageIds();
  }

  @Override
  public Maybe<Thumbnail> getThumbnailById(String thumbnailId) {
    // Get the Thumbnail
    return thumbnailRepository.getThumbnail(thumbnailId);
  }

  @Override
  public Single<Image> processImageUpload(
      CompletedFileUpload completedFileUpload
  ) throws IOException {

    // Build the Image from the StreamingFileUpload and return the image and the File
    Image image = imageManager.buildImageInfo(completedFileUpload);
    // Save the image and get the id to generate the thumbnail
    Single<Image> imageSingle = imageRepository.saveImage(image);
    // Build the thumbnail from the image
    Thumbnail thumbnail = imageManager.buildThumbnail(image, completedFileUpload);
    // Save the thumbnail and get the id to generate the response
    Success success = thumbnailRepository.saveThumbnail(thumbnail);
    // Return the Image id
    return imageSingle;
  }
}
