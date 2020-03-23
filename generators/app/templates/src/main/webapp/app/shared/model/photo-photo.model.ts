import { Moment } from 'moment';

export interface IPhotoPhoto {
  id?: number;
  title?: string;
  note?: string;
  imageContentType?: string;
  image?: any;
  imageSha1?: string;
  thumbnailx1ContentType?: string;
  thumbnailx1?: any;
  thumbnailx1Sha1?: string;
  thumbnailx2ContentType?: string;
  thumbnailx2?: any;
  thumbnailx2Sha1?: string;
  exif?: any;
  extractedText?: any;
  detectedObjects?: any;
  createdAt?: Moment;
  updatedAt?: Moment;
}

export class PhotoPhoto implements IPhotoPhoto {
  constructor(
    public id?: number,
    public title?: string,
    public note?: string,
    public imageContentType?: string,
    public image?: any,
    public imageSha1?: string,
    public thumbnailx1ContentType?: string,
    public thumbnailx1?: any,
    public thumbnailx1Sha1?: string,
    public thumbnailx2ContentType?: string,
    public thumbnailx2?: any,
    public thumbnailx2Sha1?: string,
    public exif?: any,
    public extractedText?: any,
    public detectedObjects?: any,
    public createdAt?: Moment,
    public updatedAt?: Moment
  ) {}
}
