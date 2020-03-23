//<--! package -->

//<--! import -->

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link sample.lazyblob.domain.Photo} entity.
 */
@ApiModel(description = "Entity Photo")
public class PhotoDTO implements Serializable {

    private Long id;

    /**
     * Title
     */
    @ApiModelProperty(value = "Title")
    private String title;

    /**
     * Note
     */
    @ApiModelProperty(value = "Note")
    private String note;

    /**
     * Image
     */

    @ApiModelProperty(value = "Image", required = true)
    @Lob
    private byte[] image;

    private String imageContentType;
    @Size(min = 40, max = 40)
    @Pattern(regexp = "([a-fA-F0-9]{40})?")
    private String imageSha1;

    /**
     * Thumbnail x1
     */

    //@ApiModelProperty(value = "Thumbnail x1", required = true)
    @ApiModelProperty(value = "Thumbnail x1")
    @Lob
    private byte[] thumbnailx1;

    private String thumbnailx1ContentType;
    @Size(min = 40, max = 40)
    @Pattern(regexp = "([a-fA-F0-9]{40})?")
    private String thumbnailx1Sha1;

    /**
     * Thumbnail x2
     */

    //@ApiModelProperty(value = "Thumbnail x2", required = true)
    @ApiModelProperty(value = "Thumbnail x2")
    @Lob
    private byte[] thumbnailx2;

    private String thumbnailx2ContentType;
    @Size(min = 40, max = 40)
    @Pattern(regexp = "([a-fA-F0-9]{40})?")
    private String thumbnailx2Sha1;

    /**
     * Extracted EXIF from the photo (LAZY)
     */
    @ApiModelProperty(value = "Extracted EXIF from the photo (LAZY)")
    @Lob
    private String exif;

    /**
     * Extracted text by the Tesseract OCR (LAZY)
     */
    @ApiModelProperty(value = "Extracted text by the Tesseract OCR (LAZY)")
    @Lob
    private String extractedText;

    /**
     * Detected objects into the photo (ImageAI, Tensorflow ...) (LAZY)
     */
    @ApiModelProperty(value = "Detected objects into the photo (ImageAI, Tensorflow ...) (LAZY)")
    @Lob
    private String detectedObjects;

    /**
     * Creation date
     */
    //@NotNull
    //@ApiModelProperty(value = "Creation date", required = true)
    @ApiModelProperty(value = "Creation date")
    private Instant createdAt;

    /**
     * Update date
     */
    @ApiModelProperty(value = "Update date")
    private Instant updatedAt;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public String getImageSha1() {
        return imageSha1;
    }

    public void setImageSha1(String imageSha1) {
        this.imageSha1 = imageSha1;
    }

    public byte[] getThumbnailx1() {
        return thumbnailx1;
    }

    public void setThumbnailx1(byte[] thumbnailx1) {
        this.thumbnailx1 = thumbnailx1;
    }

    public String getThumbnailx1ContentType() {
        return thumbnailx1ContentType;
    }

    public void setThumbnailx1ContentType(String thumbnailx1ContentType) {
        this.thumbnailx1ContentType = thumbnailx1ContentType;
    }

    public String getThumbnailx1Sha1() {
        return thumbnailx1Sha1;
    }

    public void setThumbnailx1Sha1(String thumbnailx1Sha1) {
        this.thumbnailx1Sha1 = thumbnailx1Sha1;
    }

    public byte[] getThumbnailx2() {
        return thumbnailx2;
    }

    public void setThumbnailx2(byte[] thumbnailx2) {
        this.thumbnailx2 = thumbnailx2;
    }

    public String getThumbnailx2ContentType() {
        return thumbnailx2ContentType;
    }

    public void setThumbnailx2ContentType(String thumbnailx2ContentType) {
        this.thumbnailx2ContentType = thumbnailx2ContentType;
    }

    public String getThumbnailx2Sha1() {
        return thumbnailx2Sha1;
    }

    public void setThumbnailx2Sha1(String thumbnailx2Sha1) {
        this.thumbnailx2Sha1 = thumbnailx2Sha1;
    }

    public String getExif() {
        return exif;
    }

    public void setExif(String exif) {
        this.exif = exif;
    }

    public String getExtractedText() {
        return extractedText;
    }

    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }

    public String getDetectedObjects() {
        return detectedObjects;
    }

    public void setDetectedObjects(String detectedObjects) {
        this.detectedObjects = detectedObjects;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PhotoDTO photoDTO = (PhotoDTO) o;
        if (photoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), photoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PhotoDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", note='" + getNote() + "'" +
            ", image='" + getImage() + "'" +
            ", imageSha1='" + getImageSha1() + "'" +
            ", thumbnailx1='" + getThumbnailx1() + "'" +
            ", thumbnailx1Sha1='" + getThumbnailx1Sha1() + "'" +
            ", thumbnailx2='" + getThumbnailx2() + "'" +
            ", thumbnailx2Sha1='" + getThumbnailx2Sha1() + "'" +
            ", exif='" + getExif() + "'" +
            ", extractedText='" + getExtractedText() + "'" +
            ", detectedObjects='" + getDetectedObjects() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
