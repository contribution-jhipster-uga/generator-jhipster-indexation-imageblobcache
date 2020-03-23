import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

//<--! import -->

import { PhotoPhotoComponent } from './photo-photo.component';
import { PhotoPhotoDetailComponent } from './photo-photo-detail.component';
import { PhotoPhotoUpdateComponent } from './photo-photo-update.component';
import { PhotoPhotoDeleteDialogComponent } from './photo-photo-delete-dialog.component';
import { photoRoute } from './photo-photo.route';

import { AuthImagePipe } from './authimg.pipe';

@NgModule({
  //<--! sharedmodule -->
  declarations: [PhotoPhotoComponent, PhotoPhotoDetailComponent, PhotoPhotoUpdateComponent, PhotoPhotoDeleteDialogComponent, AuthImagePipe],
  entryComponents: [PhotoPhotoDeleteDialogComponent]
})
export class LazyblobPhotoPhotoModule {}
