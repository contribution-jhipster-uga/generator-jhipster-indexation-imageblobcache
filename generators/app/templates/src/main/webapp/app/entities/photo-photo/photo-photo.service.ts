import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IPhotoPhoto } from 'app/shared/model/photo-photo.model';

type EntityResponseType = HttpResponse<IPhotoPhoto>;
type EntityArrayResponseType = HttpResponse<IPhotoPhoto[]>;

@Injectable({ providedIn: 'root' })
export class PhotoPhotoService {
  public resourceUrl = SERVER_API_URL + 'api/photos';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/photos';

  constructor(public http: HttpClient) {}

  create(photo: IPhotoPhoto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(photo);
    return this.http
      .post<IPhotoPhoto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(photo: IPhotoPhoto): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(photo);
    return this.http
      .put<IPhotoPhoto>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IPhotoPhoto>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  findBlob(id: number, blob: string): Observable<EntityResponseType> {
    return this.http.get(`${this.resourceUrl}/${id}/${blob}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPhotoPhoto[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IPhotoPhoto[]>(this.resourceSearchUrl + '/index', { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(photo: IPhotoPhoto): IPhotoPhoto {
    const copy: IPhotoPhoto = Object.assign({}, photo, {
      createdAt: photo.createdAt != null && photo.createdAt.isValid() ? photo.createdAt.toJSON() : null,
      updatedAt: photo.updatedAt != null && photo.updatedAt.isValid() ? photo.updatedAt.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
      res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((photo: IPhotoPhoto) => {
        photo.createdAt = photo.createdAt != null ? moment(photo.createdAt) : null;
        photo.updatedAt = photo.updatedAt != null ? moment(photo.updatedAt) : null;
      });
    }
    return res;
  }

  async transform(src: string): Promise<string> {
    const imageBlob = await this.http.get(src, { responseType: 'blob' }).toPromise();
    const reader = new FileReader();
    return new Promise((resolve, reject) => {
      reader.onloadend = () => resolve(reader.result as string);
      reader.readAsDataURL(imageBlob);
    });
  }
}
