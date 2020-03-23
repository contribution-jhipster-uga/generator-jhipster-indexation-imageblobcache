//<--! package -->

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Type;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * Entity Photo
 */
@Entity
@Table(name = "photo")
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    /**
     * Title
     */
    @Column(name = "title")
    private String title;

    /**
     * Note
     */
    @Column(name = "note")
    private String note;

    /**
     * Image
     */

    @Lazy
    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @Size(min = 40, max = 40)
    @Pattern(regexp = "([a-fA-F0-9]{40})?")
    @Column(name = "image_sha_1", length = 40)
    private String imageSha1;

    /**
     * Thumbnail x1
     */

    @Lazy
    @Lob
    @Column(name = "thumbnailx_1", nullable = false)
    private byte[] thumbnailx1;

    @Column(name = "thumbnailx_1_content_type", nullable = false)
    private String thumbnailx1ContentType;

    @Size(min = 40, max = 40)
    @Pattern(regexp = "([a-fA-F0-9]{40})?")
    @Column(name = "thumbnailx_1_sha_1", length = 40)
    private String thumbnailx1Sha1;

    /**
     * Thumbnail x2
     */

    @Lazy
    @Lob
    @Column(name = "thumbnailx_2", nullable = false)
    private byte[] thumbnailx2;

    @Column(name = "thumbnailx_2_content_type", nullable = false)
    private String thumbnailx2ContentType;

    @Size(min = 40, max = 40)
    @Pattern(regexp = "([a-fA-F0-9]{40})?")
    @Column(name = "thumbnailx_2_sha_1", length = 40)
    private String thumbnailx2Sha1;

    /**
     * Extracted EXIF from the photo (LAZY)
     */
//    @Lazy
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "exif")
    private String exif;

    /**
     * Extracted text by the Tesseract OCR (LAZY)
     */
//    @Lazy
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "extracted_text")
    private String extractedText;

    /**
     * Detected objects into the photo (ImageAI, Tensorflow ...) (LAZY)
     */
//    @Lazy
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "detected_objects")
    private String detectedObjects;

    /**
     * Creation date
     */
    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    /**
     * Update date
     */
    @Column(name = "updated_at")
    private Instant updatedAt;


    @ManyToOne
    @JsonIgnoreProperties("photos")
    private User user;



    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Photo title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public Photo note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public Photo image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Photo imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageSha1() {
        return imageSha1;
    }

    public Photo imageSha1(String imageSha1) {
        this.imageSha1 = imageSha1;
        return this;
    }

    public void setImageSha1(String imageSha1) {
        this.imageSha1 = imageSha1;
    }

    public byte[] getThumbnailx1() {
        return thumbnailx1;
    }

    public Photo thumbnailx1(byte[] thumbnailx1) {
        this.thumbnailx1 = thumbnailx1;
        return this;
    }

    public void setThumbnailx1(byte[] thumbnailx1) {
        this.thumbnailx1 = thumbnailx1;
    }

    public String getThumbnailx1ContentType() {
        return thumbnailx1ContentType;
    }

    public Photo thumbnailx1ContentType(String thumbnailx1ContentType) {
        this.thumbnailx1ContentType = thumbnailx1ContentType;
        return this;
    }

    public void setThumbnailx1ContentType(String thumbnailx1ContentType) {
        this.thumbnailx1ContentType = thumbnailx1ContentType;
    }

    public String getThumbnailx1Sha1() {
        return thumbnailx1Sha1;
    }

    public Photo thumbnailx1Sha1(String thumbnailx1Sha1) {
        this.thumbnailx1Sha1 = thumbnailx1Sha1;
        return this;
    }

    public void setThumbnailx1Sha1(String thumbnailx1Sha1) {
        this.thumbnailx1Sha1 = thumbnailx1Sha1;
    }

    public byte[] getThumbnailx2() {
        return thumbnailx2;
    }

    public Photo thumbnailx2(byte[] thumbnailx2) {
        this.thumbnailx2 = thumbnailx2;
        return this;
    }

    public void setThumbnailx2(byte[] thumbnailx2) {
        this.thumbnailx2 = thumbnailx2;
    }

    public String getThumbnailx2ContentType() {
        return thumbnailx2ContentType;
    }

    public Photo thumbnailx2ContentType(String thumbnailx2ContentType) {
        this.thumbnailx2ContentType = thumbnailx2ContentType;
        return this;
    }

    public void setThumbnailx2ContentType(String thumbnailx2ContentType) {
        this.thumbnailx2ContentType = thumbnailx2ContentType;
    }

    public String getThumbnailx2Sha1() {
        return thumbnailx2Sha1;
    }

    public Photo thumbnailx2Sha1(String thumbnailx2Sha1) {
        this.thumbnailx2Sha1 = thumbnailx2Sha1;
        return this;
    }

    public void setThumbnailx2Sha1(String thumbnailx2Sha1) {
        this.thumbnailx2Sha1 = thumbnailx2Sha1;
    }

    public String getExif() {
        return exif;
    }

    public Photo exif(String exif) {
        this.exif = exif;
        return this;
    }

    public void setExif(String exif) {
        this.exif = exif;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public Photo extractedText(String extractedText) {
        this.extractedText = extractedText;
        return this;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public String getDetectedObjects() {
        return detectedObjects;
    }

    public Photo detectedObjects(String detectedObjects) {
        this.detectedObjects = detectedObjects;
        return this;
    }

    public void setDetectedObjects(String detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Photo createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Photo updatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUser() {
        return user;
    }

    public Photo user(User user) {
        this.user = user;
        return this;
    }
    
    public void setUser(User user) {
        this.user = user;
    }


    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photo)) {
            return false;
        }
        return id != null && id.equals(((Photo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", note='" + getNote() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", imageSha1='" + getImageSha1() + "'" +
            ", thumbnailx1='" + getThumbnailx1() + "'" +
            ", thumbnailx1ContentType='" + getThumbnailx1ContentType() + "'" +
            ", thumbnailx1Sha1='" + getThumbnailx1Sha1() + "'" +
            ", thumbnailx2='" + getThumbnailx2() + "'" +
            ", thumbnailx2ContentType='" + getThumbnailx2ContentType() + "'" +
            ", thumbnailx2Sha1='" + getThumbnailx2Sha1() + "'" +
            ", exif='" + getExif() + "'" +
            ", extractedText='" + getExtractedText() + "'" +
            ", detectedObjects='" + getDetectedObjects() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
