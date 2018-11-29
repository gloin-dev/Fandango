package es.fandango.service.impl;

import es.fandango.manager.ImageManager;
import es.fandango.model.Image;
import es.fandango.model.Thumbnail;
import es.fandango.repository.ImageRepository;
import es.fandango.repository.ThumbnailRepository;
import es.fandango.service.ImageService;
import io.micronaut.http.multipart.StreamingFileUpload;
import io.reactivex.Observable;
import java.io.File;
import java.io.IOException;
import javafx.util.Pair;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.reactivestreams.Publisher;

@Log
@Singleton
@AllArgsConstructor
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
  public Observable<Image> getImageById(String imageId) {

    // Get the Image
    Publisher<Image> image = imageRepository.getImage(imageId);
    // Return the Image
    return Observable.fromPublisher(image);
  }

  @Override
  public Observable<Thumbnail> getThumbnailById(String thumbnailId) {

    // Get the Thumbnail
    Publisher<Thumbnail> thumbnail = thumbnailRepository.getThumbnail(thumbnailId);
    // Return the Thumbnail
    return Observable.fromPublisher(thumbnail);
  }

  @Override
  public String processImageUpload(StreamingFileUpload streamingFileUpload)
      throws IOException {

    // Build the Image from the StreamingFileUpload and return the image and the File
    Pair<Image, File> imageFilePair = imageManager.buildImageInfo(streamingFileUpload);
    // Get the image from Pair
    Image image = imageFilePair.getKey();
    // Save the image and get the id to generate the thumbnail
    String newImageId = imageRepository.saveImageAndGetId(image);
    // Build the thumbnail from the image
    Thumbnail thumbnail = imageManager.buildThumbnail(image, imageFilePair.getValue());
    //  Save the thumbnail and get the id to generate the response
    thumbnailRepository.saveThumbnail(thumbnail);
    // Return the Image id
    return newImageId;
  }
}