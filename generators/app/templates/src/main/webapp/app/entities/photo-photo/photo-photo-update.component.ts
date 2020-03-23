import { Component, OnInit, ElementRef } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IPhotoPhoto, PhotoPhoto } from 'app/shared/model/photo-photo.model';
import { PhotoPhotoService } from './photo-photo.service';

@Component({
  selector: 'jhi-photo-photo-update',
  templateUrl: './photo-photo-update.component.html'
})
export class PhotoPhotoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [],
    note: [],
    image: [null, [Validators.required]],
    imageContentType: []
    /*
    imageSha1: [null, [Validators.minLength(40), Validators.maxLength(40), Validators.pattern('([a-fA-F0-9]{40})?')]],
    thumbnailx1: [null, [Validators.required]],
    thumbnailx1ContentType: [],
    thumbnailx1Sha1: [null, [Validators.minLength(40), Validators.maxLength(40), Validators.pattern('([a-fA-F0-9]{40})?')]],
    thumbnailx2: [null, [Validators.required]],
    thumbnailx2ContentType: [],
    thumbnailx2Sha1: [null, [Validators.minLength(40), Validators.maxLength(40), Validators.pattern('([a-fA-F0-9]{40})?')]],
    exif: [],
    extractedText: [],
    detectedObjects: [],
    createdAt: [null, [Validators.required]],
    updatedAt: [],
    */
    //belongToId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected photoService: PhotoPhotoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;

    this.activatedRoute.data.subscribe(({ photo }) => {
      this.photoService.transform('api/photos/' + photo.id + '/image').then(res => {
        console.log('-----------------------------');
        photo.image = res;
        this.updateForm(photo);
      });
    });
  }

  updateForm(photo: IPhotoPhoto) {
    this.editForm.patchValue({
      id: photo.id,
      title: photo.title,
      note: photo.note,
      image: photo.image,
      imageContentType: photo.imageContentType
      /*
      imageSha1: photo.imageSha1,
      thumbnailx1: photo.thumbnailx1,
      thumbnailx1ContentType: photo.thumbnailx1ContentType,
      thumbnailx1Sha1: photo.thumbnailx1Sha1,
      thumbnailx2: photo.thumbnailx2,
      thumbnailx2ContentType: photo.thumbnailx2ContentType,
      thumbnailx2Sha1: photo.thumbnailx2Sha1,
      exif: photo.exif,
      extractedText: photo.extractedText,
      detectedObjects: photo.detectedObjects,
      createdAt: photo.createdAt != null ? photo.createdAt.format(DATE_TIME_FORMAT) : null,
      updatedAt: photo.updatedAt != null ? photo.updatedAt.format(DATE_TIME_FORMAT) : null,
      */
      //belongToId: photo.belongToId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: 'data:' + file.type + ';base64,' + base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const photo = this.createFromForm();
    photo.image = photo.image.replace('data:' + photo.imageContentType + ';base64,', '');
    if (photo.id !== undefined && photo.id != null) {
      this.subscribeToSaveResponse(this.photoService.update(photo));
    } else {
      this.subscribeToSaveResponse(this.photoService.create(photo));
    }
  }

  private createFromForm(): IPhotoPhoto {
    return {
      ...new PhotoPhoto(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      note: this.editForm.get(['note']).value,
      imageContentType: this.editForm.get(['imageContentType']).value,
      image: this.editForm.get(['image']).value
      /*imageSha1: this.editForm.get(['imageSha1']).value,
      thumbnailx1ContentType: this.editForm.get(['thumbnailx1ContentType']).value,
      thumbnailx1: this.editForm.get(['thumbnailx1']).value,
      thumbnailx1Sha1: this.editForm.get(['thumbnailx1Sha1']).value,
      thumbnailx2ContentType: this.editForm.get(['thumbnailx2ContentType']).value,
      thumbnailx2: this.editForm.get(['thumbnailx2']).value,
      thumbnailx2Sha1: this.editForm.get(['thumbnailx2Sha1']).value,
      exif: this.editForm.get(['exif']).value,
      extractedText: this.editForm.get(['extractedText']).value,
      detectedObjects: this.editForm.get(['detectedObjects']).value,
      createdAt:
        this.editForm.get(['createdAt']).value != null ? moment(this.editForm.get(['createdAt']).value, DATE_TIME_FORMAT) : undefined,
      updatedAt:
        this.editForm.get(['updatedAt']).value != null ? moment(this.editForm.get(['updatedAt']).value, DATE_TIME_FORMAT) : undefined,
      */
      //belongToId: this.editForm.get(['belongToId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhotoPhoto>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
