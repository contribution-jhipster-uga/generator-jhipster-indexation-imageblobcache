import { Component } from '@angular/core';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPhotoPhoto } from 'app/shared/model/photo-photo.model';
import { PhotoPhotoService } from './photo-photo.service';

@Component({
  templateUrl: './photo-photo-delete-dialog.component.html'
})
export class PhotoPhotoDeleteDialogComponent {
  photo: IPhotoPhoto;

  constructor(protected photoService: PhotoPhotoService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.photoService.delete(id).subscribe(() => {
      this.eventManager.broadcast({
        name: 'photoListModification',
        content: 'Deleted an photo'
      });
      this.activeModal.dismiss(true);
    });
  }
}
