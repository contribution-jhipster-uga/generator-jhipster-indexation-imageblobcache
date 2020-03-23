//<--! package -->

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link sample.lazyblob.domain.Photo} entity. This class is used
 * in {@link sample.lazyblob.web.rest.PhotoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /photos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhotoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter note;

    private StringFilter imageSha1;

    private StringFilter thumbnailx1Sha1;

    private StringFilter thumbnailx2Sha1;

    private InstantFilter createdAt;

    private InstantFilter updatedAt;

    private LongFilter userId;

    public PhotoCriteria(){
    }

    public PhotoCriteria(PhotoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.note = other.note == null ? null : other.note.copy();
        this.imageSha1 = other.imageSha1 == null ? null : other.imageSha1.copy();
        this.thumbnailx1Sha1 = other.thumbnailx1Sha1 == null ? null : other.thumbnailx1Sha1.copy();
        this.thumbnailx2Sha1 = other.thumbnailx2Sha1 == null ? null : other.thumbnailx2Sha1.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public PhotoCriteria copy() {
        return new PhotoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getImageSha1() {
        return imageSha1;
    }

    public void setImageSha1(StringFilter imageSha1) {
        this.imageSha1 = imageSha1;
    }

    public StringFilter getThumbnailx1Sha1() {
        return thumbnailx1Sha1;
    }

    public void setThumbnailx1Sha1(StringFilter thumbnailx1Sha1) {
        this.thumbnailx1Sha1 = thumbnailx1Sha1;
    }

    public StringFilter getThumbnailx2Sha1() {
        return thumbnailx2Sha1;
    }

    public void setThumbnailx2Sha1(StringFilter thumbnailx2Sha1) {
        this.thumbnailx2Sha1 = thumbnailx2Sha1;
    }

    public InstantFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(InstantFilter createdAt) {
        this.createdAt = createdAt;
    }

    public InstantFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(InstantFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhotoCriteria that = (PhotoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(note, that.note) &&
            Objects.equals(imageSha1, that.imageSha1) &&
            Objects.equals(thumbnailx1Sha1, that.thumbnailx1Sha1) &&
            Objects.equals(thumbnailx2Sha1, that.thumbnailx2Sha1) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        note,
        imageSha1,
        thumbnailx1Sha1,
        thumbnailx2Sha1,
        createdAt,
        updatedAt,
        userId
        );
    }

    @Override
    public String toString() {
        return "PhotoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (imageSha1 != null ? "imageSha1=" + imageSha1 + ", " : "") +
                (thumbnailx1Sha1 != null ? "thumbnailx1Sha1=" + thumbnailx1Sha1 + ", " : "") +
                (thumbnailx2Sha1 != null ? "thumbnailx2Sha1=" + thumbnailx2Sha1 + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
