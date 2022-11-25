package es.fandango.core.service.impl;

import es.fandango.core.service.ThumbnailService;
import es.fandango.data.model.Thumbnail;
import es.fandango.data.repository.ThumbnailRepository;
import io.reactivex.rxjava3.core.Maybe;
import jakarta.inject.Singleton;

@Singleton
public class ThumbnailServiceImpl implements ThumbnailService {

    /**
     * The Thumbnail repository
     */
    private final ThumbnailRepository thumbnailRepository;

    /**
     * Public constructor for Thumbnail Service
     *
     * @param thumbnailRepository The thumbnail repository
     */
    public ThumbnailServiceImpl(ThumbnailRepository thumbnailRepository) {
        this.thumbnailRepository = thumbnailRepository;
    }

    @Override
    public Maybe<Thumbnail> getThumbnailById(String thumbnailId) {
        return thumbnailRepository.getThumbnail(thumbnailId);
    }
}
