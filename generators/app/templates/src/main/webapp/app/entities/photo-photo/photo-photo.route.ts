import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { PhotoPhoto } from 'app/shared/model/photo-photo.model';
import { PhotoPhotoService } from './photo-photo.service';
import { PhotoPhotoComponent } from './photo-photo.component';
import { PhotoPhotoDetailComponent } from './photo-photo-detail.component';
import { PhotoPhotoUpdateComponent } from './photo-photo-update.component';
import { IPhotoPhoto } from 'app/shared/model/photo-photo.model';

@Injectable({ providedIn: 'root' })
export class PhotoPhotoResolve implements Resolve<IPhotoPhoto> {
  private result: PhotoPhoto;
  constructor(private service: PhotoPhotoService) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPhotoPhoto> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        map((photo: HttpResponse<PhotoPhoto>) => {
          this.result = photo.body;
          return this.result;
        })
      );
    }
    return of(new PhotoPhoto());
  }
}

export const photoRoute: Routes = [
  {
    path: '',
    component: PhotoPhotoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'lazyblobApp.photo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PhotoPhotoDetailComponent,
    resolve: {
      photo: PhotoPhotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lazyblobApp.photo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PhotoPhotoUpdateComponent,
    resolve: {
      photo: PhotoPhotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lazyblobApp.photo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PhotoPhotoUpdateComponent,
    resolve: {
      photo: PhotoPhotoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'lazyblobApp.photo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
