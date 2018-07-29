import { BaseEntity } from './../../shared';

export const enum FORMAT_PHOTO {
    'COUVERTURE',
    'CARTE',
    'VIGNETTE'
}

export class Photo implements BaseEntity {
    constructor(
        public id?: number,
        public idPhoto?: string,
        public format?: FORMAT_PHOTO,
        public photoContentType?: string,
        public photo?: any,
    ) {
    }
}
