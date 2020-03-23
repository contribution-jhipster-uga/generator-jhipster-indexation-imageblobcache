//<--! package -->

//<--! import -->

import com.drew.imaging.ImageProcessingException;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing {@link Photo}.
 */
@Service
@Transactional
public class PhotoServiceImpl implements PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoServiceImpl.class);

    private final PhotoRepository photoRepository;
    private final PhotoLiteRepository photoLiteRepository;
    private final UserRepository userRepository;
    private final PhotoLiteSearchRepository photoLiteSearchRepository;

    private final PhotoLiteMapper photoLiteMapper;
    private final PhotoMapper photoMapper;

    @NotNull(message = "thumbnail.x1.maxDim can not be null")
    @Value("${thumbnail.x1.maxDim}")
    private int x1MaxDim;

    @NotNull(message = "thumbnail.x2.maxDim can not be null")
    @Value("${thumbnail.x2.maxDim}")
    private int x2MaxDim;

    public PhotoServiceImpl(PhotoLiteSearchRepository photoLiteSearchRepository, UserRepository userRepository, PhotoRepository photoRepository, PhotoLiteRepository photoLiteRepository, PhotoLiteMapper photoLiteMapper, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoLiteRepository = photoLiteRepository;
        this.photoMapper = photoMapper;
        this.photoLiteMapper = photoLiteMapper;
        this.userRepository = userRepository;
        this.photoLiteSearchRepository = photoLiteSearchRepository;
    }

    private void reset(PhotoDTO photoDTO) {
        photoDTO.setThumbnailx1(null);
        photoDTO.setThumbnailx1Sha1(null);
        photoDTO.setThumbnailx1ContentType(null);
        photoDTO.setThumbnailx2(null);
        photoDTO.setThumbnailx2Sha1(null);
        photoDTO.setThumbnailx2ContentType(null);
        photoDTO.setExif(null);
        photoDTO.setExtractedText(null);
        photoDTO.setDetectedObjects(null);
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);

        Photo photo = null;
        Instant now = Instant.now();
        if (photoDTO.getId() == null) {
            // create entity
            photoDTO.setCreatedAt(now);
            photoDTO.setUpdatedAt(now);

            SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    photoDTO.setUserId(user.getId());
                  });
        } else {
            // update entity
            Long id = photoDTO.getId();
            if (id != null) {
                Optional<Photo> opt = photoRepository.findById(id);
                if (opt.isPresent()) {
                    photo = opt.get();
                } else {
                    log.error("Photo {} does not exist", id);
                    return null;
                }
            }
            photoDTO.setCreatedAt(photo.getCreatedAt());
            photoDTO.setUpdatedAt(now);
        }
        byte[] image = photoDTO.getImage();
        if (image != null) {
            String sha1Image = SHAUtil.hash((image));
            System.out.println();
            photoDTO.setImageSha1(sha1Image);
            if (photo == null || !photo.getImageSha1().equals(sha1Image)) {
                try {
                    String mimeType = photoDTO.getImageContentType();
                    String formatName = MimeTypes.lookupExt(mimeType);
                    photoDTO.setThumbnailx1(ThumbnailUtil.scale(photoDTO.getImage(), x1MaxDim, formatName));
                    photoDTO.setThumbnailx1Sha1(SHAUtil.hash(photoDTO.getThumbnailx1()));
                    photoDTO.setThumbnailx1ContentType(mimeType);
                    photoDTO.setThumbnailx2(ThumbnailUtil.scale(photoDTO.getImage(), x2MaxDim, formatName));
                    photoDTO.setThumbnailx2Sha1(SHAUtil.hash(photoDTO.getThumbnailx2()));
                    photoDTO.setThumbnailx2ContentType(mimeType);

                    String filename = Indexation.createImagefromByteArray(image);

                    // Extract Exif
                    try {
                        photoDTO.setExif(MetadataUtil.extract(image));
                    } catch (ImageProcessingException e) {
                        log.warn("Can not extract the image metadata", e);
                    }
                    // TODO Extract GPS tag from Metadata

                    // Extract Text with OCR
                    try {
                        photoDTO.setExtractedText(Indexation.parseTextFromImage(filename));
                    } catch (TesseractException e) {
                        log.warn("Can not extract the image text", e);
                    }

                    // Extract Objects with ImageAI
                    try {
                        photoDTO.setDetectedObjects(Indexation.imageAI(filename));
                    } catch (Exception e) {
                        log.warn("Can not extract the image detection object", e);
                    }

                } catch (IOException e) {
                    log.warn("Can not thumbnail the image", e);
                    reset(photoDTO);
                }
            } else {
                photoDTO.setThumbnailx1(photo.getThumbnailx1());
                photoDTO.setThumbnailx1Sha1(photo.getThumbnailx1Sha1());
                photoDTO.setThumbnailx1ContentType(photo.getThumbnailx2ContentType());
                photoDTO.setThumbnailx2(photo.getThumbnailx2());
                photoDTO.setThumbnailx2Sha1(photo.getThumbnailx2Sha1());
                photoDTO.setThumbnailx2ContentType(photo.getThumbnailx2ContentType());
                photoDTO.setExif(photo.getExif());
                photoDTO.setExtractedText(photo.getExtractedText());
                photoDTO.setDetectedObjects(photo.getDetectedObjects());
            }
        } else {
            reset(photoDTO);
        }

        // photoDTO.setBelongTo

        Photo phototostore = photoMapper.toEntity(photoDTO);
        phototostore = photoRepository.save(phototostore);
        PhotoLite photoLitetostore = photoLiteMapper.toEntity(photoDTO);
        photoLitetostore.setId(phototostore.getId());
        photoLiteSearchRepository.save(photoLitetostore);
        return photoMapper.toDto(phototostore);
    }

    /**
     * Get all the photos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Photos");
        return photoLiteRepository.findAll(pageable)
            .map(photoLiteMapper::toDto);
    }


    /**
     * Get one photo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoLiteRepository.findById(id)
            .map(photoLiteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOneWithImage(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findById(id)
            .map(photoMapper::toDto);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
        photoLiteSearchRepository.deleteById(id);
    }


    /**
     * Search for the photo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Photos for query {}", query);
        return photoLiteSearchRepository.search(queryStringQuery(query), pageable)
            .map(photoLiteMapper::toDto);
    }

    /**
     * Search for the photo corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PhotoDTO> searchByIndex(String query, Pageable pageable) {
        log.debug("Request to search for a page of Photos for query {}", query);
        return photoLiteSearchRepository.searchByIndex(query, pageable)
            .map(photoLiteMapper::toDto);
    }
}
