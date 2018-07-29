import { BaseEntity } from './../../shared';

export class Commentaire implements BaseEntity {
    constructor(
        public id?: number,
        public contenu?: string,
        public articleId?: number,
        public abonneId?: number,
    ) {
    }
}
