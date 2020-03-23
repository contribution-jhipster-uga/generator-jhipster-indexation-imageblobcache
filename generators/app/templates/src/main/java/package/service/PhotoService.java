//<--! package -->

//<--! import -->

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Service Interface for managing {@link sample.lazyblob.domain.Photo}.
 */
public interface PhotoService {

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    PhotoDTO save(PhotoDTO photoDTO);

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhotoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" photo.
     *
     * @param id the id of the entity.
     * @return the entity without image.
     */
    Optional<PhotoDTO> findOne(Long id);

    /**
     * Get the "id" photo.
     *
     * @param id the id of the entity.
     * @return the entity with image.
     */
    Optional<PhotoDTO> findOneWithImage(Long id);

    /**
     * Delete the "id" photo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);



    /**
     * Search for the photo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhotoDTO> search(String query, Pageable pageable);

    /**
     * Search for the photo corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PhotoDTO> searchByIndex(String query, Pageable pageable);
}
