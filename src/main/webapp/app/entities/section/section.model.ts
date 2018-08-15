import { BaseEntity } from './../../shared';

export class Section implements BaseEntity {
    constructor(
        public id?: number,
        public titre?: string,
        public textAvant?: string,
        public textApres?: string,
        public idPhoto?: string,
        public photoContentType?: string,
        public photo?: any,
        public articleId?: number,
    ) {
    }
}
