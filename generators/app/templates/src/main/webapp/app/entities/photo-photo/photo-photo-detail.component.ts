import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPhotoPhoto } from 'app/shared/model/photo-photo.model';
import { PhotoPhotoService } from 'app/entities/photo-photo/photo-photo.service';

@Component({
  selector: 'jhi-photo-photo-detail',
  templateUrl: './photo-photo-detail.component.html'
})
export class PhotoPhotoDetailComponent implements OnInit {
  photo: IPhotoPhoto;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected photoService: PhotoPhotoService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ photo }) => {
      this.photoService.transform('/api/photos/' + photo.id + '/image').then(res => {
        this.photo = photo;
        this.photo.image = res;
      });
      this.photoService.transform('/api/photos/' + photo.id + '/thumbnailx1').then(res => {
        this.photo.thumbnailx1 = res;
      });
      this.photoService.transform('/api/photos/' + photo.id + '/thumbnailx2').then(res => {
        this.photo.thumbnailx2 = res;
      });
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
