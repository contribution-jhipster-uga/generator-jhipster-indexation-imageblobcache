//<--! package -->//

//<--! import -->

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.CacheControl;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.micrometer.core.annotation.Timed;

/**
 * REST controller for managing {@link sample.lazyblob.domain.Photo}.
 */
@RestController
@RequestMapping("/api")
public class PhotoResource {

	private final Logger log = LoggerFactory.getLogger(PhotoResource.class);

	private static final String ENTITY_NAME = "photo";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final PhotoService photoService;

	private final PhotoQueryService photoQueryService;

	public PhotoResource(PhotoService photoService, PhotoQueryService photoQueryService) {
		this.photoService = photoService;
		this.photoQueryService = photoQueryService;
	}

	/**
	 * {@code POST  /photos} : Create a new photo.
	 *
	 * @param photoDTO the photoDTO to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new photoDTO, or with status {@code 400 (Bad Request)} if
	 *         the photo has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PostMapping("/photos")
	public ResponseEntity<PhotoDTO> createPhoto(@Valid @RequestBody PhotoDTO photoDTO) throws URISyntaxException {
		log.debug("REST request to save Photo : {}", photoDTO);
		if (photoDTO.getId() != null) {
			throw new BadRequestAlertException("A new photo cannot already have an ID", ENTITY_NAME, "idexists");
		}
		PhotoDTO result = photoService.save(photoDTO);
		return ResponseEntity
				.created(new URI("/api/photos/" + result.getId())).headers(HeaderUtil
						.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
				.body(result);
	}

	/**
	 * {@code PUT  /photos} : Updates an existing photo.
	 *
	 * @param photoDTO the photoDTO to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated photoDTO, or with status {@code 400 (Bad Request)} if the
	 *         photoDTO is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the photoDTO couldn't be
	 *         updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@PutMapping("/photos")
	public ResponseEntity<PhotoDTO> updatePhoto(@Valid @RequestBody PhotoDTO photoDTO) throws URISyntaxException {
		log.debug("REST request to update Photo : {}", photoDTO);
		if (photoDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		PhotoDTO result = photoService.save(photoDTO);
		return ResponseEntity.ok().headers(
				HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, photoDTO.getId().toString()))
				.body(result);
	}

	/**
	 * {@code GET  /photos} : get all the photos.
	 *
	 *
	 * @param pageable the pagination information.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of photos in body.
	 */
	@GetMapping("/photos")
	public ResponseEntity<List<PhotoDTO>> getAllPhotos(PhotoCriteria criteria, Pageable pageable) {
		log.debug("REST request to get Photos by criteria: {}", criteria);
		Page<PhotoDTO> page = photoQueryService.findByCriteria(criteria, pageable);
        for (PhotoDTO photo: page) {
            reset(photo);
        }
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return ResponseEntity.ok().headers(headers).body(page.getContent());
	}

	/**
	 * {@code GET  /photos/count} : count all the photos.
	 *
	 * @param criteria the criteria which the requested entities should match.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count
	 *         in body.
	 */
	@GetMapping("/photos/count")
	public ResponseEntity<Long> countPhotos(PhotoCriteria criteria) {
		log.debug("REST request to count Photos by criteria: {}", criteria);
		return ResponseEntity.ok().body(photoQueryService.countByCriteria(criteria));
	}

	/**
	 * {@code GET  /photos/:id} : get the "id" photo.
	 *
	 * @param id the id of the photoDTO to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the photoDTO, or with status {@code 404 (Not Found)}.
	 */
	@GetMapping("/photos/{id}")
	public ResponseEntity<PhotoDTO> getPhoto(@PathVariable Long id) {
		log.debug("REST request to get Photo : {}", id);
		Optional<PhotoDTO> photoDTO = photoService.findOne(id);
		reset(photoDTO.get());
		return ResponseUtil.wrapOrNotFound(photoDTO);
	}

	private void reset(PhotoDTO photoDTO){
	    	//photoDTO.setImage(null);
	    	//photoDTO.setImageSha1(null);
        //photoDTO.setThumbnailx1(null);
        //photoDTO.setThumbnailx1Sha1(null);
        //photoDTO.setThumbnailx1ContentType(null);
        //photoDTO.setThumbnailx2(null);
        //photoDTO.setThumbnailx2Sha1(null);
        //photoDTO.setThumbnailx2ContentType(null);
        photoDTO.setExif(null);
        photoDTO.setExtractedText(null);
        photoDTO.setDetectedObjects(null);
    }

