import { BaseEntity } from './../../shared';

export class Article implements BaseEntity {
    constructor(
        public id?: number,
        public nom?: string,
        public photoContentType?: string,
        public photo?: any,
        public idPhoto?: any,
        public titre?: string,
        public resume?: string,
        public position?: number,
        public positionDansCategorie?: number,
        public dateCreation?: any,
        public dateDerniereModification?: any,
        public nbVue?: number,
        public nbLike?: number,
        public nbPartage?: number,
        public categorieId?: number,
        public sections?: BaseEntity[],
    ) {
    }
}
