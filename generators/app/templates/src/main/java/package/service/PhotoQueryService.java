//<--! package -->

//<--! import -->

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Photo} entities in the database.
 * The main input is a {@link PhotoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PhotoDTO} or a {@link Page} of {@link PhotoDTO} which fulfills the criteria.
 */
 @Service
 @Transactional(readOnly = true)
 public class PhotoQueryService extends QueryService<PhotoLite> {

     private final Logger log = LoggerFactory.getLogger(PhotoQueryService.class);

     private final PhotoRepository photoRepository;
     private final PhotoLiteRepository photoLiteRepository;

     private final PhotoMapper photoMapper;
     private final PhotoLiteMapper photoLiteMapper;


     public PhotoQueryService(PhotoRepository photoRepository, PhotoLiteRepository photoLiteRepository, PhotoMapper photoMapper, PhotoLiteMapper photoLiteMapper) {
         this.photoRepository = photoRepository;
         this.photoLiteRepository = photoLiteRepository;
         this.photoMapper = photoMapper;
         this.photoLiteMapper = photoLiteMapper;
     }

     /**
      * Return a {@link List} of {@link PhotoDTO} which matches the criteria from the database.
      * @param criteria The object which holds all the filters, which the entities should match.
      * @return the matching entities.
      */
     @Transactional(readOnly = true)
     public List<PhotoDTO> findByCriteria(PhotoCriteria criteria) {
         log.debug("find by criteria : {}", criteria);
         final Specification<PhotoLite> specification = createSpecification(criteria);
         return photoLiteMapper.toDto(photoLiteRepository.findAll(specification));
     }

     /**
      * Return a {@link Page} of {@link PhotoDTO} which matches the criteria from the database.
      * @param criteria The object which holds all the filters, which the entities should match.
      * @param page The page, which should be returned.
      * @return the matching entities.
      */
     @Transactional(readOnly = true)
     public Page<PhotoDTO> findByCriteria(PhotoCriteria criteria, Pageable page) {
         log.debug("find by criteria : {}, page: {}", criteria, page);
         final Specification<PhotoLite> specification = createSpecification(criteria);
         return photoLiteRepository.findAll(specification, page)
             .map(photoLiteMapper::toDto);
     }

     /**
      * Return the number of matching entities in the database.
      * @param criteria The object which holds all the filters, which the entities should match.
      * @return the number of matching entities.
      */
     @Transactional(readOnly = true)
     public long countByCriteria(PhotoCriteria criteria) {
         log.debug("count by criteria : {}", criteria);
         final Specification<PhotoLite> specification = createSpecification(criteria);
         return photoLiteRepository.count(specification);
     }

     /**
      * Function to convert {@link PhotoCriteria} to a {@link Specification}
      * @param criteria The object which holds all the filters, which the entities should match.
      * @return the matching {@link Specification} of the entity.
      */
     protected Specification<PhotoLite> createSpecification(PhotoCriteria criteria) {
         Specification<PhotoLite> specification = Specification.where(null);
         if (criteria != null) {
             if (criteria.getId() != null) {
                 specification = specification.and(buildRangeSpecification(criteria.getId(), PhotoLite_.id));
             }
             if (criteria.getTitle() != null) {
                 specification = specification.and(buildStringSpecification(criteria.getTitle(), PhotoLite_.title));
             }
             if (criteria.getNote() != null) {
                 specification = specification.and(buildStringSpecification(criteria.getNote(), PhotoLite_.note));
             }
             if (criteria.getImageSha1() != null) {
                 specification = specification.and(buildStringSpecification(criteria.getImageSha1(), PhotoLite_.imageSha1));
             }
             if (criteria.getThumbnailx1Sha1() != null) {
                 specification = specification.and(buildStringSpecification(criteria.getThumbnailx1Sha1(), PhotoLite_.thumbnailx1Sha1));
             }
             if (criteria.getThumbnailx2Sha1() != null) {
                 specification = specification.and(buildStringSpecification(criteria.getThumbnailx2Sha1(), PhotoLite_.thumbnailx2Sha1));
             }
         }
         return specification;
     }
 }