	/**
	 * {@code DELETE  /photos/:id} : delete the "id" photo.
	 *
	 * @param id the id of the photoDTO to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@DeleteMapping("/photos/{id}")
	public ResponseEntity<Void> deletePhoto(@PathVariable Long id) {
		log.debug("REST request to delete Photo : {}", id);
		photoService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
				.build();
	}

	// Begin Lazy Blob generated code

	@GetMapping(value = "/photos/{id}/{blob}")
	@Timed
	public ResponseEntity<byte[]> getBlobAsResponseEntity(
			@Nullable @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, defaultValue = "") final String ifNoneMatch,
			@PathVariable final Long id, @PathVariable final String blob) {
		Optional<String> login = SecurityUtils.getCurrentUserLogin();
		log.debug("REST request from {} to get the {} of {} with id={}",  login, blob, applicationName, id);

		final Optional<PhotoDTO> photoDTO = photoService.findOne(id);
		if (photoDTO.isPresent()) {
			final PhotoDTO d = photoDTO.get();
			String contentType = null;
			String sha1 = null;

			//final byte[] buf;
			switch (blob) {
			case "image":
				//buf = d.getImage();
				if ((sha1 = d.getImageSha1()) != null) {
					contentType = d.getImageContentType();
					//sha1 = d.getImageSha1();
				}
				break;
			case "thumbnailx1":
				//buf = d.getThumbnailx1();
				if ((sha1 = d.getThumbnailx1Sha1()) != null) {
					contentType = d.getThumbnailx1ContentType();
					//sha1 = d.getThumbnailx1Sha1();
				}
				break;

			case "thumbnailx2":
				//buf = d.getThumbnailx2();
				if ((sha1 = d.getThumbnailx2Sha1()) != null) {
					contentType = d.getThumbnailx2ContentType();
					//sha1 = d.getThumbnailx2Sha1();
				}
				break;

			default:
				// = null;
			}

			//if (buf != null) {
				String ext = MimeTypes.lookupExt(contentType);
				String filename = ENTITY_NAME + "_" + id + "." + blob + "." + ext;
				return getResponseEntity(d.getId(),sha1, contentType, d.getUpdatedAt(), ifNoneMatch, filename);
			//}
		}
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(null));
	}



    /**
     * {@code SEARCH  /_search/photos?query=:query} : search for the photo corresponding
     * to the query.
     *
     * @param query    the query of the photo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/photos")
    public ResponseEntity<List<PhotoDTO>> searchPhotos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Photos for query {}", query);
        Page<PhotoDTO> page = photoService.search(query, pageable);
        for (PhotoDTO p : page) {
            reset(p);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code SEARCH  /_search/photos/index?query=:query} : search for the photo corresponding
     * to the query.
     *
     * @param query    the query of the photo search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/photos/index")
    public ResponseEntity<List<PhotoDTO>> searchPhotosByIndex(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Photos for query {}", query);
        Page<PhotoDTO> page = photoService.searchByIndex(query, pageable);
        for (PhotoDTO p : page) {
            reset(p);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

	/**
	 *  cacheControl maxAge in minutes
	 */
	@Value("${lazyblob.cacheControl.maxAge}")
	private long MAXAGE;


	/**
	 * Helper for byte[] ResponseEntity
	 * @param id
	 * @param sha1
	 * @param contentType
	 * @param dateModification
	 * @param ifNoneMatch
	 * @param filename
	 * @return
	 */
	private ResponseEntity<byte[]> getResponseEntity(final Long id,final String sha1, final String contentType,
                                                     final Instant dateModification, final String ifNoneMatch, final String filename) {
		// sélectionner la politique de caching en fonction de l'authorité du sujet
		final String cacheControl;
		if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
			cacheControl = CacheControl.noCache().getHeaderValue();
		} else {
			cacheControl = CacheControl.maxAge(MAXAGE, TimeUnit.MINUTES).getHeaderValue();
		}

		if (ifNoneMatch.equals("\"" + sha1 + "\"")) {
			return getResponseEntity(null, contentType, dateModification, sha1, cacheControl, filename);
		} else {
		    byte[] buf = photoService.findOneWithImage(id).get().getImage();
			return getResponseEntity(buf, contentType, dateModification, sha1, cacheControl, filename);
		}
	}


	/**
	 * Helper for byte[] ResponseEntity
	 * @param buf can be null
	 * @param contentType
	 * @param updateAt can be null
	 * @param sha1
	 * @param cacheControl
	 * @param filename can be null
	 * @return
	 */

	private ResponseEntity<byte[]> getResponseEntity(@Nullable final byte[] buf, final String contentType,
			final Instant updateAt, @NotNull final String sha1, final String cacheControl, final String filename) {

		HttpHeaders headers = new HttpHeaders();
		if(buf!=null) {
			headers.setContentLength(buf.length);
		}
		headers.setCacheControl(cacheControl);

		headers.set(HttpHeaders.CONTENT_TYPE, contentType); // TODO ajout du Charset au contentType ? par exemple,
															// application/json;charset=UTF-8
		if (filename != null) {
			headers.setContentDisposition(ContentDisposition.builder("inline").filename(filename).build());
		}

		if (updateAt != null) {
			OffsetDateTime odt = updateAt.atOffset(ZoneOffset.UTC);
			headers.set(HttpHeaders.LAST_MODIFIED, odt.format(DateTimeFormatter.RFC_1123_DATE_TIME));
		}
		if (sha1 != null) {
			headers.setETag("\"" + sha1 + "\"");
		}

		HttpStatus status;
		if (buf == null) {
			status= HttpStatus.NOT_MODIFIED;
		} else {
			status= HttpStatus.OK;
		}
		return new ResponseEntity<>(buf, headers, status);
	}

}
